package com.codurance.treasurehunting.genetic_algorithm.evolution

import com.codurance.UnitSpec
import com.codurance.treasurehunting.domain.{Population, Individual}

class ParentSelectionShould extends UnitSpec {

	"generate ascending ordered list of individuals per fitness" in {
		val individual_1 = Individual(fitness = 50, actions = Seq())
		val individual_2 = Individual(fitness = 70, actions = Seq())
		val individual_3 = Individual(fitness = 30, actions = Seq())
		val population = Population(individual_1, individual_2, individual_3)

		val parentSelection = new ParentSelection(population)

		parentSelection.individualsOrderedByFitnessAsc should be (List(individual_3, individual_1, individual_2))
	}

	"generate a map where the key is the index and the value is the individual" in {
		val individual_1 = Individual(fitness = 50, actions = Seq())
		val individual_2 = Individual(fitness = 70, actions = Seq())
		val individual_3 = Individual(fitness = 30, actions = Seq())
		val population = Population(individual_1, individual_2, individual_3)

		val parentSelection = new ParentSelection(population)

		parentSelection.indexIndividualMap should be (Map(0 -> individual_3, 1 -> individual_1, 2 -> individual_2))
	}

	"generate an index list containing a number of repeated elements according to index" in {
		val individual_1 = Individual(fitness = 50, actions = Seq())
		val individual_2 = Individual(fitness = 70, actions = Seq())
		val individual_3 = Individual(fitness = 30, actions = Seq())
		val population = Population(individual_1, individual_2, individual_3)

		val parentSelection = new ParentSelection(population)

		println(parentSelection.indexList)

		parentSelection.indexList.count(_ == 0) should be (1)
		parentSelection.indexList.count(_ == 1) should be (2)
		parentSelection.indexList.count(_ == 2) should be (3)
	}
}
