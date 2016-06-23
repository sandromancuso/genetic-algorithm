package com.codurance.treasurehunting.genetic_algorithm.evolution

import java.util.concurrent.atomic.AtomicInteger

import com.codurance.treasurehunting.domain.Individual
import com.codurance.treasurehunting.genetic_algorithm.map.{TreasureMapGenerator, TreasureHuntingSession, TreasureMap}

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

