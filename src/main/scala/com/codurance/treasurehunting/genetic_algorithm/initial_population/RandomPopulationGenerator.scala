package com.codurance.treasurehunting.genetic_algorithm.initial_population

import com.codurance.treasurehunting.domain.Population

class RandomPopulationGenerator(randomIndividualGenerator: RandomIndividualGenerator) {

	def populationWith(numberOfIndividuals: Int): Population = {
		val individuals = 1 to numberOfIndividuals map(_ => randomIndividualGenerator.generate())
		return Population(individuals:_*)
	}

}
