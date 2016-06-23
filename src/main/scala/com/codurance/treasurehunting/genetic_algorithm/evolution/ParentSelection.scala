package com.codurance.treasurehunting.genetic_algorithm.evolution

import com.codurance.treasurehunting.domain.{Individual, Population}

import scala.collection.mutable
import scala.util.Random

class ParentSelection(population: Population) {

	val individualsOrderedByFitnessAsc = population.individuals.sortBy(_.fitness)
	var indexIndividualMap: Map[Int, Individual] = createIndexIndividualMap()
	var indexList = createIndexList()

	private def createIndexIndividualMap() =
		individualsOrderedByFitnessAsc.indices
				        .map(idx => idx -> individualsOrderedByFitnessAsc(idx))
				        .toMap

	private def createIndexList() = {
		val list = mutable.ListBuffer[Int]()
		indexIndividualMap.keySet foreach { index =>
			0 to index foreach (i => list += index)
		}
		Random.shuffle(list)
	}

	def nextParent(): Individual = indexIndividualMap(indexList(Random.nextInt(indexList.size - 1)))

}
