package com.udacity.asteroid.ui.adapters

import android.os.Parcel
import android.os.Parcelable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.udacity.asteroid.R
import com.udacity.asteroid.data.pojo.Asteroid
import com.udacity.asteroid.databinding.AsteroidListItemBinding

class AsteroidAdapter() : RecyclerView.Adapter<AsteroidAdapter.AsteroidViewHolder>() {

    private val asteroids = ArrayList<Asteroid>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AsteroidViewHolder {
        return AsteroidViewHolder.from(parent)


    }

    override fun onBindViewHolder(holder: AsteroidViewHolder, position: Int) {
        val asteroidsItem = asteroids[position]
        holder.bind(asteroidsItem)
    }

    override fun getItemCount(): Int = asteroids.size

    fun submitAsteroids(arrayList: ArrayList<Asteroid>){
        asteroids.addAll(arrayList)
    }

    class AsteroidViewHolder private constructor(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val binding = AsteroidListItemBinding.bind(itemView)

        fun bind(asteroidsItem: Asteroid){
            binding.asteroid = asteroidsItem
        }

        companion object {
            fun from(parent: ViewGroup): AsteroidViewHolder {
                val view = LayoutInflater.from(parent.context)
                    .inflate(R.layout.asteroid_list_item, parent, false)
                return AsteroidViewHolder(view)
            }
        }
    }
}