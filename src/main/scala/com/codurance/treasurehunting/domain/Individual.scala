package com.codurance.treasurehunting.domain

import scala.collection.mutable

case class Individual(fitness: Int = Int.MinValue, actions: Seq[Action.Value])
	extends Ordered[Individual] {

	val strategy = Strategy(actions)

	def stringRepresentation(): String =
		actions.map(a => a.id.toString).mkString

	def actionFor(situation: Situation) = strategy actionFor situation

	override def compare(that: Individual): Int = (fitness) compare (that.fitness)
}

case class Situation(north: SiteState.Value,
                     south: SiteState.Value,
                     east: SiteState.Value,
                     west: SiteState.Value,
                     current: SiteState.Value)

case class Strategy(actions: Seq[Action.Value]) {

	def actionFor(situation: Situation) = situationActionMap(situation)

	val situationActionMap: Map[Situation, Action.Value] = createStrategy()

	private def createStrategy(): Map[Situation, Action.Value] = {

		val actionsIterator = actions.iterator

		def nextAction() = if (actionsIterator.hasNext) actionsIterator.next() else RandomAction.next()

		val numberOfSiteStates = SiteState.values.size
		val situationActionMap = mutable.Map[Situation, Action.Value]()
		val i, j, k, l, m: Int = 0

		0 until numberOfSiteStates foreach { i =>
			0 until numberOfSiteStates foreach { j =>
				0 until numberOfSiteStates foreach { k =>
					0 until numberOfSiteStates foreach { l =>
						0 until numberOfSiteStates foreach { m =>
							val situation = Situation(
								north = SiteState(i),
								south = SiteState(j),
								east = SiteState(k),
								west = SiteState(l),
								current = SiteState(m))
							situationActionMap.put(situation, nextAction())
						}
					}
				}
			}
		}
		situationActionMap.toMap
	}

}

