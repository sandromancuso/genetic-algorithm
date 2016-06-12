package com.codurance.treasurehunting.domain

case class Site(x: Int, y: Int)

object SiteState extends Enumeration {

	val EMPTY, TREASURE, WALL = Value

}
