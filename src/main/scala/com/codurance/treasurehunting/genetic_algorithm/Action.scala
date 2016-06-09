package com.codurance.treasurehunting.genetic_algorithm

import scala.util.Random

object Action extends Enumeration {

	val MOVE_NORTH, MOVE_SOUTH, MOVE_EAST, MOVE_WEST,
		STAY_PUT, PICK_UP_TREASURE, RANDOM_MOVE = Value

}

object RandomAction {
	def next(): Action.Value = Action(Random.nextInt(Action.maxId))
}
