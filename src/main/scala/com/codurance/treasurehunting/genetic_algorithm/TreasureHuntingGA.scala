package com.codurance.treasurehunting.genetic_algorithm

class TreasureHuntingGA(randomPopulationGenerator: RandomPopulationGenerator,
                        fitnessCalculator: FitnessCalculator,
                        evolution: Evolution) {

	def generateFittestIndividual(): Unit = {
		val initialPopulation = randomPopulationGenerator populationWith(200)
		val fitIndividuals = fitnessCalculator calculateFitnessFor(initialPopulation)

		evolution nextGenerationFor fitIndividuals

		// At this point, I think I should move the FitnessCalculator
		// to Evolution. Evolution should receive the initial population
		// and evolve it (calculate the next generations) according to the
		// number of generations specified somewhere (param?).
	}

}
