package com.codurance.treasurehunting.genetic_algorithm.evolution

import com.codurance.treasurehunting.domain.{Individual, Population}
import com.codurance.treasurehunting.genetic_algorithm.GAConfig
import com.codurance.treasurehunting.genetic_algorithm.map.TreasureMapGenerator

class PopulationFitnessCalculator(individualFitnessCalculator: IndividualFitnessCalculator) {

	def calculateFitnessFor(population: Population): Population = {
		var fitIndividuals: Seq[Individual] = Seq()

		population.individuals foreach { individual =>
			val averageFitness = individualFitnessCalculator averageFitnessFor individual
			fitIndividuals = fitIndividuals :+ individual.copy(fitness = averageFitness)
		}

		Population(fitIndividuals: _*)
	}

}

class IndividualFitnessCalculator(gaConfig: GAConfig, mapGenerator: TreasureMapGenerator) {

	def averageFitnessFor(individual: Individual): Int = {
		var fitnessList: List[Int] = List()

		1 to gaConfig.numberOfHuntingSessions foreach { _ =>
			val map = mapGenerator next()
			fitnessList = fitnessList :+ map.fitnessFor(individual)
		}

		val fitnessAverage: Int = fitnessList.sum / fitnessList.length
		fitnessAverage
	}

}
