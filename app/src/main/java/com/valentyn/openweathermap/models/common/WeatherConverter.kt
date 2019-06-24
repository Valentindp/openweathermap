package com.valentyn.openweathermap.models.common

import android.arch.persistence.room.TypeConverter
import com.google.gson.reflect.TypeToken
import com.google.gson.Gson

class WeatherConverter {

    @TypeConverter
    fun fromWeatherList(weatherList: List<Weather>?): String? {
        if (weatherList == null) {
            return null
        }
        val gson = Gson()
        val type = object : TypeToken<List<Weather>>() {

        }.type
        return gson.toJson(weatherList, type)
    }

    @TypeConverter
    fun toWeatherList(weatherString: String?): List<Weather>? {
        if (weatherString == null) {
            return null
        }
        val gson = Gson()
        val type = object : TypeToken<List<Weather>>() {

        }.type
        return gson.fromJson<List<Weather>>(weatherString, type)
    }
}