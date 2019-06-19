package com.valentyn.openweathermap.models.common

import com.google.gson.annotations.SerializedName

data class Rain (
    @field:SerializedName("3h")
    val precipVol3h: Double? = null
){
}