package com.codurance.treasurehunting.genetic_algorithm.initial_population

import com.codurance.treasurehunting.domain.{Individual, RandomAction}

class RandomIndividualGenerator {

	def generate(): Individual = {
		val actions = 1 to 243 map(n => RandomAction.next())
		Individual(actions)
	}

}
