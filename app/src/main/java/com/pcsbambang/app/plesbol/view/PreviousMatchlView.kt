package com.pcsbambang.app.plesbol.view

import com.pcsbambang.app.plesbol.model.Events

interface PreviousMatchlView {
    fun showLoading()
    fun hideLoading()
    fun showPrevMatch(prevMatch: List<Events>)
}