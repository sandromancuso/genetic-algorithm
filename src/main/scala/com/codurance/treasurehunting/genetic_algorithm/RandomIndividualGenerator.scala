package com.codurance.treasurehunting.genetic_algorithm

class RandomIndividualGenerator {

	def generate(): Individual = {
		val actions = 1 to 243 map(n => RandomAction.next())
		Individual(actions)
	}

}
