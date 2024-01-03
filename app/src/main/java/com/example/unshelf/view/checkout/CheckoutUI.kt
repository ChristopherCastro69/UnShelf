package com.example.unshelf.view.checkout
import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.Composable
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBackIos
import androidx.compose.material.icons.filled.ArrowForwardIos
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
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
import androidx.compose.ui.unit.*
import androidx.lifecycle.lifecycleScope
import coil.compose.rememberAsyncImagePainter
import com.example.unshelf.R
import com.example.unshelf.controller.Cart.CartController
import com.example.unshelf.controller.Checkout.CheckoutSessionController
import com.example.unshelf.model.entities.Product
import com.example.unshelf.ui.theme.DeepMossGreen
import com.example.unshelf.ui.theme.MediumSpringBud
import com.example.unshelf.ui.theme.MiddleGreenYellow
import com.example.unshelf.ui.theme.PalmLeaf
import kotlinx.coroutines.launch

class CheckoutUI: ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        var checkouturl = ""
        var checkoutID = ""
        var cart = CartController.storeMapped
        var storeMapped = CartController.storeActiveState
        lifecycleScope.launch {
            val checkoutSesh = CheckoutSessionController().createCheckoutSession(cart)
            if(checkoutSesh!=null) {
                checkouturl = checkoutSesh.data.attributes.checkout_url
                Log.d("CheckoutSesh", "$checkouturl")
                checkoutID = checkoutSesh.data.id
                Log.d("CheckoutID", "$checkoutID")
            }
            /*val data = DataRequest()
            data.makeGetRequest()*/
        }


        setContent{
            CheckoutPage(cart, storeMapped, onClick = {
                redirect(checkouturl, checkoutID)
            })
        }
    }
    fun redirect(url:String, checkoutID: String) {
        val intent = Intent(this, CheckoutRedirect::class.java)
        intent.putExtra("checkoutUrl", url)
        intent.putExtra("checkoutID", checkoutID)
        startActivity(intent)
    }
}

//@Preview
//@Composable
//fun PreviewCheckout() {
//    CheckoutPage(null,onClick = {println("hello")})
//}


@Composable
fun CheckoutPage(
    cart :  MutableMap<String, MutableList<Product>>,
    storeMapped :  MutableMap<String, MutableState<Boolean>>,
    onClick: () -> Unit) {
    val context = LocalContext.current
    //var transactionFee by remember{ mutableStateOf(0.0) }
    var totalAmount by remember{ mutableStateOf(0.0) }
    //var totalWithTransactionFee by remember{ mutableStateOf(0.0) }
    var subTotal = 0.0
    for((_, products) in cart) {
        products.forEach{
            if(it.active)
                totalAmount += (it.sellingPrice * it.quantity)
        }
    }
    Scaffold(
        topBar = {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(height = 48.dp)
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
                            imageVector = Icons.Filled.ArrowBackIos,
                            contentDescription = "Back button",
                            tint = Color.White,
                            modifier = Modifier
                                .fillMaxSize()
                                .padding(start=10.dp)
                        )
                    }
                    Text(
                        text = "Checkout",
                        color = Color.White,
                        style = TextStyle(
                            fontSize = 20.sp,
                            fontWeight = FontWeight.Medium
                        ),
                        modifier = Modifier
                            .fillMaxHeight()
                            .wrapContentHeight(align = Alignment.CenterVertically)
                    )
                }
            }
        },
        bottomBar = {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(height = 65.dp)
                    .padding(5.dp)
                    .background(Color.White)
            ) {
                Row {
                    Text(
                        text = "Total\n₱ ${String.format("%.2f", totalAmount)}",
                        color = DeepMossGreen,
                        style = TextStyle(
                            fontSize = 20.sp,
                            fontWeight = FontWeight.Black,
                            textAlign = TextAlign.Right,
                        ),
                        modifier = Modifier
                            .fillMaxHeight()
                            .weight(1F)
                            .wrapContentHeight(align = Alignment.CenterVertically)
                    )
                    Button(onClick = onClick,
                        shape = RoundedCornerShape(13.dp),
                        colors = ButtonDefaults.buttonColors(containerColor = PalmLeaf),
                        modifier = Modifier
                            .width(130.dp)
                            .padding(start = 8.dp)
                    ){
                        Text(
                            text = "Confirm\nPurchase",
                            color = Color.White,
                            style = TextStyle(
                                fontSize = 16.sp,
                                fontWeight = FontWeight.Black,
                                textAlign = TextAlign.Center,
                            ),
                            modifier = Modifier
                                .fillMaxHeight()
                                .wrapContentHeight(align = Alignment.CenterVertically)
                        )

                    }
                }
            }
        }
    ) {innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding),
            verticalArrangement = Arrangement.spacedBy(8.dp),
        ) {
            Column {
                Divider(
                    color = MediumSpringBud,
                    modifier = Modifier
                        .height(5.dp)
                        .fillMaxWidth()
                )

                LazyColumn {
                    item {
                        for((storeID, products) in cart) {
                            if(storeMapped[storeID]!!.value) {
                                val amount = storeGrouped(products)
                                subTotal += amount
//                                if(amount < 110.0) {
//                                    transactionFee += (amount * 0.5)
//                                } else {
//                                    transactionFee += (amount * 0.03)
//                                }
                            }
                        }
                    }
                    item {
                        Column(modifier = Modifier
                            .fillMaxWidth()
                            .background(Color.White)
                            .padding(10.dp)) {
//                            Row() {
//                                Icon(
//                                    painter = painterResource(id = R.drawable.wallet),
//                                    contentDescription = "wallet icon",
//                                    tint = DeepMossGreen,
//                                    modifier = Modifier.size(25.dp)
//                                        .align(Alignment.CenterVertically)
//                                )
//                                Text(
//                                    text = "Payment Method",
//                                    color = DeepMossGreen,
//                                    style = TextStyle(
//                                        fontSize = 15.sp,
//                                        fontWeight = FontWeight.Bold
//                                    ),
//                                    modifier = Modifier
//                                        .align(Alignment.CenterVertically)
//                                        .padding(start = 5.dp)
//                                )
//                                Row(Modifier.clickable {
//
//                                }) {
//                                    Text(
//                                        text = "Select a\npayment method",
//                                        color = DeepMossGreen,
//                                        style = TextStyle(
//                                            fontSize = 15.sp,
//                                            textAlign = TextAlign.Right,
//                                        ),
//                                        modifier = Modifier
//                                            .align(Alignment.CenterVertically)
//                                            .padding(start = 5.dp)
//                                            .weight(1F)
//                                            .clickable {
//
//                                            }
//                                    )
//                                    Icon(
//                                        imageVector = Icons.Filled.ArrowForwardIos,
//                                        contentDescription = "To Pickup Details",
//                                        tint = DeepMossGreen,
//                                        modifier = Modifier
//                                            .align(Alignment.CenterVertically)
//                                            .size(20.dp)
//                                            .clickable {
//
//                                            }
//                                    )
//                                }
//                            }
                            Row(
                                modifier = Modifier
                                    .padding(vertical = 8.dp)
                            ) {
                                Icon(
                                    painter = painterResource(id = R.drawable.ic_receipt),
                                    contentDescription = "store icon",
                                    tint = DeepMossGreen,
                                    modifier = Modifier.size(30.dp)
                                )
                                Text(
                                    text = "Payment Details",
                                    color = DeepMossGreen,
                                    style = TextStyle(
                                        fontSize = 17.sp,
                                        fontWeight = FontWeight.Bold
                                    ),
                                    modifier = Modifier
                                        .align(Alignment.CenterVertically)
                                        .padding(start = 5.dp)
                                )
                            }
                            Row(
                                modifier = Modifier
                                    .padding(vertical = 8.dp)
                            ) {
                                Text(
                                    text = "Subtotal",
                                    color = DeepMossGreen,
                                    style = TextStyle(
                                        fontSize = 17.sp
                                    ),
                                    modifier = Modifier
                                        .align(Alignment.CenterVertically)
                                        .padding(start = 5.dp)
                                        .weight(1F)
                                )
                                Text(
                                    text = "₱ ${String.format("%.2f", subTotal)}",
                                    color = DeepMossGreen,
                                    style = TextStyle(
                                        fontSize = 17.sp,
                                        textAlign = TextAlign.Right,
                                    ),
                                    modifier = Modifier
                                        .align(Alignment.CenterVertically)
                                        .padding(start = 5.dp)
                                        .weight(1F)
                                )
                            }
//                            Row(
//                                modifier = Modifier
//                                    .padding(vertical = 8.dp)
//                            ) {
//                                Text(
//                                    text = "Transaction Fees",
//                                    color = DeepMossGreen,
//                                    style = TextStyle(
//                                        fontSize = 15.sp
//                                    ),
//                                    modifier = Modifier
//                                        .align(Alignment.CenterVertically)
//                                        .padding(start = 5.dp)
//                                        .weight(1F)
//                                )
//                                Text(
//                                    text = "₱ 0.00",
//                                    color = DeepMossGreen,
//                                    style = TextStyle(
//                                        fontSize = 15.sp,
//                                        textAlign = TextAlign.Right,
//                                    ),
//                                    modifier = Modifier
//                                        .align(Alignment.CenterVertically)
//                                        .padding(start = 5.dp)
//                                        .weight(1F)
//                                )
//                            }
                            Row(
                                modifier = Modifier
                                    .padding(vertical = 8.dp)
                            ) {
                                Text(
                                    text = "Total Payment",
                                    color = DeepMossGreen,
                                    style = TextStyle(
                                        fontSize = 17.sp
                                    ),
                                    modifier = Modifier
                                        .align(Alignment.CenterVertically)
                                        .padding(start = 5.dp)
                                        .weight(1F)
                                )
                                Text(
                                    text = "₱ ${String.format("%.2f", subTotal)}",
                                    color = DeepMossGreen,
                                    style = TextStyle(
                                        fontSize = 17.sp,
                                        textAlign = TextAlign.Right,
                                        fontWeight = FontWeight.Bold
                                    ),
                                    modifier = Modifier
                                        .align(Alignment.CenterVertically)
                                        .padding(start = 5.dp)
                                        .weight(1F)
                                )
                                totalAmount = subTotal
                            }
                        }

                    }
                }

            }
        }



    }
}
@Composable
fun storeGrouped(
    products : MutableList<Product>
) : Double {
    val context = LocalContext.current
    var subtotal = 0.0
    Column(modifier = Modifier.background(Color(0xffC8DD96))) {
        Box(modifier = Modifier
            .fillMaxWidth()
            .background(Color.White)
            .padding(10.dp)
        ) {
            Column {
                Row {
                    Image(
                        painter = painterResource(id = R.drawable.ic_market_stall),
                        contentDescription = "store icon",
                        modifier = Modifier.size(25.dp)
                    )
                    Text(
                        text = "${products.get(0).storeName}",
                        color = DeepMossGreen,
                        style = TextStyle(
                            fontSize = 17.sp,
                        ),
                        modifier = Modifier
                            .align(Alignment.CenterVertically)
                            .padding(start = 4.dp)
                    )
                }
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = "For Pickup",
                    color = PalmLeaf,
                    style = TextStyle(
                        fontSize = 15.sp,
                    ),
                    modifier = Modifier
                        .border(2.dp, color = MediumSpringBud, shape = RoundedCornerShape(4.dp))
                        .padding(horizontal = 8.dp, vertical = 4.dp)
                )
                Row(
                    modifier = Modifier
                        .padding(vertical = 8.dp)
                        .clickable {
                            val intent = Intent(context, PickUp::class.java)
                            context.startActivity(intent)
                        }
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.ic_pickup),
                        contentDescription = "pickup icon",
                        modifier = Modifier
                            .size(25.dp)
                            .align(Alignment.CenterVertically)
                    )
                    Column (
                        modifier = Modifier
                            .weight(1F)
                            .padding(start = 5.dp)
                    ){
                        Text(
                            text = "Pickup Details",
                            color = DeepMossGreen,
                            style = TextStyle(
                                fontSize = 15.sp,
                                fontWeight = FontWeight.Bold,
                            ),
                            modifier = Modifier
                                .padding(start = 4.dp)
                        )
                        Text(
                            text = "Location: \t2F CityMall, N. Bacalso St.",
                            color = DeepMossGreen,
                            style = TextStyle(
                                fontSize = 15.sp
                            ),
                            modifier = Modifier
                                .padding(start = 4.dp)
                        )
                        Text(
                            text = "Date:\t January 14, 2024\nTime:\t 10:00 a.m.",
                            color = DeepMossGreen,
                            style = TextStyle(
                                fontSize = 15.sp
                            ),
                            modifier = Modifier
                                .padding(start = 4.dp)
                        )
                    }
                    Icon(
                        imageVector = Icons.Filled.ArrowForwardIos,
                        contentDescription = "To Pickup Details",
                        tint = DeepMossGreen,
                        modifier = Modifier
                            .size(20.dp)
                            .align(Alignment.CenterVertically)
                            .clickable {

                            }
                    )
                }
                Divider(
                    modifier = Modifier
                        .background(DeepMossGreen)
                        .height(2.dp)
                        .padding(top = 8.dp)
                )
                Spacer(modifier = Modifier.height(5.dp))
                Column{
                    products.forEach{product ->
                        if(product.active) {
                            productDetails(product)
                            subtotal += (product.sellingPrice * product.quantity)
                        }
                    }
                }
            }

        }
        Divider(
            Modifier.size(1.dp)
                .background(color = MiddleGreenYellow)
        )
        Text(
            text = "Subtotal: ₱ ${String.format("%.2f", subtotal)}",
            color = DeepMossGreen,
            style = TextStyle(
                fontSize = 15.sp,
                fontWeight = FontWeight.Bold
            ),
            modifier = Modifier
                .padding(top = 5.dp, bottom = 5.dp, end = 10.dp)
                .background(Color(0xffC8DD96))
                .align(Alignment.End)
        )
        Divider(
            Modifier.size(1.dp)
                .background(color = MiddleGreenYellow)
        )
    }
    return subtotal
}

@Composable
fun productDetails(
    p : Product
) {
    Row (
        modifier = Modifier
            .padding(5.dp)
    ){
        Image (
            painter = rememberAsyncImagePainter(p.thumbnail),
            contentDescription = "product img",
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .size(73.dp)
                .clip(RoundedCornerShape(10.dp))
                .border(2.dp, color = MediumSpringBud, shape = RoundedCornerShape(10.dp))
        )
        Column (
            modifier = Modifier
                .weight(1F)
                .padding(start = 5.dp)
                .align(Alignment.CenterVertically)
                .weight(1F)
        ){
            Text(
                text = p.productName,
                color = DeepMossGreen,
                style = TextStyle(
                    fontSize = 15.sp,
                    fontWeight = FontWeight.Medium,
                ),
                modifier = Modifier
                    .padding(start = 4.dp)
            )

            Text(
                text = "Consume before: ${p.expirationDate}",
                color = PalmLeaf,
                style = TextStyle(
                    fontSize = 15.sp
                ),
                modifier = Modifier
                    .padding(start = 4.dp)
            )
            Text(
                text = "",
                color = PalmLeaf,
                style = TextStyle(
                    fontSize = 15.sp
                ),
                modifier = Modifier
                    .padding(start = 4.dp)
            )
            Text(
                text = "₱ ${p.sellingPrice}",
                color = DeepMossGreen,
                style = TextStyle(
                    fontSize = 15.sp,
                    fontWeight = FontWeight.Bold,
                ),
                modifier = Modifier
                    .padding(start = 4.dp)
            )
        }
        Text(
            text = "x${p.quantity}",
            color = DeepMossGreen,
            style = TextStyle(
                fontSize = 15.sp
            ),
            modifier = Modifier
                .padding(start = 4.dp)
        )
    }
}


