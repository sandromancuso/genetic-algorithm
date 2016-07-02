package com.codurance.treasurehunting.genetic_algorithm.evolution

import com.codurance.UnitSpec
import com.codurance.treasurehunting.domain.Action._
import com.codurance.treasurehunting.domain.{Population, Individual}
import org.mockito.BDDMockito
import org.mockito.BDDMockito.given

class GenerationShould extends UnitSpec {

	val INDIVIDUAL_1 = Individual(50, Seq(MOVE_EAST, MOVE_NORTH, RANDOM_MOVE, MOVE_SOUTH))
	val INDIVIDUAL_2 = Individual(80, Seq(MOVE_NORTH, MOVE_EAST, MOVE_SOUTH, RANDOM_MOVE))
	val INDIVIDUAL_3 = Individual(90, Seq(MOVE_SOUTH, MOVE_NORTH, MOVE_EAST, RANDOM_MOVE))
	val INDIVIDUAL_4 = Individual(70, Seq(MOVE_NORTH, RANDOM_MOVE, MOVE_EAST, MOVE_SOUTH))
	val UNFIT_POPULATION = Population(INDIVIDUAL_1, INDIVIDUAL_2, INDIVIDUAL_3, INDIVIDUAL_4)

	trait context {
		val parentSelection = mock[ParentSelection]
		val mutation = new Mutation {
			override def mutate(individual: Individual): Individual = individual
		}

		given(parentSelection nextParent()) willReturn(INDIVIDUAL_3, INDIVIDUAL_2, INDIVIDUAL_4, INDIVIDUAL_1)

		val generation = new Generation(mutation) {
			override def randomGeneIndex(individual: Individual): Int = individual.actions.size / 2

			override def parentSelectionFor(population: Population): ParentSelection = parentSelection
		}
	}

	"generate new population with the same size of the orginal population" in new context {
		val fitPopulation = generation next UNFIT_POPULATION

		fitPopulation.individuals.size should be(UNFIT_POPULATION.individuals.size)
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
		val fitPopulation = generation next UNFIT_POPULATION

		fitPopulation.individuals(0) should be(Individual(actions = Seq(MOVE_SOUTH, MOVE_NORTH, MOVE_SOUTH, RANDOM_MOVE)))
		fitPopulation.individuals(1) should be(Individual(actions = Seq(MOVE_NORTH, MOVE_EAST, MOVE_EAST, RANDOM_MOVE)))
		fitPopulation.individuals(2) should be(Individual(actions = Seq(MOVE_NORTH, RANDOM_MOVE, RANDOM_MOVE, MOVE_SOUTH)))
		fitPopulation.individuals(3) should be(Individual(actions = Seq(MOVE_EAST, MOVE_NORTH, MOVE_EAST, MOVE_SOUTH)))
	}

}
