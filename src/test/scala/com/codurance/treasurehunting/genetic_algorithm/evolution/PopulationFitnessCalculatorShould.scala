package com.codurance.treasurehunting.genetic_algorithm.evolution

import com.codurance.UnitSpec
import com.codurance.treasurehunting.domain.Action.{MOVE_NORTH, MOVE_SOUTH}
import com.codurance.treasurehunting.domain.{Individual, Population}
import org.mockito.BDDMockito.given

class PopulationFitnessCalculatorShould extends UnitSpec {

	val UNFIT_INDIVIDUAL_1 = Individual(Seq(MOVE_NORTH), fitness = 0)
	val UNFIT_INDIVIDUAL_2 = Individual(Seq(MOVE_SOUTH), fitness = -10)
	val UNFIT_POPULATION = Population(UNFIT_INDIVIDUAL_1, UNFIT_INDIVIDUAL_2)

	trait context {
		val individualFitnessCalculator = mock[IndividualFitnessCalculator]
		val populationFitnessCalculator = new PopulationFitnessCalculator(individualFitnessCalculator)
	}

	"calculate fitness for each individual in a population" in new context {
		given(individualFitnessCalculator averageFitnessFor(UNFIT_INDIVIDUAL_1)) willReturn(80)
		given(individualFitnessCalculator averageFitnessFor(UNFIT_INDIVIDUAL_2)) willReturn(60)

		val fitPopulation = populationFitnessCalculator calculateFitnessFor UNFIT_POPULATION

		fitPopulation.individuals(0).actions should be(UNFIT_INDIVIDUAL_1.actions)
		fitPopulation.individuals(0).fitness should be(80)

		fitPopulation.individuals(1).actions should be(UNFIT_INDIVIDUAL_2.actions)
		fitPopulation.individuals(1).fitness should be(60)
	}

}
