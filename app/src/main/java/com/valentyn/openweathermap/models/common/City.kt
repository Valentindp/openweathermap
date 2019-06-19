package com.valentyn.openweathermap.models.common

import com.google.gson.annotations.SerializedName

data class City (
    @field:SerializedName("id")
    val id: Int? = null,

    @field:SerializedName("name")
    val name: String? = null,

    @field:SerializedName("coord")
    val coordData: Coord? = null,

    @field:SerializedName("country")
    val countryCode: String? = null,

    @field:SerializedName("population")
    val population: Long? = null
) {
}