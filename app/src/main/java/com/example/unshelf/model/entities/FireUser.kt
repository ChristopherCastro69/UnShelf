package com.example.unshelf.model.entities

class FireUser {
    private var name: String? = null
    private var email: String? = null

    constructor(name: String?, email: String?) {
        this.name = name
        this.email = email
    }

    fun getName(): String? {
        return name
    }

    fun getEmail(): String? {
        return email
    }
}