package com.codurance.treasurehunting.genetic_algorithm

import com.codurance.UnitSpec
import org.mockito.Mockito.verify

class TreasureHuntingGAShould extends UnitSpec {

	trait context {
		val randomStrategyGenerator = mock[RandomIndividualGenerator]
		val treasureHuntingGA = new TreasureHuntingGA(randomStrategyGenerator)
	}

	"generate 200 random individual strategies at start" in new context {
		treasureHuntingGA generateFittestIndividual()

		verify(randomStrategyGenerator) generate(200)
	}

}
