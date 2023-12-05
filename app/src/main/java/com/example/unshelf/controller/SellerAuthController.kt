package com.example.unshelf.controller

import com.example.unshelf.model.authentication.CustomerAuthModel
import com.example.unshelf.model.authentication.SellerAuthModel
import com.example.unshelf.model.entities.Product

class SellerAuthController ( // Please modify class diagram and add this class as a controller
    private val sellerAuthModel: SellerAuthModel){
    fun createSellerAccount(
        email: String,
        password: String,
        phoneNumber: Long,
        fullName: String,
        address: String,
        storeName: String,

        rating: Long,
        followers: Int,
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
            rating,
            followers,
            adminVerified,
            callback

        )
    }
}

