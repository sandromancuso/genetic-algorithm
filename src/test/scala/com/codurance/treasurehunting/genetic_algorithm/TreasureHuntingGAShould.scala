package com.codurance.treasurehunting.genetic_algorithm

import com.codurance.UnitSpec
import org.mockito.BDDMockito
import org.mockito.BDDMockito.given
import org.mockito.Mockito.verify

class TreasureHuntingGAShould extends UnitSpec {

	val TWO_HUNDRED_INDIVIDUALS = List[Individual]()
	val TWO_HUNDRED_FIT_INDIVIDUALS = List[FitIndividual]()

	trait context {
		val randomIndividualGenerator = mock[RandomIndividualGenerator]
		val fitnessCalculator = mock[FitnessCalculator]
		val evolution = mock[Evolution]
		val treasureHuntingGA = new TreasureHuntingGA(randomIndividualGenerator,
														fitnessCalculator,
														evolution)
	}

	"generate 200 random individuals at start" in new context {
		treasureHuntingGA generateFittestIndividual()

		verify(randomIndividualGenerator) generate 200
	}

	"calculate the fitness for each individual" in new context {
		given(randomIndividualGenerator generate 200) willReturn TWO_HUNDRED_INDIVIDUALS

		treasureHuntingGA generateFittestIndividual()

		verify(fitnessCalculator) calculateFitnessForEach TWO_HUNDRED_INDIVIDUALS
	}

	"evolve initial population" in new context {
		given(randomIndividualGenerator generate 200) willReturn TWO_HUNDRED_INDIVIDUALS
		given(fitnessCalculator calculateFitnessForEach TWO_HUNDRED_INDIVIDUALS) willReturn TWO_HUNDRED_FIT_INDIVIDUALS

		treasureHuntingGA generateFittestIndividual()

		verify(evolution) nextGenerationFor TWO_HUNDRED_FIT_INDIVIDUALS

	}

}
