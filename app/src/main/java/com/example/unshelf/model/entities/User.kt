package com.example.unshelf.model.entities

interface User {
    val email: String
    val password: String
    val phoneNumber: Long
    val fullName: String
    val address: String
}