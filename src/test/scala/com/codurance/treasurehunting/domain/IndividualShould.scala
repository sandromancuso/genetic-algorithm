package com.codurance.treasurehunting.domain

import com.codurance.UnitSpec
import com.codurance.treasurehunting.domain.Action._

class IndividualShould extends UnitSpec {

	"return a string representation" in {
		val actions = Seq(MOVE_NORTH, MOVE_EAST, MOVE_SOUTH, MOVE_WEST, PICK_UP_TREASURE, RANDOM_MOVE)

		val individual = Individual(Int.MinValue, actions)

		individual.stringRepresentation() should be("0213564")
	}

}
