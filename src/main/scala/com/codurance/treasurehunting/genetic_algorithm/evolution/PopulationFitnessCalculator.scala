package com.codurance.treasurehunting.genetic_algorithm.evolution

import com.codurance.treasurehunting.domain.{Individual, Population}

class PopulationFitnessCalculator(individualFitnessCalculator: IndividualFitnessCalculator) {

	def calculateFitnessFor(population: Population): Population = {
		var fitIndividuals: Seq[Individual] = Seq()

		population.individuals.par foreach { individual =>
			val averageFitness = individualFitnessCalculator averageFitnessFor individual
			val fitIndividual = individual.copy(fitness = averageFitness)
			fitIndividuals = fitIndividuals :+ fitIndividual
		}

		Population(fitIndividuals: _*)
	}

}