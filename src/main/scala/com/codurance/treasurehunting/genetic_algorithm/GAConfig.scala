package com.codurance.treasurehunting.genetic_algorithm

import com.codurance.treasurehunting.genetic_algorithm.map.TreasureMapDimension

case class GAConfig(generations: Int = 200,
                    numberOfHuntingSessions: Int = 100,
                    numberOfIndividuals: Int = 200,
                    treasureMapDimension: TreasureMapDimension = TreasureMapDimension(10, 10),
                    numberOfTreasuresPerMap: Int = 50)
