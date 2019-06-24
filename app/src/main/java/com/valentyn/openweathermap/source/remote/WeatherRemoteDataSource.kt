package com.valentyn.openweathermap.source.remote

import com.valentyn.openweathermap.models.CurrentWeather
import com.valentyn.openweathermap.models.CurrentWeatherList
import com.valentyn.openweathermap.models.DailyWeatherForecast
import com.valentyn.openweathermap.source.WeatherDataSource
import com.valentyn.openweathermap.util.AppExecutors
import org.json.JSONException
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.IOException
import java.net.HttpURLConnection

class WeatherRemoteDataSource private constructor(
    val appExecutors: AppExecutors
) {

    private val apiKey = "d48cbca4126b1d23452ae6654400e4b0"

    private val APPID = "appId"
    private val UNITS = "units"
    private val LANGUAGE = "lang"
    private val QUERY = "q"
    private val CITY_ID = "id"

    private val weatherApi = WeatherApi.getClient()
    private val defOptions: HashMap<String, String> = hashMapOf(APPID to apiKey, LANGUAGE to "en", UNITS to "metric")

    fun getCurrentWeatherByCityName(cityName: String, callback: WeatherDataSource.LoadWeatherData<CurrentWeather>) {

        appExecutors.networkIO.execute {
            val options = HashMap(defOptions).apply { put(QUERY, cityName) }

            weatherApi.getCurrentWeatherByCityName(options).enqueue(object : Callback<CurrentWeather> {
                override fun onResponse(call: Call<CurrentWeather>, response: Response<CurrentWeather>) {
                    handleCurrentWeatherResponse(response, callback)
                }

                override fun onFailure(call: Call<CurrentWeather>, throwable: Throwable) {
                    callback.onError(throwable)
                }
            })
        }
    }

    fun getCurrentWeatherByCityId(cityId: Int, callback: WeatherDataSource.LoadWeatherData<CurrentWeather>) {

        appExecutors.networkIO.execute {
            val options = HashMap(defOptions).apply { put(CITY_ID, cityId.toString()) }

            weatherApi.getCurrentWeatherByCityName(options).enqueue(object : Callback<CurrentWeather> {
                override fun onResponse(call: Call<CurrentWeather>, response: Response<CurrentWeather>) {
                    handleCurrentWeatherResponse(response, callback)
                }

                override fun onFailure(call: Call<CurrentWeather>, throwable: Throwable) {
                    callback.onError(throwable)
                }
            })
        }
    }

    fun getCurrentWeatherList(listId: List<Int>, callback: WeatherDataSource.LoadWeatherData<List<CurrentWeather>>) {

        appExecutors.networkIO.execute {
            val stringId = listId.toString().substring(1, listId.toString().length - 1).replace(" ", "")

            val options = HashMap(defOptions).apply { put(CITY_ID, stringId) }

            weatherApi.getCurrentWeatherByArrayCityID(options).enqueue(object : Callback<CurrentWeatherList> {
                override fun onResponse(call: Call<CurrentWeatherList>, response: Response<CurrentWeatherList>) {
                    handleListCurrentWeatherResponse(response, callback)
                }

                override fun onFailure(call: Call<CurrentWeatherList>, throwable: Throwable) {
                    callback.onError(throwable)
                }
            })
        }
    }

    fun getDailyWeatherForecastList(cityId: Int, callback: WeatherDataSource.LoadWeatherData<DailyWeatherForecast>) {

        appExecutors.networkIO.execute {

            val options = HashMap(defOptions).apply { put(CITY_ID, cityId.toString()) }

            weatherApi.getDailyWeatherForecastByCityID(options).enqueue(object : Callback<DailyWeatherForecast> {
                override fun onResponse(call: Call<DailyWeatherForecast>, response: Response<DailyWeatherForecast>) {
                    handleListDailyWeatherForecastResponse(response, callback)
                }

                override fun onFailure(call: Call<DailyWeatherForecast>, throwable: Throwable) {
                    callback.onError(throwable)
                }
            })
        }
    }


    private fun handleCurrentWeatherResponse(
        response: Response<CurrentWeather>,
        callback: WeatherDataSource.LoadWeatherData<CurrentWeather>
    ) {
        when {
            response.code() == HttpURLConnection.HTTP_OK && response.body() != null -> callback.onSuccess(
                response.body()!!
            )
            response.code() == HttpURLConnection.HTTP_FORBIDDEN || response.code() == HttpURLConnection.HTTP_UNAUTHORIZED -> callback.onError(
                appIdErrMessage()
            )
            else -> try {
                callback.onError(errorMsg(response.errorBody()!!.string()))
            } catch (e: IOException) {
                e.printStackTrace()
            }
        }
    }

    private fun handleListCurrentWeatherResponse(
        response: Response<CurrentWeatherList>,
        callback: WeatherDataSource.LoadWeatherData<List<CurrentWeather>>
    ) = when {
        response.code() == HttpURLConnection.HTTP_OK && response.body() != null -> {
            callback.onSuccess(response.body()!!.list as List<CurrentWeather>)
        }
        response.code() == HttpURLConnection.HTTP_FORBIDDEN || response.code() == HttpURLConnection.HTTP_UNAUTHORIZED -> callback.onError(
            appIdErrMessage()
        )
        else -> try {
            callback.onError(errorMsg(response.errorBody()!!.string()))
        } catch (e: IOException) {
            e.printStackTrace()
        }
    }

    private fun handleListDailyWeatherForecastResponse(
        response: Response<DailyWeatherForecast>,
        callback: WeatherDataSource.LoadWeatherData<DailyWeatherForecast>
    ) = when {
        response.code() == HttpURLConnection.HTTP_OK && response.body() != null -> {
            callback.onSuccess(response.body()!!)
        }
        response.code() == HttpURLConnection.HTTP_FORBIDDEN || response.code() == HttpURLConnection.HTTP_UNAUTHORIZED -> callback.onError(
            appIdErrMessage()
        )
        else -> try {
            callback.onError(errorMsg(response.errorBody()!!.string()))
        } catch (e: IOException) {
            e.printStackTrace()
        }
    }

    private fun appIdErrMessage(): Throwable {
        return Throwable("UnAuthorized. Please set a valid OpenWeatherMap API KEY.")
    }

    private fun errorMsg(str: String): Throwable {
        var throwable: Throwable? = null
        try {
            val obj = JSONObject(str)
            throwable = Throwable(obj.getString("message"))
        } catch (e: JSONException) {
            e.printStackTrace()
        }

        if (throwable == null) {
            throwable = Throwable("An unexpected error occurred.")
        }
        return throwable
    }


    companion object {
        private var INSTANCE: WeatherRemoteDataSource? = null

        @JvmStatic
        fun getInstance(appExecutors: AppExecutors): WeatherRemoteDataSource {
            if (INSTANCE == null) {
                synchronized(WeatherRemoteDataSource::javaClass) {
                    INSTANCE = WeatherRemoteDataSource(appExecutors)
                }
            }
            return INSTANCE!!
        }
    }
}