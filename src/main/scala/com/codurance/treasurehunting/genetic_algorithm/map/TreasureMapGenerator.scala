package com.codurance.treasurehunting.genetic_algorithm.map

import com.codurance.treasurehunting.genetic_algorithm.GAConfig

import scala.collection.mutable
import scala.util.Random

class TreasureMapGenerator(gaConfig: GAConfig, treasureGenerator: TreasureGenerator) {

	def next(): TreasureMap = TreasureMap(dimension = gaConfig.treasureMapDimension,
										  treasures = treasureGenerator randomTreasures())

}

class TreasureGenerator(gaConfig: GAConfig) {

	def randomTreasures(): Seq[Treasure] = {
		var availableSites = generateAvailableSites()

		var treasures: Seq[Treasure] = Seq()

		1 to gaConfig.numberOfTreasuresPerMap foreach { _ =>
			val randomSite = availableSites(Random.nextInt(availableSites.length))
			treasures = treasures :+ Treasure(randomSite)
			availableSites = availableSites diff Seq(randomSite)
		}
		treasures
	}

	private def generateAvailableSites(): Seq[Site] = {
		var sites: Seq[Site] = mutable.MutableList()
		0 until gaConfig.treasureMapDimension.x foreach { x =>
			0 until gaConfig.treasureMapDimension.y foreach { y =>
				sites = sites :+ Site(x, y)
			}
		}
		sites
	}

}
