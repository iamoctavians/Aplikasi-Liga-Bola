package com.pcsbambang.app.plesbol.view

import com.pcsbambang.app.plesbol.model.LeagueDetail

interface LeagueDetailView {
    fun showLoading()
    fun hideLoading()
    fun showLeagueDetail(leagueDetail: List<LeagueDetail>)
}