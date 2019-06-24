package com.valentyn.openweathermap.ui.adapters

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.valentyn.openweathermap.R
import com.valentyn.openweathermap.models.DailyWeatherForecastData
import com.valentyn.openweathermap.util.getFormatDate
import com.valentyn.openweathermap.util.getFormatTemp
import kotlinx.android.synthetic.main.weather_forecast_item.view.*
import kotlinx.android.synthetic.main.weather_item.view.temp

class ForecastWeatherAdapter(var list: List<DailyWeatherForecastData>) :
    RecyclerView.Adapter<ForecastWeatherAdapter.ItemHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = ItemHolder(
        LayoutInflater.from(parent.context)
            .inflate(R.layout.weather_forecast_item, parent, false)
    )

    override fun getItemCount() = list.size

    override fun onBindViewHolder(holder: ItemHolder, position: Int) {
        holder.bindData(list[position])
    }

    fun updateData(newList: List<DailyWeatherForecastData>) {
        list = newList
        notifyDataSetChanged()
    }

    class ItemHolder(val view: View) : RecyclerView.ViewHolder(view) {
        fun bindData(item: DailyWeatherForecastData) {

            view.apply {
                dt_forecast.text = getFormatDate(item.dateTime)
                temp.text = getFormatTemp(item.main?.temp)
                description.text = item.weatherList?.get(0)?.moreInfo?.toUpperCase()
                humidity.text = item.main?.humidity.toString()
                pressure.text = item.main?.pressure.toString()
            }
        }
    }
}