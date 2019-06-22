package com.valentyn.openweathermap.ui.activitys

import android.app.Activity
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.valentyn.openweathermap.R
import com.valentyn.openweathermap.ui.presenters.AddCityContract
import com.valentyn.openweathermap.ui.presenters.AddCityPresenter
import com.valentyn.openweathermap.util.Injection
import kotlinx.android.synthetic.main.add_weather_activity.*

class AddCityActivity : AppCompatActivity(), AddCityContract.View {

    override lateinit var presenter: AddCityContract.Presenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.add_weather_activity)

        presenter = AddCityPresenter(Injection.provideWeatherRepository(applicationContext), this)

        fab_add_city_done.apply {
            setImageResource(R.drawable.ic_done_white_24dp)
            setOnClickListener {
                presenter.saveCity(add_city_title.text.toString())
            }
        }

        button_cancel.setOnClickListener { onBackPressed() }
    }

    override fun showCurrentWeatherList() {
        setResult(Activity.RESULT_OK)
        finish()
    }

    override fun showNotFoundError(message: String?) {
        Toast.makeText(this, message ?: getString(R.string.Error_city_not_found), Toast.LENGTH_LONG).show()
    }

    companion object {
        const val REQUEST_ADD_CITY = 1
    }
}