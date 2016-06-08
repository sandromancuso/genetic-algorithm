package com.codurance.treasurehunting.genetic_algorithm

class TreasureHuntingGA(randomIndividualGenerator: RandomIndividualGenerator) {

	def generateFittestIndividual(): Unit = {
		randomIndividualGenerator generate(200)
	}

}
