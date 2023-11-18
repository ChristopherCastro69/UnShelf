package com.example.unshelf.view.RestaurantNearMe

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.unshelf.R

class AdapterRestaurantNearMe(private val restaurantsNearMeList: ArrayList<DataRestaurantNearMe>): RecyclerView.Adapter<AdapterRestaurantNearMe.ViewHolder>() {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): AdapterRestaurantNearMe.ViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.rv_item_popular_restaurant, parent, false);
        return ViewHolder(itemView);
    }

    override fun onBindViewHolder(holder: AdapterRestaurantNearMe.ViewHolder, position: Int) {
        val currentRestaurant = restaurantsNearMeList[position];
        holder.ivThumbnail.setImageResource(currentRestaurant.thumbnail);
        holder.ivStar.setImageResource(currentRestaurant.star);
        holder.tvRestaurantName.text = currentRestaurant.restaurantName;
        holder.tvRating.text = currentRestaurant.rating;
        holder.tvCuisine.text = currentRestaurant.cuisine;
        holder.tvTime.text = currentRestaurant.time;
    }

    override fun getItemCount(): Int {
        return restaurantsNearMeList.size;
    }


    class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        val ivThumbnail:ImageView = itemView.findViewById(R.id.ivThumbnail);
        val ivStar:ImageView = itemView.findViewById(R.id.ivStar);
        val tvRestaurantName:TextView = itemView.findViewById(R.id.tvRestaurantName);
        val tvRating:TextView = itemView.findViewById(R.id.tvRating);
        val tvCuisine:TextView = itemView.findViewById(R.id.tvCuisine);
        val tvTime:TextView = itemView.findViewById(R.id.tvTime);
    }
}