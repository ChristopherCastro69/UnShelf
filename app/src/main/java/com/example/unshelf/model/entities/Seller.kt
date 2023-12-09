
package com.example.unshelf.model.entities

class Seller(
    val sellerID : String,
    override val email: String,
    override val password: String,
    override val phoneNumber: Long,
    override val fullName: String,
    override val address: String,
    val storeName: String,
    val adminVerified: String
) : User()
