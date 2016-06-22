package com.codurance.treasurehunting.domain

import com.codurance.UnitSpec
import com.codurance.treasurehunting.domain.Action._
import com.codurance.treasurehunting.domain.SiteState._

class IndividualShould extends UnitSpec {

	"return a string representation" in {
		val actions = Seq(MOVE_NORTH, MOVE_EAST, MOVE_SOUTH, MOVE_WEST, PICK_UP_TREASURE, RANDOM_MOVE)

		val individual = Individual(Int.MinValue, actions)

		individual.stringRepresentation() should be("021356")
	}

	"compare itself with another individual" in {
		val individual1 = Individual(fitness = 10, actions = Seq())
		val individual2 = Individual(fitness = 5, actions = Seq())
		val individual3 = Individual(fitness = 10, actions = Seq())

		individual1 compare individual2 should be (1)
		individual2 compare individual1 should be (-1)
		individual1 compare individual3 should be (0)
	}

}
