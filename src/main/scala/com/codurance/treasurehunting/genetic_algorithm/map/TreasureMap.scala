package com.codurance.treasurehunting.genetic_algorithm.map

import com.codurance.treasurehunting.domain.SiteState.{EMPTY, TREASURE}
import com.codurance.treasurehunting.domain._

import scala.collection.mutable

case class TreasureMap(dimension: TreasureMapDimension = TreasureMapDimension(),
                       treasures: Seq[Treasure] = Seq()) {

	var siteTreasureMap: Map[Site, Treasure] = createSiteTreasureMap()
	val siteSituationMap: Map[Site, Situation] = createSiteSituationMap()

	def situationFor(site: Site): Situation = siteSituationMap.get(site).get

	def hasTreasureAt(site: Site): Boolean = siteTreasureMap.contains(site)

	def removeTreasureAt(site: Site): Unit = siteTreasureMap = siteTreasureMap - site

	def isSiteOutOfBounds(site: Site): Boolean = !dimension.withinBounds(site)

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
				val situation = createSituationFor(site)
				siteSituationMap.put(site, situation)
			}
		}
		siteSituationMap.toMap
	}

	def createSituationFor(site: Site): Situation =
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


