package com.codurance.treasurehunting.genetic_algorithm.evolution

import com.codurance.treasurehunting.domain.{Individual, Population, RandomAction}

import scala.util.Random

class Generation {

	def next(population: Population): Population = {

		var newIndividuals: Seq[Individual] = Seq()

		def generateNextPopulation(parentSelection: ParentSelection): Unit = {

			1 to population.size() / 2 foreach { n =>
				val parentA = parentSelection nextParent()
				val parentB = parentSelection nextParent()
				val children = generateChildren(parentA, parentB)
				newIndividuals = newIndividuals :+ children._1 :+ children._2
			}

		}

		def generateChildren(parentA: Individual, parentB: Individual) = {
			val splitPosition: Int = randomSplitPosition()
			val parentA_splitDNA = parentA.actions.splitAt(splitPosition)
			val parentB_splitDNA = parentB.actions.splitAt(splitPosition)

			val child_1 = Individual(actions = parentA_splitDNA._1 ++ parentB_splitDNA._2)
			val child_2 = Individual(actions = parentB_splitDNA._1 ++ parentA_splitDNA._2)

			(mutate(child_1), mutate(child_2))
		}

		generateNextPopulation(new ParentSelection(population))

		Population(newIndividuals:_*)
	}

	def randomSplitPosition(): Int = Random.nextInt(242)

	def mutate(individual: Individual): Individual = {
		var mutatedIndividual = individual
		1 to 10 foreach { i =>
			val mutatedActions = mutatedIndividual.actions.updated(Random.nextInt(242), RandomAction.next())
			mutatedIndividual = mutatedIndividual.copy(actions = mutatedActions)
		}
		mutatedIndividual
	}

}



