package com.example.unshelf.controller.FilterDesign

import com.example.unshelf.model.entities.Product

interface Filter {
    fun meetsCriteria(products :List<Product>): List<Product>

}