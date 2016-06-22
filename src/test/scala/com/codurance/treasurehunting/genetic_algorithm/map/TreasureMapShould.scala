package com.codurance.treasurehunting.genetic_algorithm.map

import com.codurance.UnitSpec
import com.codurance.treasurehunting.domain.{Individual, Site}

class TreasureMapShould extends UnitSpec {

	"calculate fitness for an individual" in {
		val treasureMap = TreasureMap(TreasureMapDimension(10, 10), Seq(Treasure(Site(0, 1))))
		val individual: Individual  = Individual(actions = Seq())

		val fitness = treasureMap fitnessFor(individual, 243)

		fitness should be(-1 + -5 + -5 + 10)
	}
}
