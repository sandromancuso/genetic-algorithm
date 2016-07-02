package com.codurance.treasurehunting.genetic_algorithm.evolution

import com.codurance.treasurehunting.domain.{RandomAction, Individual}

import scala.util.Random

class Mutation {

	def mutate(individual: Individual): Individual = {
		var mutatedIndividual = individual
		1 to 10 foreach { i =>
			val mutatedActions = mutatedIndividual.actions.updated(
				randomGeneIndex(mutatedIndividual),
				RandomAction.nextWithProbability())
			mutatedIndividual = mutatedIndividual.copy(actions = mutatedActions)
		}
		mutatedIndividual
	}

	protected def randomGeneIndex(individual: Individual): Int = Random.nextInt(individual.actions.size - 1)

}
