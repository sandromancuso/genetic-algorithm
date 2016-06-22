package com.codurance.treasurehunting.domain

case class Situation(north: SiteState.Value,
                     south: SiteState.Value,
                     east: SiteState.Value,
                     west: SiteState.Value,
                     current: SiteState.Value)
