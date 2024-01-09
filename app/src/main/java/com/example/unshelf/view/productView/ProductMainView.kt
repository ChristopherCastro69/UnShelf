package com.example.unshelf.view.productView

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
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
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.DefaultShadowColor
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.content.ContextCompat
import coil.compose.rememberAsyncImagePainter
import com.example.unshelf.R
import com.example.unshelf.controller.Cart.CartController
import com.example.unshelf.model.entities.Product
import com.example.unshelf.ui.theme.DarkPalmLeaf
import com.example.unshelf.ui.theme.DeepMossGreen
import com.example.unshelf.ui.theme.MiddleGreenYellow
import com.example.unshelf.ui.theme.PalmLeaf
import com.example.unshelf.view.checkout.CheckoutUI
import com.example.unshelf.view.StartUI.initialMarketNav
import com.example.unshelf.view.cart.CartView
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class ProductMainView : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val user: Boolean = intent.getBooleanExtra("user",false)
        val product: Product? = intent.getParcelableExtra("product")

        setContent {
            // Your Compose UI goes here
            ProductMain(product, user)
        }
    }
}

@Preview
@Composable
fun ProductContent() {
    ProductMain(product = null, false)
}

@Composable
fun ProductMain(product : Product?, user : Boolean) {
    product as Product
    val qty = remember{ mutableStateOf(product.quantity) }
    Scaffold(
        modifier = Modifier.fillMaxSize(),
        bottomBar = {
            if(user) {
                Column() {
                    PMMenu(product)
                }
            } else {
                initialMarketNav()
            }
        }
    ) { innerPadding ->
        Column(modifier = Modifier.padding(innerPadding)) {
            Column(
                modifier = Modifier
                    .background(Color.White)
                    .fillMaxWidth()
                    .weight(1f)
                    .verticalScroll(rememberScrollState())
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(color = Color.Gray) // Optional: Set background color
                ) {
                    Image(
                        rememberAsyncImagePainter(product.thumbnail),
                        contentDescription = null,
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(370.dp), // Adjust the height as needed
                        contentScale = ContentScale.Crop
                    )
                }
                PMContent(product)
            }
        }
        PMNavigation(product, user)
    }
}

@Composable
fun PMMenu(product : Product?) {
    var isButtonClicked by remember { mutableStateOf(false) }

    val context = LocalContext.current
    println("pleasseeee re compose")
    LaunchedEffect(isButtonClicked) {
        println("should work right?")
        if (isButtonClicked) {
            // Coroutine is launched when isButtonClicked becomes true
            withContext(Dispatchers.IO) {
                println("Or did it?")
                // Call your suspend function here, e.g., updateCart
                updateCart(fetchUser()?.uid, product?.productID)
            }

            // Reset the flag after the coroutine is executed
            isButtonClicked = !isButtonClicked
        } else {
            println("were do you go")
        }
    }
    Column {
        product?.quantity = itemQuantity()
        Row(
            modifier = Modifier.run {
                padding(top = 10.dp)
                    .fillMaxWidth()
                    .shadow(
                        elevation = 50.dp,
                        shape = RectangleShape,
                        clip = false,
                        ambientColor = DefaultShadowColor,
                        spotColor = DefaultShadowColor,
                    )
            },
            horizontalArrangement = Arrangement.Center, // justify-content: center
        ) {
            Button(
                onClick = {
//                    println("It didnt run :(")
//                    isButtonClicked = !isButtonClicked
//                    println("Button Value ${isButtonClicked}")
                    if(product != null) {
                        try {
                            CartController.addToCart(product, "product")
                            Toast.makeText( context,"Added to basket!", Toast.LENGTH_SHORT).show()
                        } catch(e : Exception) {
                            Toast.makeText( context,"Failed to basket", Toast.LENGTH_SHORT).show()
                        }

                    }
                },
                colors = ButtonDefaults.buttonColors(MiddleGreenYellow),
                modifier = Modifier
                    .height(50.dp)
                    .padding(bottom = 10.dp, start = 20.dp)
                    .align(Alignment.CenterVertically)
                    .weight(1F)
            ) {
                Text(
                    text = "ADD TO BASKET",
                    color = DeepMossGreen
                )
            }
            Button(
                onClick = {
                    val intent = Intent(context, CheckoutUI::class.java)
                    context.startActivity(intent)
                },
                colors = ButtonDefaults.buttonColors(DarkPalmLeaf),
                modifier = Modifier
                    .height(50.dp)
                    .padding(bottom = 10.dp, start = 10.dp, end = 20.dp)
                    .align(Alignment.CenterVertically)
                    .weight(1F)

            ) {
                Text(
                    text = "BUY NOW",
                    color = Color.White
                )
            }
        }
    }

}

@Composable
fun PMNavigation(product : Product?, user: Boolean) {
    val context = LocalContext.current
    Row {
        val activity = LocalContext.current
        Image (
            painter = painterResource(id = R.drawable.ic_back_button),
            contentDescription = "Back",
            modifier = Modifier
                .padding(15.dp)
                .height(55.dp)
                .width(55.dp)
                .clickable {
//                    val intent = Intent(context, MainNavigationActivityBuyer::class.java)
//                    context.startActivity(intent)
                    (context as? Activity)?.finish()
                }
            ,
            contentScale = ContentScale.Crop
        )
        Spacer(
            modifier = Modifier.weight(1f) // Add some space between images
        )
        if(user) {
            Image (
                painter = painterResource(id = R.drawable.ic_cart),
                contentDescription = "Back",
                modifier = Modifier
                    .padding(15.dp)
                    .height(55.dp)
                    .width(55.dp)
                    .clickable {
                        val intent = Intent(context, CartView::class.java)
                        context.startActivity(intent)
                    },
                contentScale = ContentScale.Crop,

                )
        }

    }
}

@Composable
fun PMContent(product: Product?) {
    product as Product
    var storeName = ""
    Column {
        Row (
            modifier = Modifier
                .fillMaxWidth()
        ) {
            Column(
                modifier = Modifier
                    .padding(16.dp)
                    .weight(0.5f)
            ) {
                Row{
                    Text (
                        text = "${product.productName}",
                        color = DeepMossGreen,
                        fontSize = 25.sp
                    )
                    Spacer(modifier = Modifier.weight(1F))
                    PMHeart(Modifier.padding(end = 0.dp), false, onCheckedChange = {})
                }
                Row (
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.padding(top = 8.dp)
                ) {
                    Text (
                        text = "₱ ${String.format("%.2f", product.price)}",
                        color = DeepMossGreen,
                        fontSize = 25.sp,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.padding(end = 10.dp)
                    )
                    if(product.discount!=0.0) {
                        Box {
                            Image(
                                painterResource(id = R.drawable.pm_banner_ic),
                                contentDescription = "banner",
                                contentScale = ContentScale.FillBounds,
                                modifier = Modifier
                                    .width(90.dp)
                                    .height(25.dp)
                            )
                            Text(
                                text = "Save ₱ ${String.format("%.0f", product.discount)}",
                                color = Color.White,
                                fontSize = 13.sp,
                                modifier = Modifier.padding(start=15.dp,top=5.dp)
                            )
                        }
                    }
                }
                Row (
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 8.dp),
                    verticalAlignment = Alignment.CenterVertically
                ){
                    Image(
                        painter = painterResource(
                            id = R.drawable.ic_active_store),
                        contentDescription = "SellerIc",
                        contentScale = ContentScale.FillBounds,
                        modifier = Modifier
                            .padding(end = 5.dp)
                            .size(25.dp)
                    )
                    Text (
                        text = product.storeName,
                        fontSize = 16.sp,
                        color = PalmLeaf
                    )
                }
                Text (
                    text = "Description",
                    color = DeepMossGreen,
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(top = 20.dp)
                )
                Text (
                    text = product.description,
                    color = Color(ContextCompat.getColor(LocalContext.current, R.color.green02)),
                    fontSize = 16.sp,
                    modifier = Modifier.padding(vertical = 10.dp)
                )
                Row{
                    Text (
                        text = "Consume before: \n(mm/dd/yyyy)",
                        color = DeepMossGreen,
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier
                            .align(Alignment.CenterVertically)
                            .padding(top = 10.dp)
                    )
                    Spacer(modifier = Modifier.weight(1F))
                    Text (
                        text = product.expirationDate,
                        color = Color(ContextCompat.getColor(LocalContext.current, R.color.green02)),
                        fontSize = 16.sp,
                        modifier = Modifier
                            .align(Alignment.Top)
                            .padding(top = 10.dp)
                    )
                }
            }
        }
    }
}


@Composable
fun itemQuantity(
    qty: Int = 1
) : Int {
    var (qty, setQty)  = remember { mutableStateOf(qty) }
    Row (
        modifier = Modifier.padding(start = 20.dp, end = 20.dp, top = 10.dp)
    ) {
        Text (
            text = "Quantity",
            color = DeepMossGreen,
            fontSize = 18.sp,
            fontWeight = FontWeight.Bold,
            textAlign = TextAlign.Left,
            modifier = Modifier
                .padding(top = 8.dp)
                .align(Alignment.CenterVertically)
                .weight(1F)
        )
        var isZero = false
        if(qty==1) {
            isZero = true
        }
        Image(
            painter = painterResource(id = R.drawable.ic_minus_active),
            contentDescription = "Decrease Quantity",
            modifier = Modifier
                .padding(end = 10.dp)
                .size(30.dp)
                .clickable {
                    if (isZero)
                        setQty(qty)
                    else
                        setQty(qty - 1)
                }
        )
        Text(
            text = "${qty}",
            color = Color(ContextCompat.getColor(LocalContext.current, R.color.green02)),
            fontSize = 18.sp,
            modifier = Modifier.align(Alignment.CenterVertically)

        )
        Image(
            painter = painterResource(id = R.drawable.ic_plust_active),
            contentDescription = "Increase Quantity",
            modifier = Modifier
                .padding(start = 10.dp)
                .size(30.dp)
                .clickable {
                    setQty(qty + 1)
                }
        )
    }
    return qty
}



@Composable
fun PMHeart(
    modifier: Modifier = Modifier,
    checkedState: Boolean,
    onCheckedChange: () -> Unit
) {
    val (checked, setChecked) = remember { mutableStateOf(checkedState) }

    if (checked) {
        Image (
            painter = painterResource(id = R.drawable.selected_icon_heart),
            contentDescription = "Checkbox",
            modifier = modifier
                .clickable {
                    setChecked(!checked)
                    onCheckedChange()
                }

        )
    } else {
        Image (
            painter = painterResource(id = R.drawable.__icon__heart_),
            contentDescription = "Checkbox",
            modifier = modifier
                .clickable {
                    setChecked(!checked)
                }
        )
    }

}

