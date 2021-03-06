package com.pcsbambang.app.plesbol.model

import com.google.gson.annotations.SerializedName

data class Events(
    @SerializedName("idEvent")
    var eventId: String? = null,

    @SerializedName("strSport")
    var sport: String? = null,

    @SerializedName("idHomeTeam")
    var homeTeamId: String? = null,

    @SerializedName("idAwayTeam")
    var awayTeamId: String? = null,

    @SerializedName("strHomeTeam")
    var homeTeam: String? = null,

    @SerializedName("strAwayTeam")
    var awayTeam: String? = null,

    @SerializedName("intHomeScore")
    var homeScore: String? = null,

    @SerializedName("intAwayScore")
    var awayScore: String? = null,

    @SerializedName("dateEvent")
    var dateEvent: String? = null
)