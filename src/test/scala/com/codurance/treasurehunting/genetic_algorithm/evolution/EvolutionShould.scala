package com.codurance.treasurehunting.genetic_algorithm.evolution

import com.codurance.UnitSpec
import com.codurance.treasurehunting.domain.Action.{MOVE_EAST, MOVE_NORTH, PICK_UP_TREASURE}
import com.codurance.treasurehunting.domain.{Individual, Population}
import com.codurance.treasurehunting.genetic_algorithm.GAConfig
import org.mockito.BDDMockito.given
import org.mockito.Mockito.verify

class EvolutionShould extends UnitSpec {

	val INITIAL_POPULATION = Population(Individual(Seq(MOVE_NORTH)))
	val FIRST_GENERATION   = Population(Individual(Seq(MOVE_EAST)))
	val SECOND_GENERATION  = Population(Individual(Seq(PICK_UP_TREASURE)))

	trait context {
		val gaConfig = new GAConfig(generations = 2)
		val fitnessCalculator = mock[FitnessCalculator]
		val evolution = new Evolution(gaConfig, fitnessCalculator)
	}

	"calculate fitness of populations according to the number of generations" in new context {
		given(fitnessCalculator calculateFitnessFor(INITIAL_POPULATION)) willReturn(FIRST_GENERATION)
		given(fitnessCalculator calculateFitnessFor(FIRST_GENERATION)) willReturn(SECOND_GENERATION)

		evolution nextGenerationsFor INITIAL_POPULATION

		verify(fitnessCalculator) calculateFitnessFor(INITIAL_POPULATION)
		verify(fitnessCalculator) calculateFitnessFor(FIRST_GENERATION)
	}

	"return the last generation according to the number of generations to be evolved" in new context {
		given(fitnessCalculator calculateFitnessFor(INITIAL_POPULATION)) willReturn(FIRST_GENERATION)
		given(fitnessCalculator calculateFitnessFor(FIRST_GENERATION)) willReturn(SECOND_GENERATION)

		val evolvedGeneration = evolution nextGenerationsFor INITIAL_POPULATION

		evolvedGeneration should be(SECOND_GENERATION)
	}

}
