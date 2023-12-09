package com.example.unshelf.view.marketplaceMain

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
//import com.bumptech.glide.Glide
//import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.example.unshelf.R
import com.example.unshelf.model.entities.Product

class mm_discounted_goods_recyclerview(private val datalist: ArrayList<Product>) : RecyclerView.Adapter<mm_discounted_goods_recyclerview.MyViewHolder>() {

    interface OnItemClickListener {
        fun onItemClick(position: Int)
    }
    fun setOnItemClickListener(listener: OnItemClickListener) {
        onItemClickListener = listener
    }
    // Step 2: Create a member variable for the click listener
    private var onItemClickListener: OnItemClickListener? = null

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): mm_discounted_goods_recyclerview.MyViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.rv_mm_so_card, parent, false)
        return mm_discounted_goods_recyclerview.MyViewHolder(view)
    }

    override fun onBindViewHolder(
        holder: mm_discounted_goods_recyclerview.MyViewHolder,
        position: Int
    ) {
        holder.itemView.setOnClickListener {
            onItemClickListener?.onItemClick(position)
        }
//        Glide.with(holder.itemView.context)
//            .load(R.drawable.fruit_salad_img) // Replace with the actual image URL or resource
//            .transition(DrawableTransitionOptions.withCrossFade())
//            .into(holder.rvProductImage)
//
//        holder.rvPrice.text = "₱" + datalist[position].marketPrice.toString()
        holder.rvProductName.text = datalist[position].productName
        holder.rvDiscount.text =  "8% off"
    }

    override fun getItemCount(): Int {
        return 5;
    }

    class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)  {
        val rvProductImage: ImageView = itemView.findViewById(R.id.mm_product_img)
        val rvPrice: TextView = itemView.findViewById(R.id.mm_price)
        val rvProductName: TextView = itemView.findViewById(R.id.mm_product_name)
        val rvDiscount: TextView = itemView.findViewById(R.id.mm_discount)
    }
}