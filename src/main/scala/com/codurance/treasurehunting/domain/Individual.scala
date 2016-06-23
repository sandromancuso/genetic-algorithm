package com.codurance.treasurehunting.domain

case class Individual(fitness: Int = Int.MinValue, actions: Seq[Action.Value])
	extends Ordered[Individual] {

	val strategy = createStrategy()

	def stringRepresentation(): String =
		actions.map(a => a.id.toString).mkString

	def actionFor(situation: Situation) = strategy actionFor situation

	override def compare(that: Individual): Int = fitness compare that.fitness

	protected def createStrategy() = Strategy(actions)
}