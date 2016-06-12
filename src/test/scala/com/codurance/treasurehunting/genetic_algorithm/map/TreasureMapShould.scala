package com.codurance.treasurehunting.genetic_algorithm.map

import com.codurance.UnitSpec
import com.codurance.treasurehunting.domain.SiteState.{EMPTY, WALL}
import com.codurance.treasurehunting.domain.{SiteState, Situation, Individual}
import org.mockito.Mockito
import org.mockito.Mockito.verify

class TreasureMapShould extends UnitSpec {

	trait context {
		val UNFIT_INDIVIDUAL = mock[Individual]
	}

	"return fitness of zero when individual has empty strategy" in {
		val unfitIndividual: Individual = Individual(actions = Seq(), fitness = 0)
		val treasureMap = TreasureMap(TreasureMapDimension(10, 10), treasures = Seq())

		val fitness = treasureMap fitnessFor unfitIndividual

		fitness should be(0)
	}

	"get individual action for initial situation (0,0)" in new context {
		val emptyTreasureMap = TreasureMap()
		val initialSituation = Situation(north   = WALL,
									 	 south   = EMPTY,
										 east    = EMPTY,
										 west    = WALL,
										 current = EMPTY)

		emptyTreasureMap fitnessFor UNFIT_INDIVIDUAL

		verify(UNFIT_INDIVIDUAL) actionFor(initialSituation)
	}

}
