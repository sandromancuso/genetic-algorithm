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

	"populate map with treasures according to the number specified in the GA config" in new context {
		given(treasureGenerator randomTreasures()) willReturn(Seq(Treasure(), Treasure()))

	 	val treasureMap = treasureMapGenerator next()

		treasureMap.treasures.length should be(gaConfig.numberOfTreasuresPerMap)
	}

}
