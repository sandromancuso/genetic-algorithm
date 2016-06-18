package com.codurance.treasurehunting.domain

import com.codurance.UnitSpec
import com.codurance.treasurehunting.domain.Action._
import com.codurance.treasurehunting.domain.SiteState._

class StrategyShould extends UnitSpec {

	val NO_ACTIONS = Seq[Action.Value]()

	"have size of 243 (3 x 3 x 3 x 3 x 3) representing all states of " +
			"the current site, north, south, east, and west" in {
		Strategy(NO_ACTIONS).situationActionMap.size should be (243)
	}

	"have unique situations" in {
		val situations = Strategy(NO_ACTIONS).situationActionMap.keySet.toList

		situations.size should be (situations.distinct.size)
	}

	"bind received actions to situations" in {
		val actions = Seq(MOVE_NORTH, MOVE_SOUTH, MOVE_EAST, MOVE_WEST, MOVE_NORTH)

		val strategy = Strategy(actions)

		strategy actionFor Situation(EMPTY, EMPTY, EMPTY, EMPTY, EMPTY)    should be (MOVE_NORTH)
		strategy actionFor Situation(EMPTY, EMPTY, EMPTY, EMPTY, TREASURE) should be (MOVE_SOUTH)
		strategy actionFor Situation(EMPTY, EMPTY, EMPTY, EMPTY, WALL)     should be (MOVE_EAST)
		strategy actionFor Situation(EMPTY, EMPTY, EMPTY, TREASURE, EMPTY) should be (MOVE_WEST)
		strategy actionFor Situation(EMPTY, EMPTY, EMPTY, TREASURE, TREASURE) should be (MOVE_NORTH)
		strategy actionFor Situation(EMPTY, EMPTY, EMPTY, TREASURE, WALL)     should be (PICK_UP_TREASURE)
		strategy actionFor Situation(EMPTY, WALL, EMPTY, TREASURE, WALL)      should be (RANDOM_MOVE)
		strategy actionFor Situation(TREASURE, WALL, EMPTY, TREASURE, EMPTY)  should be (PICK_UP_TREASURE)
	}

}
