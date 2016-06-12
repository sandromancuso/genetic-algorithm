package com.codurance.treasurehunting.genetic_algorithm.map

import com.codurance.UnitSpec
import com.codurance.treasurehunting.genetic_algorithm.GAConfig
import org.mockito.BDDMockito.given

class TreasureMapGeneratorShould extends UnitSpec {

	trait context {
		val gaConfig = GAConfig(treasureMapDimension = TreasureMapDimension(10, 10),
								numberOfTreasuresPerMap = 2)
		val treasureGenerator = mock[TreasureGenerator]
		val treasureMapGenerator = new TreasureMapGenerator(gaConfig, treasureGenerator)
	}

	"create a map according to the dimensions specified in the GA configuration" in new context {
		val treasureMap = treasureMapGenerator next()

		treasureMap.dimension should be(gaConfig.treasureMapDimension)
	}

	"populate map with treasures" in new context {
		val treasures = Seq(Treasure(), Treasure())
		given(treasureGenerator randomTreasures()) willReturn(treasures)

	 	val treasureMap = treasureMapGenerator next()

		treasureMap.treasures should be(treasures)
	}

}
