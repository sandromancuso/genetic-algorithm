package com.codurance.treasurehunting.genetic_algorithm.evolution

import com.codurance.treasurehunting.domain.{Action, Site, Individual, Population}
import com.codurance.treasurehunting.genetic_algorithm.GAConfig
import com.codurance.treasurehunting.genetic_algorithm.map.{TreasureHuntingSession, TreasureMap, TreasureMapGenerator}

class PopulationFitnessCalculator(individualFitnessCalculator: IndividualFitnessCalculator) {

	def calculateFitnessFor(population: Population): Population = {
		var fitIndividuals: Seq[Individual] = Seq()

		population.individuals.par foreach { individual =>
			val averageFitness = individualFitnessCalculator averageFitnessFor individual
			val fitIndividual = individual.copy(fitness = averageFitness)
			fitIndividuals = fitIndividuals :+ fitIndividual
		}

		val fitPopulation = Population(fitIndividuals: _*)
		fitPopulation
	}

}

class IndividualFitnessCalculator(gaConfig: GAConfig,
                                  mapGenerator: TreasureMapGenerator,
                                  individualFitnessForMapCalculator: IndividualFitnessForMapCalculator) {

	def averageFitnessFor(individual: Individual): Int = {
		var fitnessList: List[Int] = List()

		(1 to gaConfig.numberOfHuntingSessions).par foreach { _ =>
			val map = mapGenerator next()
			fitnessList = fitnessList :+ individualFitnessForMapCalculator.calculateFitness(map, individual)
		}

		val fitnessAverage: Int = fitnessList.sum / fitnessList.length
		fitnessAverage
	}

}

class IndividualFitnessForMapCalculator {

	def calculateFitness(treasureMap: TreasureMap, individual: Individual): Int =
		new TreasureHuntingSession(treasureMap, individual).run()

}
