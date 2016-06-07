Genetic Algorithm
=================

_This example was extracted from the book [Complexity: A Guided Tour](https://www.amazon.co.uk/Complexity-Guided-Tour-Melanie-Mitchell/dp/0199798109?ie=UTF8&ref_=asap_bc)_

# Robby, the Soda-Can-Collecting Robot

Robby's job is to clean up his world by collecting the empty soda cans. 

* The world consists of 100 squares (sites) laid out in a 10 x 10 grid.
* Robby starts on 0,0. 

* Robby can only see 5 sites at a time: North, South, East, West, and the site he currently occupies. 
* A site can be Empty, Contain a Can, or be a Wall.
 
E.g: If Robby is as site 0,0, sites to the North and West are Walls. 

## Actions

For each cleaning session, Robby can perform exactly 200 actions. Each action consists of one of the following 
seven choices:
 
* Move North 
* Move South 
* Move East 
* Move West 
* Move to Random Direction 
* Stay Put 
* Pick Up Can  
        
## Points 

Each action may generate a reward or a punishment. 

10 pts = If Robby is in the same site as a can and pickets it up    
-1 pts = If he tries to pick up a can in an empty site    
-5 pts = If he crashes into a wall. In this case, he should bounce back into the current site.    
   
Clearly, Robby's reward is maximised when he picks up as many cans as possible, without crashing into any walls or 
bending down to pick up a can if no can is there. 
    
As this is a simple problem, it would easy for a human to figure out a good strategy for Robby to follow. However, 
the point of a genetic algorithm is that humans don't need to think; they can let computer evolution to figure it out. 
 
## Strategy and Situations
 
* **Strategy**: Set of rules that gives, for any _situation_, the action you should take in that situation.
* **Situation**: What Robby can see; contents of his current site, plus the content of the north, south, west and east 
sites. In each situation, Robby can do one of the seven actions described above. 
 
A strategy for Robby can be written simply as a list of all possible situations he could encounter, and for each 
 possible situation, which of the seven possible actions he should perform. 
 
### Number of possible situations
 
There are 243 different possible situations: Robby looks at 5 different sites (current, north, south, west, east) and 
  each of those sites can be in one of the 3 states: **empty**, **contain can**, or **wall**: 3 x 3 x 3 x 3 x 3 = 243.
    
Actually, there aren't really that many situations, since Robby will never face a situation in which his current site 
is a wall, or one in which North, South, East, and West are walls. There are other impossible situations as well, 
but we don't want to figure out all the impossible situations by ourselves. Better list all the 243 situations and 
know that some of them will never be encountered. 
      
### Strategy example
      
	                Situation                      Action
	North  | South  | East   | West   | Current |          
	Empty  | Empty  | Empty  | Empty  | Empty   | Move North    
	Empty  | Empty  | Empty  | Empty  | Can     | Move East    
	Empty  | Empty  | Empty  | Empty  | Wall    | Move Random     
	Empty  | Empty  | Empty  | Can    | Empty   | Pick Up Can    
	...
	Empty  | Empty  | Can    | Wall   | Empty   | Move West
	...
	Wall   | Wall   | Wall   | Wall   | Wall    | Stay Put

I never said that the above is a _good_ strategy. Finding a good strategy isn't our job; It's the job of the genetic
 algorithm.
 
 
## Goal

Our goal is to create a genetic algorithm to evolve strategies for Robby. Each individual in the population is a 
strategy—a listing of the actions that correspond to each possible situation. That is, given a strategy such as the 
one above, an individual to be evolved by the Genetic Algorithm is just a listing of the 243 actions in the rightmost
  column, in the order given: Move North Move East Move Random Pick Up Can ... Move West ... Stay Put
  
The Genetic Algorithm remembers that the first action in the string (here Move North) goes with the first situation
("Empty Empty Empty Empty Emtpy"), the second action (here Move East) goes with the second situation ("Empty Empty 
Empty Empty Can"), and so on. In other words, we don't have to explicitly list the situations corresponding to these
 actions; instead the GA remembers the order in which they are listed. For example, suppose Robby happened to 
 observe that he was in the following situation: 
 
	North  | South  | East   | West   | Current |          
	Empty  | Empty  | Empty  | Empty  | Can     | 

We build into the GA the knowledge that this is situation number 2. It would look at the strategy table and see that 
the action in position 2 is "Move East". Robby moves east, and then observes his next situation; the GA again looks 
up the corresponding actin in the table, and so forth. 
  
## Steps to build "a" genetic algorithm
  
There are different ways to build a genetic algorithm, but here is one of them. 
  
### 1. Generate the initial population
 
The GA starts with an initial population of 200 random individuals (strategies). Each individual strategy is a list 
of 243 "genes". Each gene is a number between 0 and 6:

	0: Move North
	1: Move South
	2: Move East
	3: Move West
	4: Stay Put
	5: Pick Up Can
	6: Random Move

In the initial population, these numbers are filled in at random. For this (and all other probabilistic or random
 choicse), the GA uses a pseudo-random-number generator. Repeat the following for 1,000 generations: 
 
### 2. Calculate the fitness of each individual in the population. 

In this algorithm, the fitness of a strategy is determined by seeing how well the strategy lets Robby do on 100 
different **cleaning sessions**. A cleaning session consists of puting Robby at site 0,0, and throwing down a bunch 
of cans at random. Robby then follows the strategy for 200 actions in each session. The score of the strategy in each
session is the number of rewarding points Robby accumulates minus the total fines he incurs. The strategy fitness 
is its average score over 100 different cleaning sessions, each of which has a different configuration of cans. 

**Individual examples (strategies genome):**
 
	**Individual 1:** 3452365464324543654....45363534543 (up to 243 characters)
	**Individual 2:** 4356353453245634321....12342345312 (up to 243 characters)
	
	...
	
	**Individual 200:** 123465343134356242....65341315431 (up to 243 characters)
 
### 3. Apply evolution 
 
The currently population of strategies to create a new population. That is, repeat the following until the new 
  population has 200 individuals: 
   
1. Choose two parent individuals from the current population probabilistically based on fitness. That is, the 
higher a strategy's fitness, the more chance it has to be chosen as a parent. 
 
2. Mate the two parents to create two children. That is, randomly choose a position at which to split the two 
number strings; form one child by taking the numbers before that position from parent a and after that position
 from parent B, and vice versa to form the second child. 
 
3. With a small probability, mutate numbers in each child. That is, with a small probability, choose one or more
 numbers and replace them each with a randomly generated number between 0 and 6. 
 
4. Put the two children in the new population. 
 
### 4. Start over
 
Once the new population has 200 individuals, return to step 2 with this new generation. 
 
### 5. General numbers
 
The numbers we are using in this exercise are:
 
* Population: 200
* Generations: 1000
* Number of actions Robby can take in a session: 200
* Number of cleaning sessions to calculate fitness: 100
 
These numbers are arbitrary. Other numbers can be used and also provide a good strategy. 
 
## Comparison with a humam created strategy
 
Imagine the following strategy: 

* If there is a can in the current site, pick it up. 
* Otherwise, if there is a can in one of the adjacent sites, move to that site. 
* If there are multiple adjacent sites with cans, choose one of them. 
* Otherwise, choose a random direction to move in. 

This strategy is not that smart since it can make Robby get stuck cycling around empty sites. 

This strategy was tested on 10,000 cleaning sessions, and found that its average (per-session) score was 346. Given
that at the beginning of each session, about 50%, or 50, of the sites contain cans, the maximum possible score
for any strategy is approximately 500. 

After selecting the highest-fitness individual in the final generation, and also testing it on 10,000 new and 
 different cleaning sessions, its average (per-session) was approximately 483—that is, nearly optimal. 
 