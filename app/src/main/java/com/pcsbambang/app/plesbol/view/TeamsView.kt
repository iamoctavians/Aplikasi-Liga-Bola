package com.pcsbambang.app.plesbol.view

import com.dicoding.picodiploma.myfootballclub.Team

interface TeamsView {
    fun showLoading()
    fun hideLoading()
    fun showTeamList(data: List<Team>)
}