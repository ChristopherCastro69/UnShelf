package com.example.unshelf.controller.DataFetch

import androidx.lifecycle.ViewModel
import com.example.unshelf.model.entities.Product

class DataFetchController : ViewModel() {

    private val repository = DataFetchRepository

    val products = repository.products
    val isLoading = repository.isLoading

    init {
        fetchData()
    }

    private fun fetchData() {
        repository.fetchData()
    }

}
