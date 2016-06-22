package com.codurance.treasurehunting.genetic_algorithm.evolution

import com.codurance.UnitSpec
import com.codurance.treasurehunting.domain.Action._
import com.codurance.treasurehunting.domain.{Population, Individual}

class GenerationShould extends UnitSpec {

	trait RandomSplit {
		def randomSplitPosition() = 243 / 2
	}

	trait context {
		val generation = new Generation with RandomSplit {
			override def randomSplitPosition() = 243 / 2
		}
	}

	"generate new population with the same size of the orginal population" in new context {
		val individual_1 = Individual(50, Seq(MOVE_EAST, MOVE_NORTH, RANDOM_MOVE, MOVE_SOUTH))
		val individual_2 = Individual(80, Seq(MOVE_NORTH, MOVE_EAST, MOVE_SOUTH, RANDOM_MOVE))
		val individual_3 = Individual(90, Seq(MOVE_SOUTH, MOVE_NORTH, MOVE_EAST, RANDOM_MOVE))
		val individual_4 = Individual(70, Seq(MOVE_NORTH, RANDOM_MOVE, MOVE_EAST, MOVE_SOUTH))
		val unfitPopulation = Population(individual_1, individual_2, individual_3, individual_4)

		val fitPopulation = generation next unfitPopulation

		fitPopulation.individuals.size should be(unfitPopulation.individuals.size)
	}

	"""
	  | generate a new population mating the fittest individuals:
	  |
	  | E.g.
	  |
	  | INDIVIDUAL | FITNESSS | DNA (Actions)
	  |     A      |    50    | MOVE_EAST, MOVE_NORTH, RANDOM_MOVE, MOVE_SOUTH
	  |     B      |    80    | MOVE_NORTH, MOVE_EAST, MOVE_SOUTH, RANDOM_MOVE
	  |     C      |    90    | MOVE_SOUTH, MOVE_NORTH, MOVE_EAST, RANDOM_MOVE
	  |     D      |    70    | MOVE_NORTH, RANDOM_MOVE, MOVE_EAST, MOVE_SOUTH
	  |
	  | Next generation will have children from (C, B) and (D, A) as they are sorted by fitness.
	  | The DNA for each parent should be cut at the same point and each child's DNA should be
	  | composed by one part of the dad's DNA and one part of the mum's DNA.
	  |
	  | E.g
	  |
	  | PARENTS | FITNESSS | DNA (Actions)
	  |    C    |    90    | MOVE_SOUTH, MOVE_NORTH, MOVE_EAST, RANDOM_MOVE
	  |    B    |    80    | MOVE_NORTH, MOVE_EAST, MOVE_SOUTH, RANDOM_MOVE
	  |
	  | CHILDREN | DNA (Actions)
	  |     1    | MOVE_SOUTH (C), MOVE_NORTH (C), MOVE_SOUTH (B), RANDOM_MOVE (B)
	  |     2    | MOVE_NORTH (B), MOVE_EAST (B), MOVE_EAST (C), RANDOM_MOVE (C)
	  |
	""".stripMargin in new context {
		val individual_1 = Individual(50, Seq(MOVE_EAST, MOVE_NORTH, RANDOM_MOVE, MOVE_SOUTH))
		val individual_2 = Individual(80, Seq(MOVE_NORTH, MOVE_EAST, MOVE_SOUTH, RANDOM_MOVE))
		val individual_3 = Individual(90, Seq(MOVE_SOUTH, MOVE_NORTH, MOVE_EAST, RANDOM_MOVE))
		val individual_4 = Individual(70, Seq(MOVE_NORTH, RANDOM_MOVE, MOVE_EAST, MOVE_SOUTH))
		val unfitPopulation = Population(individual_1, individual_2, individual_3, individual_4)

		val fitPopulation = generation next unfitPopulation

		fitPopulation.individuals(0) should be(Individual(actions = Seq(MOVE_SOUTH, MOVE_NORTH, MOVE_SOUTH, RANDOM_MOVE)))
		fitPopulation.individuals(1) should be(Individual(actions = Seq(MOVE_NORTH, MOVE_EAST, MOVE_EAST, RANDOM_MOVE)))
		fitPopulation.individuals(2) should be(Individual(actions = Seq(MOVE_NORTH, RANDOM_MOVE, RANDOM_MOVE, MOVE_SOUTH)))
		fitPopulation.individuals(3) should be(Individual(actions = Seq(MOVE_EAST, MOVE_NORTH, MOVE_EAST, MOVE_SOUTH)))
	}

}
