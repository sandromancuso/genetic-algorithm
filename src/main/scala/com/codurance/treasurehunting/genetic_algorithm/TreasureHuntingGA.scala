package com.codurance.treasurehunting.genetic_algorithm

class TreasureHuntingGA(randomPopulationGenerator: RandomPopulationGenerator,
                        evolution: Evolution,
                        numberOfIndividuals: Int) {

	def generateFittestIndividual(): Individual = {
		val initialPopulation = randomPopulationGenerator populationWith numberOfIndividuals
		val evolvedPopulation = evolution nextGenerationsFor initialPopulation

		evolvedPopulation fittestIndividual()
	}

}
