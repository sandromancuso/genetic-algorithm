package com.codurance.treasurehunting.genetic_algorithm.map

import com.codurance.UnitSpec
import com.codurance.treasurehunting.domain.SiteState._
import com.codurance.treasurehunting.domain.{SiteState, Situation, Individual, Site}

class TreasureMapShould extends UnitSpec {

	val NO_TREASURES: Seq[Treasure] = Seq()

	"create situations for a 1 by 1 map" in {
		val treasureMap = TreasureMap(TreasureMapDimension(1, 1), NO_TREASURES)

		val situation = treasureMap situationFor Site(0, 0)

		situation should be(Situation(north = WALL,
										south = WALL,
										east = WALL,
										west = WALL,
										current = EMPTY))
	}

	"create situations for a 3 by 3 map where 0,0 is at the top left corner" in {
		val TREASURES = Seq(Treasure(Site(0,0)), Treasure(Site(0, 2)), Treasure(Site(1, 1)))

		val treasureMap = TreasureMap(TreasureMapDimension(3, 3), TREASURES)

		val SITUATION_FOR_SITE_0_0 = Situation(north = WALL, south = EMPTY, east = EMPTY, west = WALL, current = TREASURE)
		val SITUATION_FOR_SITE_1_1 = Situation(north = EMPTY, south = EMPTY, east = EMPTY, west = EMPTY, current = TREASURE)
		val SITUATION_FOR_SITE_2_1 = Situation(north = EMPTY, south = EMPTY, east = WALL, west = TREASURE, current = EMPTY)
		val SITUATION_FOR_SITE_2_2 = Situation(north = EMPTY, south = WALL, east = WALL, west = EMPTY, current = EMPTY)

		var situation = treasureMap situationFor Site(0, 0)
		situation should be(SITUATION_FOR_SITE_0_0)

		situation = treasureMap situationFor Site(1, 1)
		situation should be(SITUATION_FOR_SITE_1_1)

		situation = treasureMap situationFor Site(2, 1)
		situation should be(SITUATION_FOR_SITE_2_1)

		situation = treasureMap situationFor Site(2, 2)
		situation should be(SITUATION_FOR_SITE_2_2)
	}

	"inform if there is a treasure on a site" in {
		val TREASURES = Seq(Treasure(Site(0,0)), Treasure(Site(0, 2)), Treasure(Site(1, 1)))
		val treasureMap = TreasureMap(TreasureMapDimension(3, 3), TREASURES)

		treasureMap hasTreasureAt Site(0, 0) should be(true)
		treasureMap hasTreasureAt Site(0, 1) should be(false)
		treasureMap hasTreasureAt Site(0, 2) should be(true)
		treasureMap hasTreasureAt Site(1, 0) should be(false)
		treasureMap hasTreasureAt Site(1, 1) should be(true)
		treasureMap hasTreasureAt Site(2, 2) should be(false)
	}

	"remove treasure from a site" in {
		val TREASURES = Seq(Treasure(Site(0,0)), Treasure(Site(0, 2)), Treasure(Site(1, 1)))
		val treasureMap = TreasureMap(TreasureMapDimension(3, 3), TREASURES)

		treasureMap removeTreasureAt Site(0, 2)

		treasureMap hasTreasureAt Site(0, 2) should be(false)
	}

	"inform if a site is out of bounds" in {
		val DIMENSION_3X3: TreasureMapDimension = TreasureMapDimension(3, 3)
		val treasureMap = TreasureMap(DIMENSION_3X3, NO_TREASURES)

		treasureMap isSiteOutOfBounds Site(0, 0) should be(false)
		treasureMap isSiteOutOfBounds Site(3, 0) should be(true)
		treasureMap isSiteOutOfBounds Site(1, 2) should be(false)
		treasureMap isSiteOutOfBounds Site(1, 3) should be(true)
		treasureMap isSiteOutOfBounds Site(-1, 0) should be(true)
		treasureMap isSiteOutOfBounds Site(0, -1) should be(true)
	}

}
