package me.kalpanadhurpate.weatherapp.ui

import android.annotation.SuppressLint
import android.net.Uri
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import me.kalpanadhurpate.weatherapp.data.model.Latlon
import me.kalpanadhurpate.weatherapp.databinding.CityItemLayoutBinding

class CityListAdapter(private val latLonList: ArrayList<Latlon>) :
    RecyclerView.Adapter<CityListAdapter.DataViewHolder>() {

    var onItemClick: ((Latlon) -> Unit)? = null

    class DataViewHolder(private val binding: CityItemLayoutBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(latlon: Latlon) {
            binding.textViewTitle.text = buildString {
                append(latlon.name)
                append(", ")
                append(latlon.state)
                append(", ")
                append(latlon.country)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        DataViewHolder(
            CityItemLayoutBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )


    override fun getItemCount(): Int = latLonList.size


    override fun onBindViewHolder(holder: DataViewHolder, position: Int) {
        val latLon = latLonList[position]
        holder.bind(latLonList[position])
        holder.itemView.setOnClickListener {
            onItemClick?.invoke(latLon)
        }

    }

    fun addData(list: List<Latlon>) {
        latLonList.clear()
        latLonList.addAll(list)
    }

    fun clearData(){
        latLonList.clear()
        notifyDataSetChanged()

    }
}