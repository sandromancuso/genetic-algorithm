package com.codurance.treasurehunting.genetic_algorithm

class TreasureHuntingGA(randomIndividualGenerator: RandomIndividualGenerator,
                        fitnessCalculator: FitnessCalculator) {

	def generateFittestIndividual(): Unit = {
		val initialPopulation = randomIndividualGenerator generate(200)
		fitnessCalculator calculateFitnessForEach(initialPopulation)
	}

}
