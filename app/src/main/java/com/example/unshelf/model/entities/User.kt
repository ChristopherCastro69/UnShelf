package com.example.unshelf.model.entities

interface User { // please add methods (example: getters or setters)
    // (if user can modify something, add setter)
// (use getters for a summary of details, returning an object or string)
    val email: String
    val password: String
    val phoneNumber: Long
    val fullName: String
    val address: String
}