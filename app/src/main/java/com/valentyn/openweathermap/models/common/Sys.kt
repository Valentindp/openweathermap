package com.valentyn.openweathermap.models.common

import com.google.gson.annotations.SerializedName

data class Sys(
    @field:SerializedName("type")
    val type: Int? = null,

    @field:SerializedName("id")
    val id: Int? = null,

    @field:SerializedName("message")
    val message: Double? = null,

    @field:SerializedName("country")
    val countryCode: String? = null,

    @field:SerializedName("sunrise")
    private val sunrise: Int? = null,

    @field:SerializedName("sunset")
    private val sunset: Int? = null
) {
}