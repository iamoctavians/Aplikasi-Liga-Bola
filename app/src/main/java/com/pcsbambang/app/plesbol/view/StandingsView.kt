package com.pcsbambang.app.plesbol.view

import com.pcsbambang.app.plesbol.model.Standings

interface StandingsView {
    fun showLoading()
    fun hideLoading()
    fun showStandings(data: List<Standings>)
}