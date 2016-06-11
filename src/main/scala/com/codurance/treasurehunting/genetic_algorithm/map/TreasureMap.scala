package com.codurance.treasurehunting.genetic_algorithm.map

import com.codurance.treasurehunting.domain.Individual

case class TreasureMap(dimension: TreasureMapDimension = TreasureMapDimension(),
                       treasures: Seq[Treasure] = Seq()) {

	def fitnessFor(individual: Individual): Int = ???

}

case class TreasureMapDimension(x: Int = 10, y: Int = 10)

case class Treasure(x: Int = 0, y: Int = 0)
