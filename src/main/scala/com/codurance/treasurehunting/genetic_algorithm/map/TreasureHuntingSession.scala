package com.codurance.treasurehunting.genetic_algorithm.map

import com.codurance.treasurehunting.domain.Action._
import com.codurance.treasurehunting.domain._

import scala.util.Random

class TreasureHuntingSession(treasureMap: TreasureMap, individual: Individual, numberOfActionsToExcute: Int = 200) {

	type Score = Int

	var currentSite = Site(0, 0)

	var score: Score = 0

	def run(): Score = executeActions()

	private def executeActions(): Score = {
		0 until numberOfActionsToExcute foreach { a =>
			val situation = treasureMap situationFor currentSite
			val action = individual actionFor situation
			execute(action, situation)
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
		val nextSite = currentSite.copy(y = currentSite.y - 1)
		tryMovingTo(nextSite)
	}

	private def moveSouth() = {
		val nextSite = currentSite.copy(y = currentSite.y + 1)
		tryMovingTo(nextSite)
	}

	private def moveEast() = {
		val nextSite = currentSite.copy(x = currentSite.x + 1)
		tryMovingTo(nextSite)
	}

	private def moveWest() = {
		val nextSite = currentSite.copy(x = currentSite.x - 1)
		tryMovingTo(nextSite)
	}

	private def tryMovingTo(site: Site) = {
		if (treasureMap.isSiteOutOfBounds(site))
			 score = score - 5
		else currentSite = site
	}

	private def stayPut() = {}

	private def chooseRandomMove(): Unit = {
		val randomAction = RandomAction next()
		randomAction match {
			case MOVE_NORTH => moveNorth()
			case MOVE_SOUTH => moveSouth()
			case MOVE_EAST => moveEast()
			case MOVE_WEST => moveWest()
			case PICK_UP_TREASURE => pickUpTreasure()
			case STAY_PUT | RANDOM_MOVE => chooseRandomMove()
		}
	}

	private def pickUpTreasure() =
		if (treasureMap.hasTreasureAt(currentSite)) {
			score = score + 10
			treasureMap.removeTreasureAt(currentSite)
		} else {
			score = score - 1
//			chooseRandomMove()
//			moveToNewSite()
		}

	private def moveToNewSite() = {
		var actions = Seq[Action.Value]()
		if (canMoveNorth()) actions = actions :+ MOVE_NORTH;
		if (canMoveSouth()) actions = actions :+ MOVE_SOUTH;
		if (canMoveEast()) actions = actions :+ MOVE_EAST;
		if (canMoveWest()) actions = actions :+ MOVE_WEST;
		val action = actions(Random.nextInt(actions.size))
		val situation = treasureMap situationFor currentSite
		execute(action, situation)
	}

	private def canMoveNorth() = currentSite.y > 0
	private def canMoveSouth() = currentSite.y < 9
	private def canMoveEast() = currentSite.x < 9
	private def canMoveWest() = currentSite.x > 0

}
