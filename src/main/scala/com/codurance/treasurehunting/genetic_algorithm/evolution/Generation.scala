package com.codurance.treasurehunting.genetic_algorithm.evolution

import com.codurance.treasurehunting.domain.{Action, RandomAction, Individual, Population}

import scala.util.Random

class Generation {

	def next(population: Population): Population = {

		var newIndividuals: Seq[Individual] = Seq()

		val fitIndividuals = population.individuals
											.sortBy(-_.fitness)
										    .splitAt(population.individuals.size / 2)._1


		def generateNextPopulation(individuals: Seq[Individual]): Unit = {

			individuals.toList match {
				case parentA :: parentB :: theRest => {
					val children = generateChildren(parentA, parentB)
					newIndividuals = newIndividuals :+ children._1 :+ children._2 :+ children._3 :+ children._4
					generateNextPopulation(theRest)
				}
				case _ =>
			}

		}

		def generateChildren(parentA: Individual, parentB: Individual) = {
			var splitPosition: Int = randomSplitPosition()
			var parentA_splitDNA = parentA.actions.splitAt(splitPosition)
			var parentB_splitDNA = parentB.actions.splitAt(splitPosition)

			val child_1 = Individual(actions = parentA_splitDNA._1 ++ parentB_splitDNA._2)
			val child_2 = Individual(actions = parentB_splitDNA._1 ++ parentA_splitDNA._2)

			splitPosition = randomSplitPosition()
			parentA_splitDNA = parentA.actions.splitAt(splitPosition)
			parentB_splitDNA = parentB.actions.splitAt(splitPosition)

			val child_3 = Individual(actions = parentA_splitDNA._1 ++ parentB_splitDNA._2)
			val child_4 = Individual(actions = parentB_splitDNA._1 ++ parentA_splitDNA._2)

			(mutate(child_1), mutate(child_2), mutate(child_3), mutate(child_4))
		}

		generateNextPopulation(fitIndividuals)

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
