package com.codurance.treasurehunting.genetic_algorithm.evolution

import com.codurance.treasurehunting.domain.{Action, Population}
import com.codurance.treasurehunting.genetic_algorithm.GAConfig

class Evolution(gaConfig: GAConfig,
                populationFitnessCalculator: PopulationFitnessCalculator, generation: Generation) {

	def evolve(population: Population): Population = {
		var nextPopulation: Population = null
		var fitPopulation = populationFitnessCalculator calculateFitnessFor population
		1 to gaConfig.generations foreach { n =>
			nextPopulation = generation next fitPopulation
			fitPopulation = populationFitnessCalculator calculateFitnessFor nextPopulation
			val stayPuts = fitPopulation.fittestIndividual().actions.count(_ == Action.STAY_PUT)
			println(s"Generation: ${n} - Fittest: ${fitPopulation.fittestIndividual().fitness} - Stay puts: ${stayPuts}")
		}
		fitPopulation
	}
}
