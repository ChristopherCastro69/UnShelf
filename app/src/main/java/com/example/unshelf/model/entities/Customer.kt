package com.example.unshelf.model.entities

class Customer ( // please add methods (example: getters or setters)
// (if user can modify something, add setter)
// (use getters for a summary of details, returning an object or string)
    override val email: String,
    override val password: String,
    override val phoneNumber: Long,
    override val fullName: String,
    override val address: String,
) : User