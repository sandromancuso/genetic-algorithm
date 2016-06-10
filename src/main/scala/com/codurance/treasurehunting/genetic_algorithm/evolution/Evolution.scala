package com.codurance.treasurehunting.genetic_algorithm.evolution

import com.codurance.treasurehunting.domain.Population
import com.codurance.treasurehunting.genetic_algorithm.GAConfig

class Evolution(gaConfig: GAConfig, fitnessCalculator: PopulationFitnessCalculator, generation: Generation) {

	def evolve(population: Population): Population = {
		var unfitPopulation = population
		var fitPopulation = fitnessCalculator calculateFitnessFor unfitPopulation
		1 to gaConfig.generations foreach { n =>
			unfitPopulation = generation next fitPopulation
			fitPopulation = fitnessCalculator calculateFitnessFor unfitPopulation
		}
		fitPopulation
	}
}
