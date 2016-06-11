package com.codurance.treasurehunting.genetic_algorithm.evolution

import com.codurance.UnitSpec
import com.codurance.treasurehunting.domain.Action.MOVE_NORTH
import com.codurance.treasurehunting.domain.Individual
import com.codurance.treasurehunting.genetic_algorithm.GAConfig
import com.codurance.treasurehunting.genetic_algorithm.map.{TreasureMap, TreasureMapGenerator}
import org.mockito.BDDMockito.given

class IndividualFitnessCalculatorShould extends UnitSpec {
	
	val UNFIT_INDIVIDUAL = Individual(actions = Seq(MOVE_NORTH), fitness = Int.MinValue)

	trait context {
		val gaConfig = GAConfig(numberOfHuntingSessions = 3)
		val mapGenerator = mock[TreasureMapGenerator]
		val treasureMap = mock[TreasureMap]

		val individualFitnessCalculator = new IndividualFitnessCalculator(gaConfig, mapGenerator)
	}

	"calculate average fitness for an invididual" in new context {
		given(mapGenerator next()) willReturn treasureMap
		given(treasureMap fitnessFor UNFIT_INDIVIDUAL) willReturn(80, 150, 70)

		val averageFitness = individualFitnessCalculator averageFitnessFor UNFIT_INDIVIDUAL

		averageFitness should be(100)
	}
	
}
