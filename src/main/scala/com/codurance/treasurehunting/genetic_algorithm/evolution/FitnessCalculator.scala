package com.codurance.treasurehunting.genetic_algorithm.evolution

import java.util.concurrent.atomic.AtomicInteger

import com.codurance.treasurehunting.domain.{Action, Site, Individual, Population}
import com.codurance.treasurehunting.genetic_algorithm.GAConfig
import com.codurance.treasurehunting.genetic_algorithm.map.{TreasureHuntingSession, TreasureMap, TreasureMapGenerator}

import scala.collection.mutable

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

class IndividualFitnessCalculator(numberOfHuntingSessions: Int,
                                  mapGenerator: TreasureMapGenerator,
                                  individualFitnessForMapCalculator: IndividualFitnessForMapCalculator) {

	def averageFitnessFor(individual: Individual): Int = {
		val fitness = new AtomicInteger(0)

		(1 to numberOfHuntingSessions).par foreach { _ =>
			val map = mapGenerator next()
			fitness.addAndGet(individualFitnessForMapCalculator.calculateFitness(map, individual))
		}

		fitness.get() / numberOfHuntingSessions
	}

}

class IndividualFitnessForMapCalculator {

	def calculateFitness(treasureMap: TreasureMap, individual: Individual): Int =
		new TreasureHuntingSession(treasureMap, individual).run()

}
