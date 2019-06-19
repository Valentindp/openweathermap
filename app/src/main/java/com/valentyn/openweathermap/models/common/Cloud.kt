package com.valentyn.openweathermap.models.common

import com.google.gson.annotations.SerializedName

data class Cloud (
    @field:SerializedName("all")
    val cloud: Double? = null
){
}