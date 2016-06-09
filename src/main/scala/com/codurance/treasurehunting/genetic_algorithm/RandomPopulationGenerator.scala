package com.codurance.treasurehunting.genetic_algorithm

class RandomPopulationGenerator(randomIndividualGenerator: RandomIndividualGenerator) {

	def populationWith(numberOfIndividuals: Int): Population = {
		val individuals = 1 to numberOfIndividuals map(_ => randomIndividualGenerator.generate())
		return Population(individuals:_*)
	}

}
