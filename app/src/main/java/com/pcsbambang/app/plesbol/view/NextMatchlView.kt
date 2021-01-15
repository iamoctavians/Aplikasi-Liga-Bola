package com.pcsbambang.app.plesbol.view

import com.pcsbambang.app.plesbol.model.Events

interface NextMatchlView {
    fun showLoading()
    fun hideLoading()
    fun showNextMatch(nextMatch: List<Events>)
}