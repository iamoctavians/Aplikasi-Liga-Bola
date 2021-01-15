package com.pcsbambang.app.plesbol.view

import com.pcsbambang.app.plesbol.model.EventDetails
import com.pcsbambang.app.plesbol.model.TeamDetails

interface EventDetailsView {
    fun showLoading()
    fun hideLoading()
    fun showEVentDetails(
        eventDetails: List<EventDetails>,
        homeTeamDetails: List<TeamDetails>,
        awayTeamDetails: List<TeamDetails>
    )
}