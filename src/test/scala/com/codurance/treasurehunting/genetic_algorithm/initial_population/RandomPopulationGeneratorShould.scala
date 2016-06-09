package com.codurance.treasurehunting.genetic_algorithm.initial_population

import com.codurance.UnitSpec
import com.codurance.treasurehunting.domain.{Individual, Population, RandomAction}
import org.mockito.BDDMockito.given

class RandomPopulationGeneratorShould extends UnitSpec {

	val INDIVIDUAL_1 = new Individual(Seq(RandomAction.next()))
	val INDIVIDUAL_2 = new Individual(Seq(RandomAction.next()))
	val INDIVIDUAL_3 = new Individual(Seq(RandomAction.next()))
	val THREE_INDIVIDUALS = 3

	val RANDOM_INDIVIDUALS = List(INDIVIDUAL_1, INDIVIDUAL_2, INDIVIDUAL_3)

	trait context {
		val randomIndividualGenerator = mock[RandomIndividualGenerator]
		val randomPopulationGenerator = new RandomPopulationGenerator(randomIndividualGenerator)
	}

	"generate a population of random individuals" in new context {
		given(randomIndividualGenerator generate())
				.willReturn (RANDOM_INDIVIDUALS.head, RANDOM_INDIVIDUALS.tail: _*)

		val population = randomPopulationGenerator populationWith THREE_INDIVIDUALS

		population should be(Population(RANDOM_INDIVIDUALS:_*))
	}

}
