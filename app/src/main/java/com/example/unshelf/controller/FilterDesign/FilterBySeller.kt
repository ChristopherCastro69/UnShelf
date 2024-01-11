package com.example.unshelf.controller.FilterDesign

import com.example.unshelf.model.checkout.LineItem
import com.example.unshelf.model.entities.Product

class FilterBySeller : Filter<LineItem> {
    var sellerID = ""
    override fun meetsCriteria(products: List<LineItem>): List<LineItem> {
        var filteredList : List<LineItem> = listOf()
        for(product in products) {
            val sID = product.sellerID!!.substring(10..37)
            if(sID.equals(sellerID)) {
                filteredList = filteredList + product
            }
        }
        return filteredList
    }

    fun meetsCriteria(sellerID : String, products: List<LineItem>): List<LineItem> {
        this.sellerID = sellerID
        return meetsCriteria(products)
    }
}