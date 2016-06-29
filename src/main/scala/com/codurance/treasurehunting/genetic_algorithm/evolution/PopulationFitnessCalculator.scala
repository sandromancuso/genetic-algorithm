package com.codurance.treasurehunting.genetic_algorithm.evolution

import com.codurance.treasurehunting.domain.{Individual, Population}
import com.codurance.treasurehunting.genetic_algorithm.GAConfig
import com.codurance.treasurehunting.genetic_algorithm.map.{TreasureMap, TreasureMapGenerator}

import scala.collection.mutable

class PopulationFitnessCalculator(gAConfig: GAConfig,
                                  treasureMapGenerator: TreasureMapGenerator,
                                  individualFitnessCalculator: IndividualFitnessCalculator) {

	def calculateFitnessFor(population: Population): Population = {
		val treasureMaps = generateTreasureMaps()

		val fitIndividuals = population.individuals.par.map(individual => {
			val averageFitness = individualFitnessCalculator averageFitnessFor(individual, treasureMaps)
			individual.copy(fitness = averageFitness)
		})

		Population(fitIndividuals.toList:_*)
	}

	private def generateTreasureMaps() =
		(1 to gAConfig.numberOfHuntingSessions).toList.par.map(_ => treasureMapGenerator.next()).toList

}