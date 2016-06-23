package com.codurance.treasurehunting.genetic_algorithm.map

import com.codurance.treasurehunting.domain.Action._
import com.codurance.treasurehunting.domain._

class TreasureHuntingSession(treasureMap: TreasureMap, individual: Individual) {

	type Score = Int

	val NUMBER_OF_ACTIONS_TO_EXECUTE = 200
	var currentSite = Site(0, 0)

	var score: Score = 0

	def run(): Score = executeActions()

	private def executeActions(): Score = {
		0 until NUMBER_OF_ACTIONS_TO_EXECUTE foreach { a =>
			val situation = treasureMap situationFor currentSite
			val action = individual actionFor situation
			execute(action, treasureMap.situationFor(currentSite))
		}
		score
	}

	private def execute(action: Action.Value, situation: Situation): Unit = {
		action match {
			case MOVE_NORTH => moveNorth()
			case MOVE_SOUTH => moveSouth()
			case MOVE_EAST => moveEast()
			case MOVE_WEST => moveWest()
			case STAY_PUT => stayPut()
			case RANDOM_MOVE => chooseRandomMove()
			case PICK_UP_TREASURE => pickUpTreasure()
		}
	}

	private def moveNorth() = {
		val nextSite = currentSite.copy(x = currentSite.x - 1)
		tryMovingTo(nextSite)
	}

	private def moveSouth() = {
		val nextSite = currentSite.copy(x = currentSite.x + 1)
		tryMovingTo(nextSite)
	}

	private def moveEast() = {
		val nextSite = currentSite.copy(y = currentSite.y + 1)
		tryMovingTo(nextSite)
	}

	private def moveWest() = {
		val nextSite = currentSite.copy(y = currentSite.y - 1)
		tryMovingTo(nextSite)
	}

	private def tryMovingTo(site: Site) = {
		if (treasureMap.isSiteOutOfBounds(site))
			score += -5
		else currentSite = site
	}

	private def stayPut() = {}

	private def chooseRandomMove() = {
		val randomAction = RandomAction next()
		randomAction match {
			case MOVE_NORTH => moveNorth()
			case MOVE_SOUTH => moveSouth()
			case MOVE_EAST => moveEast()
			case MOVE_WEST => moveWest()
			case STAY_PUT => stayPut()
			case PICK_UP_TREASURE => pickUpTreasure()
			case RANDOM_MOVE =>
		}
	}

	private def pickUpTreasure() =
		if (treasureMap.hasTreasureAt(currentSite)) {
			score += 10
			treasureMap.removeTreasureAt(currentSite)
		} else score += -1


}