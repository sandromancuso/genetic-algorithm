package com.codurance.treasurehunting.genetic_algorithm.map

import com.codurance.treasurehunting.domain.{Site, Individual}

case class TreasureMap(dimension: TreasureMapDimension = TreasureMapDimension(),
                       treasures: Seq[Treasure] = Seq()) {

	def fitnessFor(individual: Individual): Int = ???

}

case class TreasureMapDimension(x: Int = 10, y: Int = 10) {

	def withinBounds(site: Site): Boolean =
			site.x >= 0 && site.x < x &&
			site.y >= 0 && site.y < y

}



case class Treasure(site: Site = Site(0, 0))
