package com.codurance.treasurehunting.genetic_algorithm.evolution

import com.codurance.UnitSpec
import com.codurance.treasurehunting.domain.Action.{MOVE_NORTH, MOVE_SOUTH}
import com.codurance.treasurehunting.domain.{Individual, Population}
import org.mockito.BDDMockito.given

class PopulationFitnessCalculatorShould extends UnitSpec {

	val UNFIT_INDIVIDUAL_1 = Individual(0, Seq(MOVE_NORTH))
	val UNFIT_INDIVIDUAL_2 = Individual(-10, Seq(MOVE_SOUTH))
	val UNFIT_POPULATION = Population(UNFIT_INDIVIDUAL_1, UNFIT_INDIVIDUAL_2)

	trait context {
		val individualFitnessCalculator = mock[IndividualFitnessCalculator]
		val populationFitnessCalculator = new PopulationFitnessCalculator(individualFitnessCalculator)
	}

	"calculate fitness for each individual in a population" in new context {
		given(individualFitnessCalculator averageFitnessFor UNFIT_INDIVIDUAL_1) willReturn 80
		given(individualFitnessCalculator averageFitnessFor UNFIT_INDIVIDUAL_2) willReturn 60

		val fitPopulation = populationFitnessCalculator calculateFitnessFor UNFIT_POPULATION

		fitPopulation.size should be (UNFIT_POPULATION.size)
		fitPopulation.individuals should contain(Individual(80, UNFIT_INDIVIDUAL_1.actions))
		fitPopulation.individuals should contain(Individual(60, UNFIT_INDIVIDUAL_2.actions))
	}

}
