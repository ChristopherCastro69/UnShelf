package com.example.unshelf.model.entities

class Customer(
    var customerID : String,
    override val email: String,
    override val password: String,
    override val phoneNumber: Long,
    override val fullName: String,
    override val address: String,
) : User()


