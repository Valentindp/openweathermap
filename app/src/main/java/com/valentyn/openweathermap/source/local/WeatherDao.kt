package com.valentyn.openweathermap.source.local

import android.arch.persistence.room.*
import com.valentyn.openweathermap.models.CurrentWeather
import com.valentyn.openweathermap.models.DailyWeatherForecastData

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
    fun deleteAllCurrentWeather()



    @Query("SELECT * FROM DailyWeatherForecastData")
    fun getDailyWeatherForecastDataByCityId(): List<DailyWeatherForecastData>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertDailyWeatherForecastData(dailyWeatherForecastData: DailyWeatherForecastData)

    @Update
    fun updateDailyWeatherForecastData(dailyWeatherForecastData: DailyWeatherForecastData)

    @Query("DELETE FROM DailyWeatherForecastData WHERE forecastCityId = :dailyWeatherForecastDataId")
    fun deleteDailyWeatherForecastDataById(dailyWeatherForecastDataId: Int)

    @Query("DELETE FROM DailyWeatherForecastData")
    fun deleteAllDailyWeatherForecastData()
}