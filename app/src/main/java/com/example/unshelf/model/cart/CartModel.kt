package com.example.unshelf.model.cart

class CartModel (
    var customerID : String,
    var itemList : List<CartItemModel>,
) {
    constructor() : this("", emptyList())
}

class CartItemModel(
    var productID: String?,
    var quantity: Int?,
) {
    constructor() : this("",0,)
}
