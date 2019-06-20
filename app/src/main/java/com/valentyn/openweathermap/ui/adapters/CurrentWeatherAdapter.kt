package com.valentyn.openweathermap.ui.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.valentyn.openweathermap.R
import com.valentyn.openweathermap.models.CurrentWeather
import kotlinx.android.synthetic.main.item_weather_list.view.*

class CurrentWeatherAdapter(var list: List<CurrentWeather>) : RecyclerView.Adapter<CurrentWeatherAdapter.ItemHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = ItemHolder(
        LayoutInflater.from(parent.context)
            .inflate(R.layout.item_weather_list, parent, false)
    )

    override fun getItemCount() = list.size

    override fun onBindViewHolder(holder: ItemHolder, position: Int) {
        holder.bindData(list[position])
    }

    fun updateData(newList: List<CurrentWeather>) {
        list = newList
        notifyDataSetChanged()
    }

    class ItemHolder(val view: View) : RecyclerView.ViewHolder(view) {
        fun bindData(item: CurrentWeather) {

            /*
            Picasso.get()
                .load(PlayerUtils.getAlbumArtUri(item.id))
                .fit()
                .error(R.drawable.ic_album_blue_24dp)
                .into(view.album_card_image)

                 */

            view.apply {
                dt.text = item.dateTime.toString()
                city_title.text = item.cityName
                temp.text = item.main?.temp.toString()

            }

        }
    }
}