package com.codurance.treasurehunting.domain

case class Individual(actions: Seq[Action.Value]) {

	def stringRepresentation(): String =
		actions.map(a => a.id.toString).mkString

}

