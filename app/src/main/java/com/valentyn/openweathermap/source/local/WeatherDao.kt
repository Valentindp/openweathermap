package com.valentyn.openweathermap.source.local

import androidx.room.*
import com.valentyn.openweathermap.models.CurrentWeather

@Dao
interface WeatherDao {

    @Query("SELECT * FROM CurrentWeather")
    fun getCurrentWeather(): List<CurrentWeather>

    @Query("SELECT id FROM CurrentWeather")
    fun getCurrentWeatherListId(): List<Int>

    @Query("SELECT * FROM CurrentWeather WHERE id = :id")
    fun getCurrentWeatherById(id: Int): CurrentWeather

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertCurrentWeather(currentWeather: CurrentWeather)

    @Update
    fun updateCurrentWeather(currentWeather: CurrentWeather)

    @Query("DELETE FROM CurrentWeather WHERE id = :currentWeatherId")
    fun deleteCurrentWeatherById(currentWeatherId: Int)

    @Query("DELETE FROM CurrentWeather ")
    fun deleteCurrentWeather()
}