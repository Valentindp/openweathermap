package com.valentyn.openweathermap.source.local

import android.arch.persistence.room.Database
import android.arch.persistence.room.Room
import android.arch.persistence.room.RoomDatabase
import android.arch.persistence.room.TypeConverters
import android.content.Context
import com.valentyn.openweathermap.models.CurrentWeather
import com.valentyn.openweathermap.models.DailyWeatherForecastData
import com.valentyn.openweathermap.models.common.WeatherConverter

@Database(entities = [CurrentWeather::class, DailyWeatherForecastData::class], version = 1, exportSchema = false)
@TypeConverters(WeatherConverter::class)
abstract class WeatherDatabase : RoomDatabase() {

    abstract fun weatherDao(): WeatherDao

    companion object {

        private var INSTANCE: WeatherDatabase? = null

        private val lock = Any()

        fun getInstance(context: Context): WeatherDatabase {
            synchronized(lock) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(
                        context.applicationContext,
                        WeatherDatabase::class.java, "Weather.db"
                    )
                        .build()
                }
                return INSTANCE!!
            }
        }
    }
}