package com.valentyn.openweathermap.models.common

import com.google.gson.annotations.SerializedName

data class Weather(
    @field:SerializedName("id")
    val conditionId: Int? = null,

    @field:SerializedName("main")
    val mainInfo: String? = null,

    @field:SerializedName("description")
    val moreInfo: String? = null,

    @field:SerializedName("icon")
    val iconCode: String? = null
) {
}