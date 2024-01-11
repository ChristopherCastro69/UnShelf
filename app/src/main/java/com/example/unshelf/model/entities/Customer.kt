package com.example.unshelf.model.entities

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
class Customer(
    var customerID : String,
    override val email: String,
    override val password: String,
    override val phoneNumber: Long,
    override val fullName: String,
    override val address: String,
) : User(), Parcelable {
    constructor() : this("","","",0,"","")
}



