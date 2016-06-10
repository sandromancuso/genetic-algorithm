package com.codurance.treasurehunting.genetic_algorithm.evolution

import com.codurance.UnitSpec
import com.codurance.treasurehunting.domain.Action._
import com.codurance.treasurehunting.domain.{Action, Individual, Population}
import com.codurance.treasurehunting.genetic_algorithm.GAConfig
import org.mockito.BDDMockito.given
import org.mockito.Mockito.verify

class EvolutionShould extends UnitSpec {

	val FIRST_GENERATION  = Population(Individual(Seq(MOVE_NORTH)))
	val SECOND_GENERATION = Population(Individual(Seq(MOVE_EAST)))
	val THIRD_GENERATION  = Population(Individual(Seq(PICK_UP_TREASURE)))

	val FIT_FIRST_GENERATION  = Population(Individual(Seq(MOVE_WEST)))
	val FIT_SECOND_GENERATION = Population(Individual(Seq(MOVE_SOUTH)))
	val FIT_THIRD_GENERATION  = Population(Individual(Seq(STAY_PUT)))

	trait context {
		val gaConfig = new GAConfig(generations = 2)
		val generation = mock[Generation]
		val populationFitnessCalculator = mock[PopulationFitnessCalculator]
		val evolution = new Evolution(gaConfig, populationFitnessCalculator, generation)
	}

	"evolve initial population until the last generation specified in the GA configuration" in new context {
		given(populationFitnessCalculator calculateFitnessFor FIRST_GENERATION) willReturn FIT_FIRST_GENERATION
		given(populationFitnessCalculator calculateFitnessFor SECOND_GENERATION) willReturn FIT_SECOND_GENERATION
		given(populationFitnessCalculator calculateFitnessFor THIRD_GENERATION) willReturn FIT_THIRD_GENERATION

		given(generation next FIT_FIRST_GENERATION) willReturn SECOND_GENERATION
		given(generation next FIT_SECOND_GENERATION) willReturn THIRD_GENERATION

		val lastGeneration = evolution evolve FIRST_GENERATION

		lastGeneration should be(FIT_THIRD_GENERATION)
	}

}
