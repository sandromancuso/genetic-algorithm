package com.codurance.treasurehunting.genetic_algorithm

import com.codurance.UnitSpec
import org.mockito.BDDMockito
import org.mockito.BDDMockito.given
import org.mockito.Mockito.verify

class TreasureHuntingGAShould extends UnitSpec {

	val INITIAL_POPULATION = new Population
	val FIT_POPULATION = new Population
	val TWO_HUNDRED_INDIVIDUALS = 200

	trait context {
		val randomPopulationGenerator = mock[RandomPopulationGenerator]
		val evolution = mock[Evolution]
		val treasureHuntingGA = new TreasureHuntingGA(randomPopulationGenerator, evolution)
	}

	"generate 200 random individuals at start" in new context {
		treasureHuntingGA generateFittestIndividual()

		verify(randomPopulationGenerator) populationWith TWO_HUNDRED_INDIVIDUALS
	}

	"evolve initial population" in new context {
		given(randomPopulationGenerator populationWith TWO_HUNDRED_INDIVIDUALS) willReturn INITIAL_POPULATION

		treasureHuntingGA generateFittestIndividual()

		verify(evolution) nextGenerationsFor INITIAL_POPULATION
	}

}
