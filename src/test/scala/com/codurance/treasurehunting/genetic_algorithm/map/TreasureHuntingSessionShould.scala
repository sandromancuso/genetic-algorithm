package com.codurance.treasurehunting.genetic_algorithm.map

import com.codurance.UnitSpec
import com.codurance.treasurehunting.domain.Action._
import com.codurance.treasurehunting.domain.{Individual, Site, Situation}
import org.mockito.BDDMockito.given
import org.mockito.Mockito

class TreasureHuntingSessionShould extends UnitSpec {

	trait context {
		val situation = mock[Situation]
		val treasureMap = Mockito.spy(new TreasureMap())
		val individual = mock[Individual]

		val numberOfActionsToExecute = 1

		var treasureHuntingSession = new TreasureHuntingSession(treasureMap, individual, numberOfActionsToExecute)
	}

	"start on site 0,0" in new context {
		treasureHuntingSession.currentSite should be (Site(0,0))
	}

	"have score of 0 before start" in new context {
		treasureHuntingSession.score should be (0)
	}

	"lose five points if moving north and hit a wall" in new context {
		given(treasureMap situationFor Site(0, 0)) willReturn situation
		given(individual actionFor situation) willReturn MOVE_NORTH

		treasureHuntingSession run()

		treasureHuntingSession.score should be(-5)
		treasureHuntingSession.currentSite should be(Site(0, 0))
	}

	"lose five points if moving west and hit a wall" in new context {
		given(treasureMap situationFor Site(0, 0)) willReturn situation
		given(individual actionFor situation) willReturn MOVE_WEST

		treasureHuntingSession run()

		treasureHuntingSession.score should be(-5)
		treasureHuntingSession.currentSite should be(Site(0, 0))
	}

	"lose five points if moving east and hit a wall" in new context {
		given(treasureMap situationFor Site(9, 0)) willReturn situation
		given(individual actionFor situation) willReturn MOVE_EAST
		treasureHuntingSession.currentSite = Site(9, 0)

		treasureHuntingSession run()

		treasureHuntingSession.score should be(-5)
		treasureHuntingSession.currentSite should be(Site(9, 0))
	}

	"lose five points if moving south and hit a wall" in new context {
		given(treasureMap situationFor Site(0, 9)) willReturn situation
		given(individual actionFor situation) willReturn MOVE_SOUTH
		treasureHuntingSession.currentSite = Site(0, 9)

		treasureHuntingSession run()

		treasureHuntingSession.score should be(-5)
		treasureHuntingSession.currentSite should be(Site(0, 9))
	}

	"lose one point if picking up a treasure in an empty site" in new context {
		given(treasureMap situationFor Site(0, 0)) willReturn situation
		given(individual actionFor situation) willReturn PICK_UP_TREASURE

		treasureHuntingSession run()

		treasureHuntingSession.score should be(-1)
		treasureHuntingSession.currentSite should be(Site(0, 0))
	}

	"win ten points if it picks up a treasure" in new context {
		given(treasureMap hasTreasureAt Site(0, 0)) willReturn true
		given(treasureMap situationFor Site(0, 0)) willReturn situation
		given(individual actionFor situation) willReturn PICK_UP_TREASURE

		treasureHuntingSession run()

		treasureHuntingSession.score should be(10)
		treasureHuntingSession.currentSite should be(Site(0, 0))
	}

	"move north" in new context {
		given(treasureMap situationFor Site(0, 3)) willReturn situation
		given(individual actionFor situation) willReturn MOVE_NORTH
		treasureHuntingSession.currentSite = Site(0, 3)

		treasureHuntingSession run()

		treasureHuntingSession.score should be(0)
		treasureHuntingSession.currentSite should be(Site(0, 2))
	}

	"move south" in new context {
		given(treasureMap situationFor Site(0, 0)) willReturn situation
		given(individual actionFor situation) willReturn MOVE_SOUTH

		treasureHuntingSession run()

		treasureHuntingSession.score should be(0)
		treasureHuntingSession.currentSite should be(Site(0, 1))
	}

	"move west" in new context {
		given(treasureMap situationFor Site(3, 0)) willReturn situation
		given(individual actionFor situation) willReturn MOVE_WEST
		treasureHuntingSession.currentSite = Site(3, 0)

		treasureHuntingSession run()

		treasureHuntingSession.score should be(0)
		treasureHuntingSession.currentSite should be(Site(2, 0))
	}

	"move east" in new context {
		given(treasureMap situationFor Site(0, 0)) willReturn situation
		given(individual actionFor situation) willReturn MOVE_EAST

		treasureHuntingSession run()

		treasureHuntingSession.score should be(0)
		treasureHuntingSession.currentSite should be(Site(1, 0))
	}

	"execute multiple actions" in new context {
		val situation1 = mock[Situation]
		val situation2 = mock[Situation]
		val situation3 = mock[Situation]
		val situation4 = mock[Situation]

		given(treasureMap situationFor Site(0, 0)) willReturn situation1
		given(individual actionFor situation1) willReturn MOVE_EAST

		given(treasureMap hasTreasureAt Site(1, 0)) willReturn true
		given(treasureMap situationFor Site(1, 0)) willReturn (situation2, situation3)
		given(individual actionFor situation2) willReturn PICK_UP_TREASURE
		given(individual actionFor situation3) willReturn MOVE_EAST

		given(treasureMap situationFor Site(2, 0)) willReturn situation4
		given(individual actionFor situation4) willReturn MOVE_NORTH

		treasureHuntingSession = new TreasureHuntingSession(treasureMap, individual, 4)

		treasureHuntingSession run()

		treasureHuntingSession.score should be(5)
		treasureHuntingSession.currentSite should be(Site(2, 0))
	}

}
