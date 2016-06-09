package com.codurance.treasurehunting.genetic_algorithm

import com.codurance.UnitSpec

class RandomIndividualGeneratorShould extends UnitSpec {

	val randomIndividualGenerator = new RandomIndividualGenerator

	"generate a random individual with a string representation of 243 chars" in {
		val individual = randomIndividualGenerator generate()

		individual.representation.length should be(243)
	}

	"generate a random individual with a string of 243 chars where each one is between 0 and 6" in {
		val individual = randomIndividualGenerator generate()

		all (individual.representation map(a => a.id)) should (be >= 0 and be <= Action.maxId)
	}

	"generate different random individuals each time" in {
		val individuals_1 = 1 to 10 map(_ => randomIndividualGenerator.generate())
		val individuals_2 = 1 to 10 map(_ => randomIndividualGenerator.generate())

		individuals_1 should not be(individuals_2)
	}

}
