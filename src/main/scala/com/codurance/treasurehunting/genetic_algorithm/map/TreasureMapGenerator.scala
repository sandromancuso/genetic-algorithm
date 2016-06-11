package com.codurance.treasurehunting.genetic_algorithm.map

import com.codurance.treasurehunting.genetic_algorithm.GAConfig

class TreasureMapGenerator(gaConfig: GAConfig, treasureGenerator: TreasureGenerator) {

	def next(): TreasureMap = TreasureMap(dimension = gaConfig.treasureMapDimension,
										  treasures = treasureGenerator randomTreasures())

}

class TreasureGenerator(gaConfig: GAConfig) {

	def randomTreasures(): Seq[Treasure] = ???

}
