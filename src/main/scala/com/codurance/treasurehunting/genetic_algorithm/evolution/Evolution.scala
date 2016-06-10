package com.codurance.treasurehunting.genetic_algorithm.evolution

import com.codurance.treasurehunting.domain.Population
import com.codurance.treasurehunting.genetic_algorithm.GAConfig

class Evolution(gaConfig: GAConfig, fitnessCalculator: FitnessCalculator) {

	def nextGenerationsFor(population: Population): Population = {
		var evolvedPopulation = population
		1 to gaConfig.generations foreach { n =>
			evolvedPopulation = fitnessCalculator calculateFitnessFor(evolvedPopulation)
		}
		evolvedPopulation
	}
}
