package com.valentyn.openweathermap.source.remote

import com.valentyn.openweathermap.models.CurrentWeather
import com.valentyn.openweathermap.models.CurrentWeatherList
import com.valentyn.openweathermap.source.WeatherDataSource
import org.json.JSONException
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.IOException
import java.net.HttpURLConnection

object WeatherRemoteDataSource : WeatherDataSource {

    private const val apiKey = "d48cbca4126b1d23452ae6654400e4b0"

    private const val APPID = "appId"
    private const val UNITS = "units"
    private const val LANGUAGE = "lang"
    private const val QUERY = "q"
    private const val CITY_ID = "id"

    private val weatherApi = WeatherApi.getClient()
    private val defOptions: HashMap<String, String> = hashMapOf(APPID to apiKey, LANGUAGE to "ru", UNITS to "metric")

    override fun getCurrentWeatherByCityName(cityName: String, callback: WeatherDataSource.GetCurrentWeatherCallback) {

        val options = HashMap(defOptions).apply { put(QUERY, cityName) }

        weatherApi.getCurrentWeatherByCityName(options).enqueue(object : Callback<CurrentWeather> {
            override fun onResponse(call: Call<CurrentWeather>, response: Response<CurrentWeather>) {
                handleCurrentWeatherResponse(response, callback)
            }

            override fun onFailure(call: Call<CurrentWeather>, throwable: Throwable) {
                callback.onDataNotAvailable(throwable)
            }
        })
    }
    
    override fun getCurrentWeatherList(listId: List<Int>, callback: WeatherDataSource.LoadCurrentWeatherListCallback) {

        val stringId = listId.toString().substring(1, listId.toString().length - 1).replace(" ", "")

        val options = HashMap(defOptions).apply { put(CITY_ID, stringId) }

        weatherApi.getCurrentWeatherByArrayCityID(options).enqueue(object : Callback<CurrentWeatherList> {
            override fun onResponse(call: Call<CurrentWeatherList>, response: Response<CurrentWeatherList>) {
                handleListCurrentWeatherResponse(response, callback)
            }

            override fun onFailure(call: Call<CurrentWeatherList>, throwable: Throwable) {
                callback.onDataNotAvailable(throwable)
            }
        })
    }

    override fun createCurrentWeather(currentWeather: CurrentWeather) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun updateCurrentWeather(currentWeatherId: Int) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun deleteCurrentWeather(currentWeatherId: Int) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
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

    private fun handleCurrentWeatherResponse(
        response: Response<CurrentWeather>,
        callback: WeatherDataSource.GetCurrentWeatherCallback
    ) {
        when {
            response.code() == HttpURLConnection.HTTP_OK && response.body() != null -> callback.onCurrentWeatherLoaded(
                response.body()!!
            )
            response.code() == HttpURLConnection.HTTP_FORBIDDEN || response.code() == HttpURLConnection.HTTP_UNAUTHORIZED -> callback.onDataNotAvailable(
                appIdErrMessage()
            )
            else -> try {
                callback.onDataNotAvailable(errorMsg(response.errorBody()!!.string()))
            } catch (e: IOException) {
                e.printStackTrace()
            }
        }
    }

    private fun handleListCurrentWeatherResponse(
        response: Response<CurrentWeatherList>,
        callback: WeatherDataSource.LoadCurrentWeatherListCallback
    ) = when {
        response.code() == HttpURLConnection.HTTP_OK && response.body() != null -> {
            callback.onCurrentWeatherListLoaded((response.body()!!.list ?: listOf<CurrentWeather>()) as List<CurrentWeather>)
        }
        response.code() == HttpURLConnection.HTTP_FORBIDDEN || response.code() == HttpURLConnection.HTTP_UNAUTHORIZED -> callback.onDataNotAvailable(
            appIdErrMessage()
        )
        else -> try {
            callback.onDataNotAvailable(errorMsg(response.errorBody()!!.string()))
        } catch (e: IOException) {
            e.printStackTrace()
        }
    }
}