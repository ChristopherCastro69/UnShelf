package com.example.unshelf.view.product

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.unshelf.R

class AdapterCartItem(private val datalist: ArrayList<CartItemData>) : RecyclerView.Adapter<AdapterCartItem.ViewHolderClass>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolderClass {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.rv_cart_item, parent, false)
        return ViewHolderClass(itemView)
    }

    override fun getItemCount(): Int {
        return datalist.size
    }

    override fun onBindViewHolder(holder: ViewHolderClass, position: Int) {
        val currentItem = datalist[position]
        holder.rvPrice.text =  currentItem.price.toString()
        holder.rvSeller.text = currentItem.sellerName
        holder.rvQty.text = currentItem.qty.toString()
        holder.rvVariation.text = currentItem.variation
        holder.rvProduct.text = currentItem.productName
    }

    class ViewHolderClass(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val rvPrice: TextView = itemView.findViewById(R.id.rv_price)
        val rvSeller: TextView = itemView.findViewById(R.id.rv_cart_seller_name)
        val rvQty: TextView = itemView.findViewById(R.id.rv_cart_qty)
        val rvVariation: TextView = itemView.findViewById(R.id.rv_variation)
        val rvProduct: TextView = itemView.findViewById(R.id.rv_cart_product)
    }
}