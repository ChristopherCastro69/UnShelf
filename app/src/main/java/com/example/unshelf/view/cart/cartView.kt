package com.example.unshelf.view.cart

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
import androidx.compose.material.icons.filled.ArrowBackIos
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CheckboxDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
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


class cartView : ComponentActivity() {
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
        setContent {
            CartLayout(cart)
        }

    }

}
@Composable
fun CartLayout(cart : MutableMap<String, MutableList<Product>>) {
    val isAllCheckout = remember {mutableStateOf(true)}
    val context = LocalContext.current
    val total = remember { mutableStateOf(calculateTotal(cart)) }
    val storeActive = (mutableMapOf <String, MutableState<Boolean>>())
    for((storeID, products) in cart) {
        storeActive.getOrPut(storeID) { mutableStateOf(true) }
    }

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
//                            if (activity != null)
//                                activity.onBackPressed()
                        }
                    ) {
                        Icon(
                            imageVector = Icons.Filled.ArrowBackIos,
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
                        for((storeID, isActive) in storeActive) {
                            isActive.value = isAllCheckout.value
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
//                val intent = Intent(context, CheckoutUI::class.java)
//                context.startActivity(intent)
                        /*for (prod in checkoutProductList) {
                            println(prod)
                        }*/
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
            for ((storeID, productList) in cart) {
                val isActive = storeActive.get(storeID)!!
                total.value = CartGroup(isActive, products = productList, total, isAllCheckout)
            }
        }
    }
}


@Composable
fun CartGroup(
    isActive : MutableState<Boolean>,
    products : MutableList<Product>,
    total:  MutableState<Double>,
    isAll : MutableState<Boolean>,
    ) :   Double{
    var storeChecked = remember { isActive }
    var inactiveAll = remember { mutableStateOf(false) }
    var flag = remember { mutableStateOf(false) }

    if(isAll.value) {
        flag.value = false
        storeChecked.value = true
        inactiveAll.value = true
    }

    if(flag.value && !storeChecked.value) {
        inactiveAll.value = true
        flag.value = false
        products.forEach { product ->
            product.active = true
        }
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
                    if(!storeChecked.value) {
                        isAll.value = false
                        inactiveAll.value = true
                    }
                    total.value += calculateTotal(storeChecked.value, products)
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
            products.forEach { product ->
                CartItem(p = product, total, storeChecked, inactiveAll, isAll)
            }
            if(storeChecked.value && inactiveAll.value) {
                inactiveAll.value = false
            }
            if(storeChecked.value) {
                for(product in products) {
                    if(!product.active) {
                        flag.value = true
                        storeChecked.value = false
                    } else {
                        flag.value = false
                    }
                }
            }

        }
    }
    Spacer(
        Modifier
            .fillMaxWidth()
            .background(MediumSpringBud)
            .size(3.dp)
    )

    return total.value
}


@Composable
fun CartItem(p: Product, total:  MutableState<Double>, isActive : MutableState<Boolean>, inactiveAll : MutableState<Boolean>, isAll : MutableState<Boolean>){
    var productChecked = remember { mutableStateOf(p.active) }
    var qty = remember { mutableStateOf(p.quantity) }

    if(!isActive.value) {
        productChecked.value = false
    } else if (inactiveAll.value) {
        productChecked.value = true
    }

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 5.dp)
    ) {
        Checkbox(
            modifier = Modifier
                .padding(end = 5.dp)
                .align(Alignment.CenterVertically)
                .size(25.dp),
            checked = productChecked.value,
            enabled = isActive.value,
            onCheckedChange = {
                productChecked.value = it
                if(isActive.value) p.active = productChecked.value
                if (!productChecked.value) {
                    isAll.value = false
                    total.value -= (p.sellingPrice * qty.value)
                } else if (productChecked.value && isActive.value) {
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
                            if (productChecked.value && qty.value > 1) {
                                qty.value = maxOf(qty.value - 1, 1)
                                p.quantity = qty.value
                                total.value -= p.sellingPrice
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
                            if (productChecked.value) {
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

fun calculateTotal(isActive: Boolean, productList: MutableList<Product>): Double {
    var total = 0.0

    for (product in productList) {
        if(product.active) {
            if(isActive) {
                total += (product.sellingPrice * product.quantity)
            } else {
                total -= (product.sellingPrice * product.quantity)
            }
        }
    }

    return total
}
