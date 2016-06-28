package com.codurance.treasurehunting.domain

import scala.util.Random

object Action extends Enumeration {

	val MOVE_NORTH, MOVE_SOUTH, MOVE_EAST, MOVE_WEST,
		STAY_PUT, PICK_UP_TREASURE, RANDOM_MOVE = Value

}

object RandomAction {

	val actionsWithProbability = Random.shuffle(List(
		Action.MOVE_NORTH,
		Action.MOVE_NORTH,
		Action.MOVE_SOUTH,
		Action.MOVE_SOUTH,
		Action.MOVE_EAST,
		Action.MOVE_EAST,
		Action.MOVE_WEST,
		Action.MOVE_WEST,
		Action.PICK_UP_TREASURE,
		Action.PICK_UP_TREASURE,
		Action.PICK_UP_TREASURE,
		Action.PICK_UP_TREASURE,
		Action.RANDOM_MOVE
	))

	def next(): Action.Value = {
		val action = Action(Random.nextInt(Action.maxId))
		if (action != Action.STAY_PUT) action else next()
	}

	def nextWithProbability(): Action.Value = {
		val index = Random.nextInt(actionsWithProbability.size)
		actionsWithProbability(index)
	}
}
