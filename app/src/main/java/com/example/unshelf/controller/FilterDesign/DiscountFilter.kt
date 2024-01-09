package com.example.unshelf.controller.FilterDesign

import com.example.unshelf.model.entities.Product

class DiscountFilter : Filter<Product> {
    override fun meetsCriteria(products: List<Product>): List<Product> {
        var filteredList : List<Product> = listOf()
        for(product in products) {
            if(product.discount!=0.0) {
                filteredList = filteredList + product
            }
        }
        return filteredList
    }
}