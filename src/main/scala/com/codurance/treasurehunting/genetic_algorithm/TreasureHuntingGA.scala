package com.codurance.treasurehunting.genetic_algorithm

class TreasureHuntingGA(randomIndividualGenerator: RandomIndividualGenerator,
                        fitnessCalculator: FitnessCalculator,
                        evolution: Evolution) {

	def generateFittestIndividual(): Unit = {
		val initialPopulation = randomIndividualGenerator generate(200)
		val fitIndividuals = fitnessCalculator calculateFitnessForEach(initialPopulation)

		evolution nextGenerationFor fitIndividuals
	}

}
