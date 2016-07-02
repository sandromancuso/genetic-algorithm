package com.codurance.treasurehunting.genetic_algorithm

import com.codurance.treasurehunting.genetic_algorithm.evolution._
import com.codurance.treasurehunting.genetic_algorithm.initial_population.{RandomIndividualGenerator, RandomPopulationGenerator}
import com.codurance.treasurehunting.genetic_algorithm.map.{TreasureGenerator, TreasureMapGenerator}

object TreasureHuntingGALauncher extends App {

	val gaConfig = new GAConfig(numberOfIndividuals = 300)

	val randomIndividualGenerator = new RandomIndividualGenerator()
	val randomPopulationGenerator = new RandomPopulationGenerator(randomIndividualGenerator)

	val treasureGenerator = new TreasureGenerator(gaConfig)
	val treasureMapGenerator = new TreasureMapGenerator(gaConfig, treasureGenerator)

	val individualFitnessForMapCalculator = new IndividualFitnessForMapCalculator
	val individualFitnessCalculator = new IndividualFitnessCalculator(individualFitnessForMapCalculator)
	val populationFitnessCalculator = new PopulationFitnessCalculator(gaConfig, treasureMapGenerator, individualFitnessCalculator)
	val mutation = new Mutation
	val generation = new Generation(mutation)
	val evolution = new Evolution(gaConfig, populationFitnessCalculator, generation)

	val treasureHuntingGA = new TreasureHuntingGA(randomPopulationGenerator, evolution, gaConfig.numberOfIndividuals)

	println(treasureHuntingGA.generateFittestIndividual().stringRepresentation())

}
