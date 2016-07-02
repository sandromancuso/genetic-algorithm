package com.codurance.treasurehunting.genetic_algorithm

import com.codurance.treasurehunting.genetic_algorithm.map.TreasureMapDimension

case class GAConfig(generations: Int = 1000,
                    numberOfHuntingSessions: Int = 100,
                    numberOfIndividuals: Int = 300,
                    treasureMapDimension: TreasureMapDimension = TreasureMapDimension(10, 10),
                    numberOfMapsInTheCache: Int = 1000,
                    numberOfTreasuresPerMap: Int = 50)
