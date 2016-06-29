package com.codurance.treasurehunting.genetic_algorithm.evolution

import com.codurance.UnitSpec
import com.codurance.treasurehunting.domain.Action.{MOVE_NORTH, MOVE_SOUTH}
import com.codurance.treasurehunting.domain.{Individual, Population}
import com.codurance.treasurehunting.genetic_algorithm.GAConfig
import com.codurance.treasurehunting.genetic_algorithm.map.{TreasureMap, TreasureMapGenerator}
import org.mockito.BDDMockito.given
import org.mockito.Mockito
import org.mockito.Mockito.verify

class PopulationFitnessCalculatorShould extends UnitSpec {

	val TREASURE_MAP_1 = TreasureMap()
	val TREASURE_MAP_2 = TreasureMap()
	val TREASURE_MAPS: Seq[TreasureMap] = Seq(TREASURE_MAP_1, TREASURE_MAP_2)
	val UNFIT_INDIVIDUAL_1 = Individual(0, Seq(MOVE_NORTH))
	val UNFIT_INDIVIDUAL_2 = Individual(-10, Seq(MOVE_SOUTH))
	val UNFIT_POPULATION = Population(UNFIT_INDIVIDUAL_1, UNFIT_INDIVIDUAL_2)

	trait context {
		val gaConfig = GAConfig(numberOfHuntingSessions = 2)
		val treasureMapGenerator = mock[TreasureMapGenerator]
		val individualFitnessCalculator = mock[IndividualFitnessCalculator]
		val populationFitnessCalculator = new PopulationFitnessCalculator(gaConfig,
																			treasureMapGenerator,
																			individualFitnessCalculator)

		given(treasureMapGenerator next()) willReturn(TREASURE_MAP_1, TREASURE_MAP_2)
	}

	"calculate fitness for each individual in a population" in new context {
		given(individualFitnessCalculator averageFitnessFor(UNFIT_INDIVIDUAL_1, TREASURE_MAPS)) willReturn 80
		given(individualFitnessCalculator averageFitnessFor(UNFIT_INDIVIDUAL_2, TREASURE_MAPS)) willReturn 60

		val fitPopulation = populationFitnessCalculator calculateFitnessFor UNFIT_POPULATION

		fitPopulation.size should be (UNFIT_POPULATION.size())
		fitPopulation.individuals should contain(Individual(80, UNFIT_INDIVIDUAL_1.actions))
		fitPopulation.individuals should contain(Individual(60, UNFIT_INDIVIDUAL_2.actions))
	}

	"calculate individual fitness agains the same set of maps" in new context {
		val fitPopulation = populationFitnessCalculator calculateFitnessFor UNFIT_POPULATION

		verify(individualFitnessCalculator) averageFitnessFor(UNFIT_INDIVIDUAL_1, TREASURE_MAPS)
		verify(individualFitnessCalculator) averageFitnessFor(UNFIT_INDIVIDUAL_2, TREASURE_MAPS)
	}

}
