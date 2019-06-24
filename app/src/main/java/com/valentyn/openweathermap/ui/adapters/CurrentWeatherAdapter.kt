package com.valentyn.openweathermap.ui.adapters

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.valentyn.openweathermap.R
import com.valentyn.openweathermap.models.CurrentWeather
import com.valentyn.openweathermap.ui.activitys.WeatherActivity
import kotlinx.android.synthetic.main.weather_item.view.*
import com.valentyn.openweathermap.util.getFormatDate
import com.valentyn.openweathermap.util.getFormatTemp

class CurrentWeatherAdapter(var list: MutableList<CurrentWeather>, private val itemListener : WeatherActivity.WeatherItemListener) :
    RecyclerView.Adapter<CurrentWeatherAdapter.ItemHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = ItemHolder(
        LayoutInflater.from(parent.context)
            .inflate(R.layout.weather_item, parent, false)
    )

    override fun getItemCount() = list.size

    override fun onBindViewHolder(holder: ItemHolder, position: Int) {
        holder.bindData(list[position], itemListener)
    }

    fun deleteItem(position: Int){
        itemListener.onDeleteItem(list[position])
        list.removeAt(position);
        notifyItemRemoved(position);
    }

    fun updateData(newList: List<CurrentWeather>) {
        list = newList as MutableList<CurrentWeather>
        notifyDataSetChanged()
    }

    class ItemHolder(val view: View) : RecyclerView.ViewHolder(view) {
        fun bindData(item: CurrentWeather, itemListener :  WeatherActivity.WeatherItemListener) {

            view.apply {
                dt_forecast.text = getFormatDate(item.dateTime)
                city_title.text = item.cityName
                temp.text = getFormatTemp(item.main?.temp)
                setOnClickListener { itemListener.onWeatherClick(item) }
            }

        }
    }
}