package com.valentyn.openweathermap.source.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.valentyn.openweathermap.models.CurrentWeather
import com.valentyn.openweathermap.models.DailyWeatherForecast
import com.valentyn.openweathermap.models.DailyWeatherForecastData
import com.valentyn.openweathermap.models.common.WeatherConverter

@Database(entities = [CurrentWeather::class, DailyWeatherForecastData::class], version = 1)
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
                        WeatherDatabase::class.java, "Tasks.db"
                    )
                        .build()
                }
                return INSTANCE!!
            }
        }
    }
}