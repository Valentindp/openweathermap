package com.valentyn.openweathermap.ui.adapters


import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.valentyn.openweathermap.R
import com.valentyn.openweathermap.models.CurrentWeather
import kotlinx.android.synthetic.main.weather_item.view.*
import com.valentyn.openweathermap.util.getFormatDate
import com.valentyn.openweathermap.util.getFormatTemp

class CurrentWeatherAdapter(var list: List<CurrentWeather>, private val clickCurrentWeatherListener: (CurrentWeather) -> Unit) :
    RecyclerView.Adapter<CurrentWeatherAdapter.ItemHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = ItemHolder(
        LayoutInflater.from(parent.context)
            .inflate(R.layout.weather_item, parent, false)
    )

    override fun getItemCount() = list.size

    override fun onBindViewHolder(holder: ItemHolder, position: Int) {
        holder.bindData(list[position], clickCurrentWeatherListener)
    }

    fun updateData(newList: List<CurrentWeather>) {
        list = newList
        notifyDataSetChanged()
    }

    class ItemHolder(val view: View) : RecyclerView.ViewHolder(view) {
        fun bindData(item: CurrentWeather, clickCurrentWeatherListener: (CurrentWeather) -> Unit) {

            view.apply {
                dt.text = getFormatDate(item.dateTime)
                city_title.text = item.cityName
                temp.text = getFormatTemp(item.main?.temp)
                setOnClickListener { clickCurrentWeatherListener(item) }
            }

        }
    }
}