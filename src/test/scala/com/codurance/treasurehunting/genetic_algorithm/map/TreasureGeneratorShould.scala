package com.codurance.treasurehunting.genetic_algorithm.map

import com.codurance.UnitSpec
import com.codurance.treasurehunting.genetic_algorithm.GAConfig

class TreasureGeneratorShould extends UnitSpec {

	val TEN_x_TEN_MAP = TreasureMapDimension(10, 10)

	"generate treasures according to the number specified in the GA config" in {
		val gaConfig: GAConfig = GAConfig(numberOfTreasuresPerMap = 20)
		val treasureGenerator = new TreasureGenerator(gaConfig)

		val treasures = treasureGenerator randomTreasures()

		treasures.size should be(gaConfig.numberOfTreasuresPerMap)
	}

	"generate treasures withing the bounds of the map" in {
		val gaConfig: GAConfig = GAConfig(treasureMapDimension = TEN_x_TEN_MAP,
											numberOfTreasuresPerMap = 20)
		val treasureGenerator = new TreasureGenerator(gaConfig)

		val treasures = treasureGenerator randomTreasures()

		treasures foreach { treasure =>
			TEN_x_TEN_MAP.withinBounds(treasure.site) should be (true)
		}
	}

	"generate treasures in different sites, with no overlap" in {
		val gaConfig: GAConfig = GAConfig(treasureMapDimension = TEN_x_TEN_MAP,
											numberOfTreasuresPerMap = 90)
		val treasureGenerator = new TreasureGenerator(gaConfig)

		// Do one hundred times to minimise problems with random elements
		1 to 100 foreach { _ =>
			val treasures = treasureGenerator randomTreasures()

			treasures.distinct.size should be (treasures.size)
		}
	}

}
