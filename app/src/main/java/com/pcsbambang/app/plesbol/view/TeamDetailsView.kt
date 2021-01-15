package com.pcsbambang.app.plesbol.view

import com.pcsbambang.app.plesbol.model.TeamDetails

interface TeamDetailsView {
    fun showLoading()
    fun hideLoading()
    fun showTeamDetails(data: List<TeamDetails>)
}