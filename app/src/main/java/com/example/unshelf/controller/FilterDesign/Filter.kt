package com.example.unshelf.controller.FilterDesign

import com.example.unshelf.model.entities.Product

interface Filter<T> {
    fun meetsCriteria(products :List<T>): List<T>

}