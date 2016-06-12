package com.codurance.treasurehunting.genetic_algorithm.map

import com.codurance.treasurehunting.domain.SiteState.{TREASURE, EMPTY}
import com.codurance.treasurehunting.domain.{SiteState, Situation, Site, Individual}

import scala.collection.mutable

case class TreasureMap(dimension: TreasureMapDimension = TreasureMapDimension(),
                       treasures: Seq[Treasure] = Seq()) {

	val siteTreasureMap = createSiteTreasureMap()
	val siteSituationMap = createSiteSituationMap()

	def fitnessFor(individual: Individual): Int = {
		val currentSite = Site(0, 0)
		val situation = siteSituationMap.get(currentSite)
		val action = individual actionFor situation.get
		0
	}

	private def createSiteTreasureMap(): Map[Site, Treasure] = {
		val siteTreasureMap = mutable.Map[Site, Treasure]()
		treasures foreach(t => siteTreasureMap put(t.site, t))
		siteTreasureMap.toMap
	}

	private def createSiteSituationMap(): Map[Site, Situation] = {
		val siteSituationMap = mutable.Map[Site, Situation]()
		0 to dimension.x - 1 foreach { x =>
			0 to dimension.y - 1 foreach { y =>
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
