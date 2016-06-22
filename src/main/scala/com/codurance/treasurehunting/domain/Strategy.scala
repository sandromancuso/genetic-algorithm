package com.codurance.treasurehunting.domain

import scala.collection.mutable

case class Strategy(actions: Seq[Action.Value]) {

	val situationActionMap: Map[Situation, Action.Value] = createStrategy()

	def actionFor(situation: Situation) = situationActionMap(situation)

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
