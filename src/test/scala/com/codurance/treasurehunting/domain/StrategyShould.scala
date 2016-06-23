package com.codurance.treasurehunting.domain

import com.codurance.UnitSpec
import com.codurance.treasurehunting.domain.Action._
import com.codurance.treasurehunting.domain.SiteState._

class StrategyShould extends UnitSpec {

	val NO_ACTIONS = Seq[Action.Value]()

	"have 243 (3 x 3 x 3 x 3 x 3) unique situations" in {
		val strategy = Strategy(NO_ACTIONS)

		strategy.situationActionMap.keySet.size should be (243)
	}

	"bind received actions to situations" in {
		val ACTIONS = 1 to 243 map(_ => RandomAction next())

		val strategy = Strategy(ACTIONS)

		Action.values foreach { action =>
			numberOfActionInStrategy(strategy, action) should be (ACTIONS.count(_ == action))
		}
	}

	def numberOfActionInStrategy(strategy: Strategy, action: Action.Value) =
		strategy.situationActionMap.values.count(_ == action)

}
