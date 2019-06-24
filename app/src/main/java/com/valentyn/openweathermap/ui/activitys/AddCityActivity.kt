package com.valentyn.openweathermap.ui.activitys

import android.app.Activity
import android.os.Bundle
import android.widget.Toast
import com.arellomobile.mvp.MvpAppCompatActivity
import com.arellomobile.mvp.presenter.InjectPresenter
import com.arellomobile.mvp.presenter.PresenterType
import com.arellomobile.mvp.presenter.ProvidePresenter
import com.valentyn.openweathermap.R
import com.valentyn.openweathermap.ui.presenters.AddCityContract
import com.valentyn.openweathermap.ui.presenters.AddCityPresenter
import com.valentyn.openweathermap.util.Injection
import kotlinx.android.synthetic.main.add_weather_activity.*

class AddCityActivity : MvpAppCompatActivity(), AddCityContract {

    @InjectPresenter(type = PresenterType.GLOBAL)
    lateinit var addCityPresenter: AddCityPresenter

    @ProvidePresenter(type = PresenterType.GLOBAL)
    fun provideAddCityPresenter() = AddCityPresenter(Injection.provideWeatherRepository(applicationContext))

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.add_weather_activity)

        fab_add_city_done.apply {
            setOnClickListener {
                addCityPresenter.saveCity(add_city_title.text.toString())
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