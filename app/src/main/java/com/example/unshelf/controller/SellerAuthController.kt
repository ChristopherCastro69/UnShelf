package com.example.unshelf.controller

import com.example.unshelf.model.authentication.CustomerAuthModel
import com.example.unshelf.model.authentication.SellerAuthModel
import com.example.unshelf.model.entities.Product

class SellerAuthController (
    private val sellerAuthModel: SellerAuthModel){
    fun createSellerAccount(
        email: String,
        password: String,
        phoneNumber: Long,
        fullName: String,
        address: String,
        storeName: String,
        productList: List<Product>,
        rating: Long,
        sellerID : String,
        adminVerified: String,
        callback: (Boolean, String?) -> Unit
    ) {
        sellerAuthModel.createSellerAccountFirebase(
            email,
            password,
            phoneNumber,
            fullName,
            address,
            storeName,
            productList,
            rating,
            sellerID,
            adminVerified,
            callback

        )
    }
}

