package com.codurance.treasurehunting.genetic_algorithm

import com.codurance.UnitSpec
import org.mockito.BDDMockito
import org.mockito.BDDMockito.given
import org.mockito.Mockito.verify

class TreasureHuntingGAShould extends UnitSpec {

	val INITIAL_POPULATION = new Population
	val FIT_POPULATION = new Population
	val FITTEST_INDIVIDUAL = new Individual
	val TWO_HUNDRED_INDIVIDUALS = 200

	trait context {
		val randomPopulationGenerator = mock[RandomPopulationGenerator]
		val evolution = mock[Evolution]
		val evolvedPopulation = mock[Population]
		val treasureHuntingGA = new TreasureHuntingGA(randomPopulationGenerator, evolution, TWO_HUNDRED_INDIVIDUALS)
	}

	"return the fittest individual after evolutions of initial population" in new context {
		given(randomPopulationGenerator populationWith TWO_HUNDRED_INDIVIDUALS) willReturn INITIAL_POPULATION
		given(evolution nextGenerationsFor INITIAL_POPULATION) willReturn evolvedPopulation
		given(evolvedPopulation fittestIndividual()) willReturn FITTEST_INDIVIDUAL

		val fittestIndividual = treasureHuntingGA generateFittestIndividual()

		fittestIndividual should be(FITTEST_INDIVIDUAL)

	}

}
