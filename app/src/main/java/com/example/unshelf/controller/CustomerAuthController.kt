package com.example.unshelf.controller

import com.example.unshelf.model.authentication.CustomerAuthModel
import com.example.unshelf.view.authentication.CustomerRegister

class CustomerAuthController(
    private val customerAuthModel: CustomerAuthModel) {

    fun createCustomerAccount(
        email: String,
        password: String,
        phoneNumber: Long,
        fullName: String,
        address: String,
        callback: (Boolean, String?) -> Unit
    ) {
        customerAuthModel.createCustomerAccountFirebase(
            email,
            password,
            phoneNumber,
            fullName,
            address,
            callback)
    }
}
