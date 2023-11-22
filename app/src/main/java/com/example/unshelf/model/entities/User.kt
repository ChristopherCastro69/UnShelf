package com.example.unshelf.model.entities

abstract class User {
    abstract val email: String
    abstract val password: String
    abstract val phoneNumber: Long
    abstract val fullName: String
    abstract val address: String
}
