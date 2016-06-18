package com.codurance.treasurehunting.genetic_algorithm

import com.codurance.treasurehunting.genetic_algorithm.evolution._
import com.codurance.treasurehunting.genetic_algorithm.initial_population.{RandomIndividualGenerator, RandomPopulationGenerator}
import com.codurance.treasurehunting.genetic_algorithm.map.{TreasureGenerator, TreasureMapGenerator}

object TreasureHuntingGALauncher extends App {

	val gaConfig = new GAConfig()

	val randomIndividualGenerator = new RandomIndividualGenerator()
	val randomPopulationGenerator = new RandomPopulationGenerator(randomIndividualGenerator)

	val treasureGenerator = new TreasureGenerator(gaConfig)
	val treasureMapGenerator = new TreasureMapGenerator(gaConfig, treasureGenerator)

	val individualFitnessForMapCalculator = new IndividualFitnessForMapCalculator
	val individualFitnessCalculator = new IndividualFitnessCalculator(gaConfig, treasureMapGenerator, individualFitnessForMapCalculator)
	val populationFitnessCalculator = new PopulationFitnessCalculator(individualFitnessCalculator)
	val generation = new Generation()
	val evolution = new Evolution(gaConfig, populationFitnessCalculator, generation)

	val treasureHuntingGA = new TreasureHuntingGA(randomPopulationGenerator, evolution, gaConfig.numberOfIndividuals)

	println(treasureHuntingGA.generateFittestIndividual().stringRepresentation())

}
