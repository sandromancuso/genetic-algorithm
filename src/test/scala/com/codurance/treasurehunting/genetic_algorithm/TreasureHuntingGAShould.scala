package com.codurance.treasurehunting.genetic_algorithm

import com.codurance.UnitSpec
import org.mockito.BDDMockito
import org.mockito.BDDMockito.given
import org.mockito.Mockito.verify

class TreasureHuntingGAShould extends UnitSpec {

	val POPULATION_OF_200_INDIVIDUALS = new Population
	val FIT_POPULATION = new Population
	val TWO_HUNDRED_INDIVIDUALS = 200

	trait context {
		val randomPopulationGenerator = mock[RandomPopulationGenerator]
		val fitnessCalculator = mock[FitnessCalculator]
		val evolution = mock[Evolution]
		val treasureHuntingGA = new TreasureHuntingGA(randomPopulationGenerator,
														fitnessCalculator,
														evolution)
	}

	"generate 200 random individuals at start" in new context {
		treasureHuntingGA generateFittestIndividual()

		verify(randomPopulationGenerator) populationWith TWO_HUNDRED_INDIVIDUALS
	}

	"calculate the fitness for each individual" in new context {
		given(randomPopulationGenerator populationWith TWO_HUNDRED_INDIVIDUALS) willReturn POPULATION_OF_200_INDIVIDUALS

		treasureHuntingGA generateFittestIndividual()

		verify(fitnessCalculator) calculateFitnessFor POPULATION_OF_200_INDIVIDUALS
	}

	"evolve initial population" in new context {
		given(randomPopulationGenerator populationWith TWO_HUNDRED_INDIVIDUALS) willReturn POPULATION_OF_200_INDIVIDUALS
		given(fitnessCalculator calculateFitnessFor POPULATION_OF_200_INDIVIDUALS) willReturn FIT_POPULATION

		treasureHuntingGA generateFittestIndividual()

		verify(evolution) nextGenerationFor FIT_POPULATION

	}

}
