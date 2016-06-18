package com.codurance.treasurehunting.genetic_algorithm.map

import com.codurance.treasurehunting.domain.Action._
import com.codurance.treasurehunting.domain.SiteState.{TREASURE, EMPTY}
import com.codurance.treasurehunting.domain._

import scala.collection.mutable

case class TreasureMap(dimension: TreasureMapDimension = TreasureMapDimension(),
                       treasures: Seq[Treasure] = Seq()) {

	val siteTreasureMap: Map[Site, Treasure] = createSiteTreasureMap()
	val siteSituationMap: Map[Site, Situation] = createSiteSituationMap()

	def fitnessFor(individual: Individual, actionsToExecute: Int = 200): Int = {
		var fitness = 0
		var currentSite = Site(0, 0)
		var availableTreasures = treasures

		def situation(): Situation = { siteSituationMap.get(currentSite).get }

		def moveNorth() =
			if (currentSite.x == 0)
				fitness = fitness + -5
			else currentSite = currentSite.copy(x = currentSite.x - 1)

		def moveSouth() =
			if (currentSite.x == dimension.x - 1)
				fitness += -5
			else currentSite = currentSite.copy(x = currentSite.x + 1)

		def moveEast() =
			if (currentSite.y == dimension.y - 1)
				fitness += -5
			else currentSite = currentSite.copy(y = currentSite.y + 1)

		def moveWest() =
			if (currentSite.y == 0)
				fitness += -5
			else currentSite = currentSite.copy(y = currentSite.y - 1)

		def stayPut() = {}

		def chooseRandomMove() = {
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

		def pickUpTreasure() =
			if (hasTreasureAt(currentSite)) {
				fitness += 10
				removeTreasureAt(currentSite)
			} else fitness += -1

		def hasTreasureAt(site: Site) =
			availableTreasures.exists(_.site == site)

		def removeTreasureAt(site: Site) =
			availableTreasures = availableTreasures diff Seq(Treasure(site))

		def execute(action: Action.Value, situation: Situation): Unit = {
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

		def executeActions(): Int = {
			0 until actionsToExecute foreach { a =>
				val action = individual actionFor situation
	         	execute(action, situation())
			}
			fitness
		}

		executeActions()
	}

	private def createSiteTreasureMap(): Map[Site, Treasure] = {
		val siteTreasureMap = mutable.Map[Site, Treasure]()
		treasures foreach(t => siteTreasureMap put(t.site, t))
		siteTreasureMap.toMap
	}

	private def createSiteSituationMap(): Map[Site, Situation] = {
		val siteSituationMap = mutable.Map[Site, Situation]()
		0 until dimension.x foreach { x =>
			0 until dimension.y foreach { y =>
			    val site = Site(x, y)
				val situation = situationFor(site)
				siteSituationMap.put(site, situation)
			}
		}
		siteSituationMap.toMap
	}

	private def situationFor(site: Site): Situation =
		Situation(north   = northSiteStateFor(site),
			south   = southSiteStateFor(site),
			east    = eastSiteStateFor(site),
			west    = westSiteStateFor(site),
			current = currentSiteStateFor(site))

	private def northSiteStateFor(site: Site) =
		if (site.y == 0)
			SiteState.WALL
		else treasureOrEmpty(site.copy(y = site.y - 1))

	private def southSiteStateFor(site: Site) =
		if (site.y == dimension.y - 1)
			SiteState.WALL
		else treasureOrEmpty(site.copy(y = site.y + 1))

	private def eastSiteStateFor(site: Site) =
		if (site.x == dimension.x - 1)
			SiteState.WALL
		else treasureOrEmpty(site.copy(x = site.x + 1))

	private def westSiteStateFor(site: Site) =
		if (site.x == 0)
			SiteState.WALL
		else treasureOrEmpty(site.copy(x = site.x - 1))

	private def currentSiteStateFor(site: Site) = treasureOrEmpty(site)

	private def treasureOrEmpty(site: Site) = {
		siteTreasureMap.get(site) match {
			case None => EMPTY
			case _    => TREASURE
		}
	}

}

case class TreasureMapDimension(x: Int = 10, y: Int = 10) {

	def withinBounds(site: Site): Boolean =
			site.x >= 0 && site.x < x &&
			site.y >= 0 && site.y < y

}

case class Treasure(site: Site = Site(0, 0))
