package com.codurance.treasurehunting.domain

case class Population(individuals: Individual*) {

	def fittestIndividual(): Individual = individuals.maxBy(_.fitness)

	def size() = individuals.size

}

