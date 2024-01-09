package com.example.unshelf.view.cart

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import androidx.compose.material3.Checkbox
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBackIos
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CheckboxDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.content.ContextCompat
import coil.compose.rememberAsyncImagePainter
import com.example.unshelf.R
import com.example.unshelf.controller.Cart.CartController
import com.example.unshelf.model.entities.Product
import com.example.unshelf.ui.theme.DarkPalmLeaf
import com.example.unshelf.ui.theme.MediumSpringBud
import com.example.unshelf.ui.theme.PalmLeaf
import com.example.unshelf.view.checkout.CheckoutUI


class CartView : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        var cart = CartController.storeMapped

        if(cart.isEmpty()) {
            cart = CartController.getCart()
        }
        for ((_, productList) in cart) {
            for (product in productList) {
                product.active = true
            }
        }
        CartController.setStoreState()
        var storeStates = CartController.storeActiveState
        setContent {
            CartLayout(cart, storeStates)
        }

    }

}
@Composable
fun CartLayout(
    cart : MutableMap<String, MutableList<Product>>,
    storeActive : MutableMap<String, MutableState<Boolean>>
)
    {
    var isAllCheckout = remember {mutableStateOf(true)}
    var isProductSelected = remember {mutableStateOf(true)}
    val context = LocalContext.current
    var total = remember { mutableStateOf(calculateTotal(cart)) }
    val stores = storeActive.keys
    val activeCtr = remember { CartController.cartList.itemList.size }

    Scaffold(
        topBar = {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(height = 51.dp)
                    .background(color = PalmLeaf)
            ) {
                Row {
                    IconButton(
                        modifier = Modifier
                            .width(35.dp),
                        onClick = {
                            (context as? Activity)?.finish()
                        }
                    ) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBackIos,
                            contentDescription = "Back button",
                            tint = Color.White,
                            modifier = Modifier
                                .fillMaxSize()
                                .padding(start = 10.dp)
                        )
                    }
                    Text(
                        text = "Basket",
                        color = Color.White,
                        style = TextStyle(
                            fontSize = 20.sp,
                            fontWeight = FontWeight.Medium
                        ),
                        modifier = Modifier
                            .align(Alignment.CenterVertically)
                    )
                }
                Spacer(
                    Modifier
                        .fillMaxWidth()
                        .background(MediumSpringBud)
                        .size(3.dp))
            }
        },
        bottomBar = {
            Row(
                modifier = Modifier
                    .background(Color.White)
                    .padding(horizontal = 10.dp, vertical = 15.dp)
            ) {
                Checkbox(
                    modifier = Modifier
                        .padding(horizontal = 5.dp)
                        .align(Alignment.CenterVertically)
                        .size(22.dp),
                    checked = isAllCheckout.value,
                    onCheckedChange = {
                        isAllCheckout.value = it
                        isProductSelected = isAllCheckout
                        for((storeID, products) in cart) {
                            val isActive = storeActive[storeID]!!
                            isActive.value = isAllCheckout.value
                            for (product in products) {
                                product.active = isAllCheckout.value
                            }
                        }

                        if(isAllCheckout.value) {
                            total.value = calculateTotal(cart)
                        } else {
                            total.value = 0.0
                        }
                    },
                    colors = CheckboxDefaults.colors(
                        checkedColor = PalmLeaf,
                        uncheckedColor = PalmLeaf
                    )
                )

                Text (
                    text = "All",
                    fontSize = 18.sp,
                    color = Color(ContextCompat.getColor(LocalContext.current, R.color.green07)),
                    modifier = Modifier
                        .align(Alignment.CenterVertically)

                )
                Column (
                    modifier = Modifier
                        .weight(1F)
                        .padding(),
                    horizontalAlignment = Alignment.End
                ) {

                    Text (
                        text = "Total",
                        fontSize = 16.sp,
                        textAlign = TextAlign.Right,
                        color = PalmLeaf,
                    )
                    Text (
                        text = "₱ ${String.format("%.2f", total.value)}",
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color(ContextCompat.getColor(LocalContext.current, R.color.green07)),
                        overflow = TextOverflow.Ellipsis
                    )
                }
                Spacer(modifier = Modifier.padding(end = 10.dp))
                Button(
                    onClick = {
                        val intent = Intent(context, CheckoutUI::class.java)
                        intent.putExtra("from", "basket")
                        context.startActivity(intent)
                    },
                    colors = ButtonDefaults.buttonColors(DarkPalmLeaf),
                    modifier = Modifier
                        .height(45.dp),
                    shape = RoundedCornerShape(10.dp)
                ) {
                    Text(
                        text = "CHECKOUT",
                        fontSize = 16.sp
                    )
                }
            }
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .verticalScroll(rememberScrollState()),
            verticalArrangement = Arrangement.spacedBy(3.dp)
        ) {
            Spacer(
                Modifier
                    .fillMaxWidth()
                    .size(3.dp)
            )
//            for((storeID, products) in cart) {
//                products.forEach {
//                    println("Store1: " + storeActive[storeID]!!.value + " Product ${it.productName} -> " + it.active)
//                }
//            }
            var flag = 0
            for ((storeID, products) in cart) {
                val isActive = storeActive[storeID]!!

                CartGroup(isActive, products, total, isProductSelected)
                for (product in products) {
                    if (product.active) {
                        flag++
                    }
                }
            }
            println("isProductsSelected: ${isProductSelected.value}")

            for((storeID, products) in cart) {
                products.forEach {
                    println("Flag: ${flag} Store: " + storeActive[storeID]!!.value + " Product ${it.productName} -> " + it.active)
                }
            }

            isAllCheckout.value = flag == activeCtr
        }
    }
}


@Composable
fun CartGroup(
    isActive : MutableState<Boolean>,
    productList: MutableList<Product>,
    total:  MutableState<Double>,
    isProductSelected : MutableState<Boolean>
) {
    var storeChecked = isActive
    var activeCtr = 0
    var products = productList
    val allCtr = remember { productList.size }

    for(product in products) {
        if(product.active) {
            activeCtr++
        }
    }
//    println("Active: " + activeCtr)
    if(activeCtr > 0) {
        storeChecked.value = true
    } else {
        storeChecked.value = false
    }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(10.dp)
    ) {
        Row() {
            Checkbox(
                modifier = Modifier
                    .padding(end = 5.dp)
                    .align(Alignment.CenterVertically)
                    .size(25.dp),
                checked = storeChecked.value,
                onCheckedChange = {
                    storeChecked.value = it
                    if(storeChecked.value && activeCtr==0) {
                        products.forEach{
                            it.active = true
                        }
                    }
                    total.value += calculateTotal(storeChecked, products)
                    if(!storeChecked.value) {
                        for(product in products) {
                            if(product.active) {
                                product.active = false
                            }
                        }
                    }
                },
                colors = CheckboxDefaults.colors(
                    checkedColor = PalmLeaf,
                    uncheckedColor = PalmLeaf
                )
            )

            Image(
                painter = painterResource(id = R.drawable.ic_market_stall),
                contentDescription = "store icon",
                modifier = Modifier.size(25.dp)
            )
            Text(
                text = products.get(0).storeName,
                color = Color(ContextCompat.getColor(LocalContext.current, R.color.green02)),
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier
                    .padding(start = 6.dp)
                    .align(Alignment.CenterVertically)
            )
        }
        Column {
            products.forEach { p ->
                //println(p.productName + " in store: " + p.active)
                CartItem(p, total, isProductSelected)
                //var productChecked by remember { mutableStateOf(p.active) }
            }
            activeCtr = 0
            for(product in products) {
                if(product.active) {
                    activeCtr++
                }
            }
            if(activeCtr > 0) {
                storeChecked.value = true
            } else {
                storeChecked.value = false
            }
            //println("Post-Active: " + activeCtr)
        }
    }
    Spacer(
        Modifier
            .fillMaxWidth()
            .background(MediumSpringBud)
            .size(3.dp)
    )
}

@Composable
fun CartItem(
    p: Product,
    total: MutableState<Double>,
    isProductSelected: MutableState<Boolean>
    ) : Boolean {
    var productChecked by remember { mutableStateOf(p.active) }
    productChecked = p.active


    val qty = remember { mutableStateOf(p.quantity)}

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 5.dp)
    ) {
        //println("productChecked: " + productChecked)
        Checkbox(
            modifier = Modifier
                .padding(end = 5.dp)
                .align(Alignment.CenterVertically)
                .size(25.dp),
            checked = p.active,
            onCheckedChange = {
                productChecked = it
                p.active = productChecked
                //println("Product: " + p.active)
                if (!productChecked) {
                    isProductSelected.value = false
                    total.value -= (p.sellingPrice * qty.value)
                } else {
                    total.value += (p.sellingPrice * qty.value)
                }
            },
            colors = CheckboxDefaults.colors(
                checkedColor = PalmLeaf,
                uncheckedColor = PalmLeaf
            )
        )

        Image(
            painter = rememberAsyncImagePainter(p.thumbnail),
            contentDescription = "product thumbnail",
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .size(120.dp)
                .clip(RoundedCornerShape(10.dp))
                .border(2.dp, color = MediumSpringBud, shape = RoundedCornerShape(10.dp))
        )
        Column(
            modifier = Modifier.padding(start = 8.dp)
        ) {
            Text(
                text = p.productName,
                color = Color(ContextCompat.getColor(LocalContext.current, R.color.green02)),
                fontSize = 15.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier
                    .align(Alignment.Start)
            )
            Row(modifier = Modifier.padding(top = 5.dp)) {
                Text(
                    text = "Quantity",
                    color = Color(ContextCompat.getColor(LocalContext.current, R.color.green02)),
                    fontSize = 15.sp,
                    modifier = Modifier
                        .weight(1F)
                        .align(Alignment.CenterVertically)
                )
                Image(
                    painter = painterResource(id = R.drawable.ic_minus_active),
                    contentDescription = "Decrease Quantity",
                    modifier = Modifier
                        .size(30.dp)
                        .clickable {
                            if (qty.value > 1) {
                                qty.value = maxOf(qty.value - 1, 1)
                                p.quantity = qty.value
                                if(productChecked) {
                                    total.value -= p.sellingPrice
                                }
                            }
                            CartController.addToCart(p, "cart")
                        }
                )
                Text(
                    text = "${qty.value}",
                    color = Color(ContextCompat.getColor(LocalContext.current, R.color.green02)),
                    fontSize = 16.sp,
                    modifier = Modifier
                        .padding(horizontal = 6.dp)
                        .align(Alignment.CenterVertically)
                )
                Image(
                    painter = painterResource(id = R.drawable.ic_plust_active),
                    contentDescription = "Increase Quantity",
                    modifier = Modifier
                        .size(30.dp)
                        .clickable {
                            qty.value += 1
                            p.quantity = qty.value
                            if (productChecked) {
                                total.value += p.sellingPrice
                            }
                            CartController.addToCart(p, "cart")
                        }
                )
            }
            Spacer(
                Modifier
                    .fillMaxWidth()
                    .background(MediumSpringBud)
                    .size(2.dp)
            )
            Text(
                text = "₱ ${String.format("%.2f", p.sellingPrice * qty.value)}",
                color = Color(ContextCompat.getColor(LocalContext.current, R.color.green02)),
                fontSize = 17.sp,
                modifier = Modifier
                    .padding(6.dp)
                    .align(Alignment.End)
            )
        }
    }
    return productChecked
}


fun calculateTotal(cart: MutableMap<String, MutableList<Product>>): Double {
    var total = 0.0
    for ((_, productList) in cart) {
        for (product in productList) {
            if (product.active) {
                total += (product.sellingPrice * product.quantity)
            }
        }
    }
    return total
}

fun calculateTotal(isActive: MutableState<Boolean>, productList: MutableList<Product>): Double {
    var total = 0.0

    for (product in productList) {
        if(product.active) {
            if(isActive.value) {
                total += (product.sellingPrice * product.quantity)
            } else {
                total -= (product.sellingPrice * product.quantity)
            }
        }
    }

    return total
}
