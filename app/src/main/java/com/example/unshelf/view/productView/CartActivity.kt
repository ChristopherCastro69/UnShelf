package com.example.unshelf.view.productView

import android.content.Intent
import android.os.Bundle
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
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier // Correct import
import androidx.compose.ui.draw.paint
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.TileMode
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.content.ContextCompat
import com.example.unshelf.R
import com.example.unshelf.model.entities.Product
import com.example.unshelf.ui.theme.DarkPalmLeaf
import com.example.unshelf.view.BuyerBottomNav.ui.MainNavigationActivityBuyer
import com.example.unshelf.view.SellerBottomNav.screens.listings.product
import com.example.unshelf.view.checkout.CheckoutUI

class CartActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {

            // Your Compose UI goes here
            MyComposable()
        }
    }
}
val storesInfo : Map<String, List<Product>> = getStores()
class StoreCheck (
    StoreID : String,
    isActive : MutableState<Boolean>
)

@Preview
@Composable
fun MyComposable() {
    Surface(
        modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.colorScheme.background
    ) {
        // Create a Box with fillMaxSize width and fixed height
        Column (
            modifier = Modifier
                .fillMaxSize()
                .background(color = Color.Gray) // Optional: Set background color
        ) {
            Menu(
                modifier = Modifier
                    .fillMaxWidth()
            )
            Column(
                modifier = Modifier
                    .background(Color.LightGray)
                    .weight(1f)
                    .fillMaxWidth()
                    .verticalScroll(rememberScrollState())
            ) {

                storesInfo.forEach { (storeID, products) ->
                    //Query the store from the database
                    CartItem(Modifier, storeID, products)
                }
            }

            CartCheckOut(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(90.dp)
                    .paint(
                        // Replace with your image id
                        painterResource(id = R.drawable.sv_cart_dholder),
                        contentScale = ContentScale.FillBounds
                    ),
            )
        }
    }
}


@Composable
fun Menu(modifier: Modifier = Modifier) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(60.dp)
            .background(
                color = Color(
                    ContextCompat.getColor(
                        LocalContext.current,
                        R.color.green02
                    )
                )
            )
            .padding(8.dp)
    ) {
        // Back button
        val context = LocalContext.current
        Image(
            painter = painterResource(id = R.drawable.ic_backbtn),
            contentDescription = "Back",
            contentScale = ContentScale.Fit,
            modifier = Modifier
                .padding(start = 16.dp, end = 8.dp)
                .align(Alignment.CenterVertically)
                .clickable {
                    val intent = Intent(context, MainNavigationActivityBuyer::class.java)
                    context.startActivity(intent)
                }
        )

        // Text
        Text(
            text = "Basket",
            fontSize = 30.sp,
            color = Color.White,
            fontWeight = FontWeight.Bold,
            modifier = Modifier
                .weight(1f) // Take remaining space
                .align(Alignment.CenterVertically)

        )

        // Messages icon
        Image(
            painter = painterResource(id = R.drawable.ic_messages),
            contentDescription = "Messages",
            contentScale = ContentScale.Fit,
            modifier = Modifier
                .padding(end = 16.dp)
                .align(Alignment.CenterVertically)
        )
    }
}

@Preview
@Composable
fun CartCheckOut(modifier: Modifier = Modifier, totalAmount: Double = 380.0) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(80.dp)
            .background(
                color = Color(
                    ContextCompat.getColor(
                        LocalContext.current,
                        R.color.white
                    )
                )
            )
            .padding(16.dp)
            .padding(bottom = 0.dp)// Add padding if needed
    ) {
        val context = LocalContext.current
        val isActiveCheckout = remember {mutableStateOf(false)}

        // Add your content here, for example, Text and Button
        CheckBox(
            Modifier
                .padding(end = 8.dp)
                .align(Alignment.CenterVertically),
            checkedState = isActiveCheckout,
            onCheckedChange = {}
        )
        Text (
            text = "All",
            fontSize = 16.sp,
            color = Color(ContextCompat.getColor(LocalContext.current, R.color.green07)),
            modifier = Modifier
                .weight(1f) // Take remaining space
                .align(Alignment.CenterVertically)

        )

        Column (
            modifier = Modifier
                .fillMaxHeight()
                .padding(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            Text (
                text = "Total",
                fontSize = 16.sp,
                color = Color(ContextCompat.getColor(LocalContext.current, R.color.green07)),
            )
            Text (
                text = "₱${totalAmount}",
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold,
                color = Color(ContextCompat.getColor(LocalContext.current, R.color.green07)),
                overflow = TextOverflow.Ellipsis,
                maxLines = 2
            )
        }
        Spacer(modifier = Modifier.padding(end = 16.dp))
        Button(
            onClick = {
                val intent = Intent(context, CheckoutUI::class.java)
                context.startActivity(intent)
            },
            colors = ButtonDefaults.buttonColors(DarkPalmLeaf),
            modifier = Modifier
                .height(60.dp)

        ) {
            Text(
                text = "CHECKOUT",
                )
        }
    }
}


@Composable
fun CartProduct(
    product : Product,
    parentCheck : MutableState<Boolean>,
    fromChild : MutableState<Int>
) {
    var isActive = remember { mutableStateOf(false) }

    Row(
        modifier = Modifier.padding(top = 16.dp),
    ) {
        CheckBox(
            Modifier.padding(top = 50.dp),
            isActive,
            onCheckedChange = {
                if (isActive.value) {
                   parentCheck.value = true
                    fromChild.value += 1
                } else {
                    fromChild.value--
                }
            }
        )

        Spacer(modifier = Modifier.width(16.dp))
        Image(
            painter = painterResource(id = R.drawable.fruit_salad_img),
            contentDescription = "Product Image",
            modifier = Modifier
                .height(120.dp)
                .width(120.dp)
        )
        Spacer(modifier = Modifier.width(16.dp))
        Column {
            Text(
                text = "Fruit Salad",
                color = Color(ContextCompat.getColor(LocalContext.current, R.color.green03)),
                fontSize = 18.sp,
            )
            Variation()
            Variation()

        }

    }
}


@Composable
fun CartItem(
    modifier: Modifier = Modifier,
    storeName : String,
    products : List<Product>
) {

    Surface(
        modifier = modifier
            .fillMaxWidth()
            .wrapContentHeight()
            .paint(
                // Replace with your image id
                painterResource(id = R.drawable.rv_cart_container),
                contentScale = ContentScale.FillBounds
            )
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            val isChecked = remember {mutableStateOf(false)}
            val fromChild = remember {mutableStateOf(0)}
            val fromParent = remember { mutableStateOf(isChecked) }
            Row(
                horizontalArrangement = Arrangement.Start,
            ) {
                isChecked.value = fromChild.value != 0

                CheckBox(
                    checkedState = isChecked,
                    onCheckedChange = {
//                    isChecked.value = !isChecked.value
                }, modifier = Modifier.clickable {
                        fromParent.value.value = !fromParent.value.value
                    })
                Spacer(modifier = Modifier.width(16.dp))
                Row (
                    modifier = Modifier.weight(1f),
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.seller_ic),
                        contentDescription = "Seller Icon",
                    )
                    Text(
                        text = "${storeName}",
                        color = Color(ContextCompat.getColor(LocalContext.current, R.color.green02)),
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.padding(start = 6.dp).align(Alignment.CenterVertically)
                    )
                }
            }
            products.forEach { product ->
                CartProduct(
                    product,
                    isChecked,
                    fromChild
                )
            }
            Column (
                modifier = Modifier.fillMaxWidth()
            ) {
                Row () {
                    Spacer(modifier = Modifier.width(30.dp))
                    Text(
                        text = "Total: ₱160",
                        color = Color(ContextCompat.getColor(LocalContext.current, R.color.green03)),
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier
                            .padding(top = 16.dp)
                            .fillMaxWidth(0.5f)
                    )
                }
            }

        }

    }
}

@Composable
fun CheckBox(
    modifier: Modifier = Modifier,
    checkedState: MutableState<Boolean>,
    onCheckedChange: () -> Unit
) {
    val (checked, setChecked) = remember { mutableStateOf(checkedState) }
    if (checked.value) {
        Image (
            painter = painterResource(id = R.drawable.checkbox_active_ic),
            contentDescription = "Checkbox",
            modifier = modifier
                .clickable {
                    checked.value = !checked.value
                    setChecked(checked)
                    onCheckedChange()
                }

        )
    } else {
        Image (
            painter = painterResource(id = R.drawable.sv_cart_checkbox),
            contentDescription = "Checkbox",
            modifier = modifier
                .clickable {
                    checked.value = !checked.value
                    setChecked(checked)
                    onCheckedChange()
                }
        )
    }
}



@Composable
fun Variation(
    variationName: String = "Mango",
    qty: Int = 0
) {
    var (qty, setQty)  = remember { mutableStateOf(qty) }


    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 16.dp)
            .paint(
                painterResource(id = R.drawable.sv_cart_dholder),
                contentScale = ContentScale.FillBounds
            ),
        horizontalArrangement = Arrangement.SpaceBetween,
    ) {
        Text(
            text = "${variationName}",
            color = Color(ContextCompat.getColor(LocalContext.current, R.color.green03)),
            fontSize = 15.sp,
            modifier = Modifier.weight(2f)
        )
        Image(
            painter = painterResource(id = R.drawable.ic_plust_active),
            contentDescription = "Increase Quantity",
            modifier = Modifier
                .clickable {
                    setQty(qty + 1)
                }
                .padding(end = 5.dp)
        )
        Text(
            text = "${qty}",
            color = Color(ContextCompat.getColor(LocalContext.current, R.color.green02)),
        )
        Image(
            painter = painterResource(id = R.drawable.ic_minus_active),
            contentDescription = "Decrease Quantity",
            modifier = Modifier
                .clickable {
                    setQty(qty - 1)
                }
                .padding(start = 5.dp)
        )
    }
}
