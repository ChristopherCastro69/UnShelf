package com.example.unshelf.controller.FilterDesign

import android.util.Log
import com.example.unshelf.model.entities.Product

class CategoryFilter : Filter<Product> {
    var categoryList: List<String> = mutableListOf("")
    override fun meetsCriteria(products: List<Product>): List<Product> {
        var filteredList : List<Product> = listOf()
        for(product in products) {
            if(product.categories.containsAll(categoryList)) {
                filteredList = filteredList + product
            }
        }
        return filteredList
    }

}