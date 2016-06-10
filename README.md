Genetic Algorithm
=================

_This example was inspired by the book [Complexity: A Guided Tour](https://www.amazon.co.uk/Complexity-Guided-Tour-Melanie-Mitchell/dp/0199798109?ie=UTF8&ref_=asap_bc)_ 
by [Melanie Mitchell](https://en.wikipedia.org/wiki/Melanie_Mitchell)

# Treasure Hunting (Long) Kata

Indy's job is to collect treasures. 

* The world consists of 100 squares (sites) laid out in a 10 x 10 grid.
* Indy starts on 0,0. 
* Indy can only see 5 sites at a time: North, South, East, West, and the site he currently occupies. 
* A site can be Empty, Contain a Treasure, or be a Wall.
 
E.g. Let's assume that Indy is "I" and the treasures are "T". If Indy is as site 0,0, sites to the North and West are 
Walls.
 
	    0 1 2 3 4 5 6 7 8 9
	   ---------------------
	0 | I T   T T           | 0
	1 |       T     T       | 1
	2 |     T               | 2
	3 |   T   T             | 3
	4 |           T   T     | 4
	5 |                     | 5
	6 |     T T   T     T   | 6
	7 | T     T       T     | 7
	8 |           T         | 8
	9 |   T                 | 9
       ---------------------
	    0 1 2 3 4 5 6 7 8 9

## Actions

For each treasure hunting session, Indy can perform exactly 200 actions. Each action consists of one of the following 
seven choices:
 
* Move North 
* Move South 
* Move East 
* Move West 
* Move to Random Direction 
* Stay Put 
* Pick Up Treasure  
        
## Points 

Each action may generate a reward or a punishment. 

10 pts = If Indy picks up a tresure in the same site he is in.    
-1 pts = If he tries to pick up a treasure in an empty site.    
-5 pts = If he crashes into a wall. In this case, he should bounce back into the current site.    
   
Clearly, Indy's reward is maximised when he picks up as many treasures as possible, without crashing into any walls or 
trying to pick up a treasure in an empty site. 
    
As this is a simple problem, it's not difficult for us to come up with a good strategy for Indy to follow. However, 
the point of a genetic algorithm is that we don't need to do it; we can let computer do it for us. 
 
## Strategy and Situations
 
* **Strategy**: Set of rules providing the action for any given _situation_
* **Situation**: What Indy can see; contents of his current site, plus the content of the north, south, west and east 
sites. In each situation, Indy can do one of the seven actions described above. 
 
A strategy for Indy can be written simply as a list of all possible situations he could encounter, and for each 
 possible situation, which of the seven possible actions he should perform. 
 
### Number of possible situations
 
There are 243 different possible situations: Indy looks at 5 different sites (current, north, south, west, east) and 
  each of those sites can be in one of the 3 states: **empty**, **contain treasure**, or **wall**: 3 x 3 x 3 x 3 x 3 = 243.
    
Actually, there aren't really that many situations, since Indy will never face a situation in which his current site 
is a wall, or one in which North, South, East, and West are walls. There are other impossible situations as well, 
but we don't need to worry about them. Better list all the 243 situations and know that some of them will never be used. 
      
### Strategy example
      
	                Situation                           Action
	North  | South  | East     | West     | Current  ||          
	Empty  | Empty  | Empty    | Empty    | Empty    || Move North    
	Empty  | Empty  | Empty    | Empty    | Treasure || Move East    
	Empty  | Empty  | Empty    | Empty    | Wall     || Move Random     
	Empty  | Empty  | Empty    | Treasure | Empty    || Pick Up Treasure    
	...
	Empty  | Empty  | Treasure | Wall     | Empty    || Move West
	...
	Wall   | Wall   | Wall     | Wall     | Wall     || Stay Put

This is probably _not a good_ strategy. Finding a good strategy isn't our job; It's the job of the genetic
 algorithm.
 
 
## Goal

Our goal is to create a genetic algorithm to evolve strategies for Indy. Each individual in the population is a 
strategy, that means, actions that correspond to each possible situation. That is, given a strategy such as the 
one above, an individual to be evolved by the Genetic Algorithm is just a listing of the 243 actions in the rightmost
 column, in the order given: Move North, Move East, Move Random, Pick Up Treasure, ..., Move West, ..., Stay Put
  
The Genetic Algorithm remembers that the first action (Move North) goes with the first situation
("Empty Empty Empty Empty Emtpy"), the second action (Move East) goes with the second situation ("Empty Empty 
Empty Empty Treasure"), and so on. In other words, we don't have to explicitly list the situations corresponding to these
 actions; instead the GA remembers the order in which they are listed. For example, suppose Indy happened to 
 observe that he was in the following situation: 
 
	North  | South  | East   | West   | Current  |          
	Empty  | Empty  | Empty  | Empty  | Treasure | 

We build into the GA the knowledge that this is situation number 2. It would look at the strategy table and see that 
the action in position 2 is "Move East". Indy moves east, and then observes his next situation; the GA again looks 
up the corresponding action in the table, and so forth. 
  
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
	5: Pick Up Treasure
	6: Random Move

In the initial population, these numbers are randomly generated. Repeat the following for 1,000 generations: 
 
### 2. Calculate the fitness of each individual in the population. 

In this algorithm, the fitness of a strategy is determined by seeing how well the strategy lets Indy do on 100 
different **treasure hunting sessions**. A treasure hunting session consists of putting Indy at site 0,0, and throwing down a bunch 
of treasures at random. Indy then follows the strategy for 200 actions in each session. The score of the strategy in each
session is the number of rewarding points Indy accumulates minus punishments. The strategy fitness is its average 
score over 100 different treasure hunting sessions, each of which has a different configuration of treasures. 

**Individual examples (strategies genome):**
 
	**Individual 1:** 3452365464324543654....45363534543 (up to 243 characters)
	**Individual 2:** 4356353453245634321....12342345312 (up to 243 characters)
	
	...
	
	**Individual 200:** 123465343134356242....65341315431 (up to 243 characters)
 
### 3. Apply evolution 
 
The currently population of strategies to create a new population. That is, repeat the following until the new 
  population has 200 individuals: 
   
1. Choose two parent individuals from the current population probabilistically based on fitness. The 
higher a strategy's fitness, the more chance it has to be chosen as a parent. 
 
2. Mate the two parents to create two children. Randomly choose a position at which to split the two 
number strings; form one child by taking the numbers before that position from parent a and after that position
 from parent B, and vice versa to form the second child. 
 
3. With a small probability, mutate numbers in each child. With a small probability, choose one or more
 numbers and replace them each with a randomly generated number between 0 and 6. 
 
4. Put the two children in the new population. 
 
### 4. Start over
 
Once the new population has 200 individuals, return to step 2 with this new generation. 
 
### 5. General numbers
 
The numbers we are using in this exercise are:
 
* Population: 200
* Generations: 1000
* Number of actions Indy can take in a session: 200
* Number of treasure hunting sessions to calculate fitness: 100
 
These numbers are arbitrary. Other numbers can be used. 
 
## Comparison with a human created strategy
 
Imagine the following strategy: 

* If there is a treasure in the current site, pick it up. 
* Otherwise, if there is a treasure in one of the adjacent sites, move to that site. 
* If there are multiple adjacent sites with treasure, choose one of them. 
* Otherwise, choose a random direction to move in. 

This strategy is not that smart since it can make Indy get stuck cycling around empty sites. 

[Melanie Mitchell](https://en.wikipedia.org/wiki/Melanie_Mitchell) tested this strategy on 10,000 treasure hunting sessions, 
and found that its average (per-session) score was 346. Given that at the beginning of each session, about 50%, or 50, 
of the sites contain a treasure, the maximum possible score for any strategy is approximately 500. 

After selecting the highest-fitness individual in the final generation, and also testing it on 10,000 new and 
 different treasure hunting sessions, its average (per-session) was approximately 483—that is, nearly optimal. 
 