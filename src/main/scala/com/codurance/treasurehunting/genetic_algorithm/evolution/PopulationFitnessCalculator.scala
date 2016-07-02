package com.codurance.treasurehunting.genetic_algorithm.evolution

import com.codurance.treasurehunting.domain.Population
import com.codurance.treasurehunting.genetic_algorithm.GAConfig
import com.codurance.treasurehunting.genetic_algorithm.map.TreasureMapGenerator

class PopulationFitnessCalculator(gAConfig: GAConfig,
                                  treasureMapGenerator: TreasureMapGenerator,
                                  individualFitnessCalculator: IndividualFitnessCalculator) {

	def calculateFitnessFor(population: Population): Population = {
		val treasureMaps = treasureMapGenerator.randomMaps(gAConfig.numberOfHuntingSessions)

		val fitIndividuals = population.individuals.par.map(individual => {
			val averageFitness = individualFitnessCalculator averageFitnessFor(individual, treasureMaps)
			individual.copy(fitness = averageFitness)
		})

		Population(fitIndividuals.toList:_*)
	}

}