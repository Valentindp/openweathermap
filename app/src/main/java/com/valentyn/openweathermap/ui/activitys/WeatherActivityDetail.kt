package com.valentyn.openweathermap.ui.activitys

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.valentyn.openweathermap.R
import com.valentyn.openweathermap.models.CurrentWeather
import com.valentyn.openweathermap.models.DailyWeatherForecastData
import com.valentyn.openweathermap.ui.adapters.ForecastWeatherAdapter
import com.valentyn.openweathermap.ui.presenters.WeatherDetailContract
import com.valentyn.openweathermap.ui.presenters.WeatherDetailPresenter
import com.valentyn.openweathermap.util.Injection
import com.valentyn.openweathermap.util.getFormatDate
import com.valentyn.openweathermap.util.getFormatTemp
import kotlinx.android.synthetic.main.weather_activity_detail.*
import java.util.*
import kotlin.collections.ArrayList

class WeatherActivityDetail : AppCompatActivity(), WeatherDetailContract.View {


    override lateinit var presenter: WeatherDetailContract.Presenter
    private val forecastWeatherAdapter = ForecastWeatherAdapter(ArrayList())

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.weather_activity_detail)

        val cityId = intent.getIntExtra(EXTRA_CITY_ID, 0)

        presenter = WeatherDetailPresenter(cityId, Injection.provideWeatherRepository(applicationContext), this)

        rv_forecast_weather.apply {
            setHasFixedSize(false)
            isHorizontalScrollBarEnabled = true
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
            adapter = forecastWeatherAdapter
        }

        to_list.setOnClickListener { onBackPressed() }
    }

    override fun onResume() {
        super.onResume()
        presenter.start()
    }


    override fun showCityTitle(cityTitle: String?) {
        city_title.text = cityTitle
    }

    override fun showDate(date: Date?) {
        weather_date.text = getFormatDate(date)
    }

    override fun showForecastWeather(list: List<DailyWeatherForecastData>) {
        forecastWeatherAdapter.updateData(list)
    }

    override fun showDetails(details: CurrentWeather) {

        var description : String? = null

        if (!details.weatherList.isNullOrEmpty()) {
             description = details.weatherList!![0]?.moreInfo?.toUpperCase()
        }
        weather_details.text =
            getString(R.string.weather_details, description ?: "--", details.main?.humidity.toString(), details.main?.pressure.toString())
    }

    override fun showCurrentTemperature(temp: Double?) {
        current_temperature.text = getFormatTemp(temp)
    }

    override fun showMissingData() {
        city_title.text = ""
        weather_date.text = ""
        weather_details.text = ""
        current_temperature.text = getString(R.string.no_data)
    }

    companion object {
        const val EXTRA_CITY_ID = "CITY_ID"
    }

}