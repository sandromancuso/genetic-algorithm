package com.codurance.treasurehunting.genetic_algorithm.evolution

import com.codurance.treasurehunting.domain.Individual
import com.codurance.treasurehunting.genetic_algorithm.map.{TreasureHuntingSession, TreasureMap}

class IndividualFitnessCalculator(individualFitnessForMapCalculator: IndividualFitnessForMapCalculator) {

	def averageFitnessFor(individual: Individual, treasureMaps: Seq[TreasureMap]): Int =
		treasureMaps.par
			.map(individualFitnessForMapCalculator.calculateFitness(_, individual))
			.sum / treasureMaps.size

}

class IndividualFitnessForMapCalculator {

	def calculateFitness(treasureMap: TreasureMap, individual: Individual): Int =
		new TreasureHuntingSession(treasureMap, individual).run()

}

