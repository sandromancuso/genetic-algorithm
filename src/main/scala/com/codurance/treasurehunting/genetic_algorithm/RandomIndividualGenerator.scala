package com.codurance.treasurehunting.genetic_algorithm

class RandomIndividualGenerator {

	def generate(): Individual = {
		val representation = 1 to 243 map(n => RandomAction.next())
		Individual(representation.toList)
	}

}
