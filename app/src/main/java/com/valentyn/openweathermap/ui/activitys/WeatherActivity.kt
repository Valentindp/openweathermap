package com.valentyn.openweathermap.ui.activitys

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.valentyn.openweathermap.R
import com.valentyn.openweathermap.models.CurrentWeather
import com.valentyn.openweathermap.ui.adapters.CurrentWeatherAdapter
import com.valentyn.openweathermap.ui.presenters.CurrentWeatherPresenter
import com.valentyn.openweathermap.util.Injection
import kotlinx.android.synthetic.main.activity_weather.*

class WeatherActivity : AppCompatActivity(), WeatherContract.View {

    override lateinit var presenter: WeatherContract.Presenter
    private val currentWeatherAdapter: CurrentWeatherAdapter = CurrentWeatherAdapter(ArrayList())

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_weather)

        presenter = CurrentWeatherPresenter(Injection.provideTasksRepository(), this)

        recycler_view_weather.apply {
            setHasFixedSize(true)
            layoutManager = LinearLayoutManager(context)
            adapter = currentWeatherAdapter
        }
    }

    override fun onResume() {
        super.onResume()
        presenter.start()
    }

    override fun setLoadingIndicator(active: Boolean) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun showCurrentWeather(currentWeatherList: List<CurrentWeather>) {
        currentWeatherAdapter.updateData(currentWeatherList)
    }

    override fun showAddCurrentWeather() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun showCurrentWeatherDetailsUi(cityId: Int) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun showCurrentWeatherError(message: String?) {
        showMessage(message ?: getString(R.string.Error_load_weather))
    }

    override fun showSuccessfullySavedMessage() {
        showMessage(getString(R.string.City_saved))
    }

    private fun showMessage(message: String) {
       Toast.makeText(this, message, Toast.LENGTH_LONG).show()
    }

}
