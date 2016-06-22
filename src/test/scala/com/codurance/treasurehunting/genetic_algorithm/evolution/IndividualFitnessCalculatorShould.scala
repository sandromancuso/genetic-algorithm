package com.codurance.treasurehunting.genetic_algorithm.evolution

import com.codurance.UnitSpec
import com.codurance.treasurehunting.domain.Action.MOVE_NORTH
import com.codurance.treasurehunting.domain.Individual
import com.codurance.treasurehunting.genetic_algorithm.GAConfig
import com.codurance.treasurehunting.genetic_algorithm.map.{TreasureMap, TreasureMapGenerator}
import org.mockito.BDDMockito.given
import org.mockito.Matchers.{same, eq, any}
import org.mockito.{Matchers, Mockito}
import org.mockito.Mockito.verify
import org.mockito.internal.matchers.Any

class IndividualFitnessCalculatorShould extends UnitSpec {
	

	val UNFIT_INDIVIDUAL = Individual(actions = Seq(MOVE_NORTH), fitness = Int.MinValue)

	trait context {
		val mapGenerator = mock[TreasureMapGenerator]
		val individualFitnessForMapCalculator = mock[IndividualFitnessForMapCalculator]

		val NUMBER_OF_HUNTING_SESSIONS: Int = 3

		val treasureMap_1 = mock[TreasureMap]
		val treasureMap_2 = mock[TreasureMap]
		val treasureMap_3 = mock[TreasureMap]

		val individualFitnessCalculator =
			new IndividualFitnessCalculator(NUMBER_OF_HUNTING_SESSIONS, mapGenerator, individualFitnessForMapCalculator)
	}

	"calculate individual fitness for each treasure hunting session" in new context {
		given(mapGenerator next()) willReturn (treasureMap_1, treasureMap_2, treasureMap_3)

		individualFitnessCalculator averageFitnessFor UNFIT_INDIVIDUAL

		verify(individualFitnessForMapCalculator) calculateFitness(treasureMap_1, UNFIT_INDIVIDUAL)
		verify(individualFitnessForMapCalculator) calculateFitness(treasureMap_2, UNFIT_INDIVIDUAL)
		verify(individualFitnessForMapCalculator) calculateFitness(treasureMap_3, UNFIT_INDIVIDUAL)
	}
	
	"calculate individual average fitness" in new context {
		given(mapGenerator next()) willReturn (treasureMap_1, treasureMap_2, treasureMap_3)
	    given(individualFitnessForMapCalculator.calculateFitness(any[TreasureMap], same((UNFIT_INDIVIDUAL))))
			        .willReturn(70, 90, 150)

		val averageFitness = individualFitnessCalculator averageFitnessFor UNFIT_INDIVIDUAL

		averageFitness should be ((70 + 90 + 150) / 3)
	}

}
