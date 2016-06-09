package com.codurance.treasurehunting.genetic_algorithm

import com.codurance.treasurehunting.domain.Individual
import com.codurance.treasurehunting.genetic_algorithm.evolution.Evolution
import com.codurance.treasurehunting.genetic_algorithm.initial_population.RandomPopulationGenerator

class TreasureHuntingGA(randomPopulationGenerator: RandomPopulationGenerator,
                        evolution: Evolution,
                        numberOfIndividuals: Int) {

	def generateFittestIndividual(): Individual = {
		val initialPopulation = randomPopulationGenerator populationWith numberOfIndividuals
		val evolvedPopulation = evolution nextGenerationsFor initialPopulation

		evolvedPopulation fittestIndividual()
	}

}
