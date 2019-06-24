package com.valentyn.openweathermap.ui.activitys

import android.content.Intent
import android.os.Bundle
import android.support.v4.content.ContextCompat
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.helper.ItemTouchHelper
import android.widget.Toast
import com.valentyn.openweathermap.models.CurrentWeather
import com.valentyn.openweathermap.ui.adapters.CurrentWeatherAdapter
import com.valentyn.openweathermap.ui.presenters.WeatherPresenter
import com.valentyn.openweathermap.ui.presenters.WeatherContract
import com.valentyn.openweathermap.util.Injection
import com.valentyn.openweathermap.util.RecyclerViewItemDecoration
import kotlinx.android.synthetic.main.weather_activity.*
import com.valentyn.openweathermap.util.SwipeToDeleteCallback
import com.arellomobile.mvp.MvpAppCompatActivity
import com.arellomobile.mvp.presenter.InjectPresenter
import com.arellomobile.mvp.presenter.PresenterType
import com.arellomobile.mvp.presenter.ProvidePresenter
import com.valentyn.openweathermap.R

class WeatherActivity : MvpAppCompatActivity(), WeatherContract {

    @InjectPresenter(type = PresenterType.GLOBAL)
    lateinit var weatherPresenter: WeatherPresenter

    @ProvidePresenter(type = PresenterType.GLOBAL)
    fun provideWeatherPresenter() = WeatherPresenter(Injection.provideWeatherRepository(applicationContext))

    internal var itemListener: WeatherItemListener = object : WeatherItemListener {

        override fun onWeatherClick(clickedCurrentWeather: CurrentWeather) {
            weatherPresenter.openCurrentWeatherDetails(clickedCurrentWeather)
        }

        override fun onDeleteItem(currentWeather: CurrentWeather) {
            weatherPresenter.deleteCity(currentWeather)
        }
    }

    private val currentWeatherAdapter: CurrentWeatherAdapter =
        CurrentWeatherAdapter(ArrayList(), itemListener)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.weather_activity)

        recycler_view_weather.apply {
            addItemDecoration(
                RecyclerViewItemDecoration(ContextCompat.getDrawable(context, R.drawable.recycler_view_item_decoration))
            )
            setHasFixedSize(true)
            layoutManager = LinearLayoutManager(context)
            adapter = currentWeatherAdapter

        }

        val itemTouchHelper = ItemTouchHelper(SwipeToDeleteCallback(baseContext, currentWeatherAdapter))
        itemTouchHelper.attachToRecyclerView(recycler_view_weather)

        fab_add_city.setOnClickListener { weatherPresenter.addNewCity()
        }
    }

    override fun onResume() {
        super.onResume()
        weatherPresenter.start()
    }

    override fun showCurrentWeather(currentWeatherList: List<CurrentWeather>) {
        currentWeatherAdapter.updateData(currentWeatherList)
    }

    override fun showAddCity() {
        val intent = Intent(this, AddCityActivity::class.java)
        startActivityForResult(intent, AddCityActivity.REQUEST_ADD_CITY)
    }

    override fun showCurrentWeatherDetailsUi(cityId: Int?) {

        val intent = Intent(this, WeatherActivityDetail::class.java).apply {
            putExtra(WeatherActivityDetail.EXTRA_CITY_ID, cityId)
        }
        startActivity(intent)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        weatherPresenter.result(requestCode, resultCode)
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

    override fun showMessageDeleteItem(cityTitle: String) {
        Toast.makeText(this, getString(R.string.rv_item_deleted, cityTitle), Toast.LENGTH_LONG).show()
    }

    interface WeatherItemListener {

        fun onWeatherClick(clickedCurrentWeather: CurrentWeather)

        fun onDeleteItem(currentWeather: CurrentWeather)

    }

}
