package com.codurance.treasurehunting.genetic_algorithm.map

import com.codurance.UnitSpec
import com.codurance.treasurehunting.genetic_algorithm.GAConfig
import org.mockito.BDDMockito.given
import org.mockito.Mockito

class TreasureMapGeneratorShould extends UnitSpec {

	val TREASURES = Seq(Treasure(), Treasure())

	trait context {
		val gaConfig = GAConfig(treasureMapDimension = TreasureMapDimension(10, 10),
								numberOfTreasuresPerMap = 2)

		val treasureGenerator = mock[TreasureGenerator]
		given(treasureGenerator randomTreasures()) willReturn TREASURES

		val treasureMapGenerator = new TreasureMapGenerator(gaConfig, treasureGenerator)
	}

	"create a map according to the dimensions specified in the GA configuration" in new context {
		val treasureMap = treasureMapGenerator next()

		treasureMap.dimension should be(gaConfig.treasureMapDimension)
	}

	"populate map with treasures" in new context {
	 	val treasureMap = treasureMapGenerator next()

		treasureMap.treasures should be(TREASURES)
	}

	"return random maps" in {
		val gaConfig = GAConfig(numberOfMapsInTheCache = 20)
		val treasureMapGenerator = new TreasureMapGenerator(gaConfig, new TreasureGenerator(gaConfig))

		val treasureMaps = treasureMapGenerator randomMaps 20

		treasureMaps.distinct.size should be (20)
	}

}
