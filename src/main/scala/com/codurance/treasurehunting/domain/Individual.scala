package com.codurance.treasurehunting.domain

case class Individual(actions: Seq[Action.Value], fitness: Int = Int.MinValue) {

	def stringRepresentation(): String =
		actions.map(a => a.id.toString).mkString

}

