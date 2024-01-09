package com.example.unshelf.view.productView

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.paint
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.content.ContextCompat
import coil.compose.rememberImagePainter
import com.example.unshelf.R
import com.example.unshelf.model.entities.Product
import com.example.unshelf.ui.theme.MiddleGreenYellow
import com.example.unshelf.ui.theme.PalmLeaf
import com.example.unshelf.view.BuyerBottomNav.ui.MainNavigationActivityBuyer
import com.example.unshelf.view.SellerBottomNav.screens.orders.PickUPCompose

class OrderTracking : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            OrderTrackingUI()
        }
    }
}
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Preview
@Composable
fun OrderTrackingUI() {
    Scaffold(
    ) {
        Column() {
            MenuOT()
            Column (modifier = Modifier
                .fillMaxSize()
                .verticalScroll(state = rememberScrollState())) {
                repeat (2) {
                    OrderItem()
                }
            }

        }


    }
}

@Composable
fun MenuOT(modifier: Modifier = Modifier) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .background(
                color = Color(
                    ContextCompat.getColor(
                        LocalContext.current,
                        R.color.green02
                    )
                )
            )
            .padding(top = 16.dp)
    ) {
        Row() {
            val context = LocalContext.current
            Image(
                painter = painterResource(id = R.drawable.ic_backbtn),
                contentDescription = "Back",
                contentScale = ContentScale.Fit,
                modifier = Modifier
                    .padding(start = 16.dp, end = 8.dp)
                    .align(Alignment.CenterVertically)
                    .clickable {
                        val intent =
                            Intent(context, MainNavigationActivityBuyer::class.java)
                        context.startActivity(intent)
                    }
            )

            Text(
                text = "Order Tracking",
                fontSize = 30.sp,
                color = Color.White,
                fontWeight = FontWeight.Bold,
                modifier = Modifier
                    .weight(1f) // Take remaining space
                    .align(Alignment.CenterVertically)
            )
        }
        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            DropdownMenuExample()
            Image(
                painter = painterResource(id = R.drawable.ic_pickup_sort),
                contentDescription = "Sort",
                modifier = Modifier
                    .height(50.dp)
                    .width(50.dp)
            )
        }

        // Spacer to create space
        Spacer(modifier = Modifier.height(16.dp))

        // Colored Row
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(5.dp)
                .background(
                    color = Color(
                        ContextCompat.getColor(
                            LocalContext.current,
                            R.color.green05
                        )
                    )
                )
        ) {}
    }
}

@Composable
fun DropdownMenuExample() {
    var expanded by remember { mutableStateOf(false) }
    var selectedItem by remember { mutableStateOf("Item 1") }

    Row (
        modifier = Modifier
            .padding(start = 16.dp, end = 16.dp, top = 16.dp)
            .background(Color.White)
            .clickable { expanded = true }
            .height(50.dp)
            .fillMaxWidth(0.8f)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(text = selectedItem) // Add the text parameter here
            Icon(imageVector = Icons.Default.ArrowDropDown, contentDescription = "Dropdown")
        }

        DropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false },
            modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(16.dp))
        ) {
            DropdownMenuItem(
                onClick = {
                    selectedItem = "Item 1"
                    expanded = false
                },
                text = { Text("Item 1") }  // Provide the option text here
            )
            DropdownMenuItem(onClick = {
                selectedItem = "Item 2"
                expanded = false
            }, text = { Text("Item 2") })
            DropdownMenuItem(onClick = {
                selectedItem = "Item 3"
                expanded = false
            }, text = { Text("Item 3") })
        }
    }
}

@Composable
fun DropdownMenuApp() {
    Scaffold(
        content = {it
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp),
                verticalArrangement = Arrangement.Center
            ) {
                DropdownMenuExample()
            }
        }
    )
}

@Preview
@Composable
fun OrderItem() {
    Surface(
        modifier = Modifier.fillMaxWidth()
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth(0.6f)
                ) {
                    Row {
                        Image(
                            painter = painterResource(id = R.drawable.ic_ot_shop),
                            contentDescription = "Shop Icon",
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(
                            text = "John Doe's Market",
                            fontSize = 20.sp,
                            color = Color(ContextCompat.getColor(LocalContext.current, R.color.green02)),
                        )
                    }
                    Spacer(modifier = Modifier.height(8.dp))
                    Row(
                        modifier = Modifier.paint(
                            painterResource(id = R.drawable.ic_order_button),
                            contentScale = ContentScale.FillBounds
                        ),
                        horizontalArrangement = Arrangement.Center
                    ) {
                        Text(
                            modifier = Modifier.padding(6.dp, 3.dp),
                            text = "For Delivery",
                            fontSize = 14.sp,
                            color = Color(ContextCompat.getColor(LocalContext.current, R.color.green03))
                        )
                    }
                }
                Button(
                    onClick = {

                    },
                    colors = ButtonDefaults.buttonColors(PalmLeaf)
                ) {
                    Text(
                        text = "Add Review",
                    )
                }
            }
            Spacer(modifier = Modifier.height(8.dp))
            Image(painter = painterResource(id = R.drawable.ic_ot_line), contentDescription = "line")
            Spacer(modifier = Modifier.height(8.dp))
            Row (
                modifier = Modifier.fillMaxWidth(),
            ) {
                Image(painter = painterResource(id = R.drawable.ic_motor), contentDescription = "motor")
                Spacer(modifier = Modifier.width(20.dp))
                Column () {
                    Text (
                        text = "Pickup Details",
                        color = Color(ContextCompat.getColor(LocalContext.current, R.color.green03)),
                        fontSize = 19.sp,
                        fontWeight = FontWeight.Bold
                    )
                    Spacer(modifier = Modifier.height(2.dp))
                    Text ( text = "Rider: Rider Name",
                        color = Color(ContextCompat.getColor(LocalContext.current, R.color.green03)),
                        fontSize = 14.sp,)
                    Spacer(modifier = Modifier.height(2.dp))
                    Text ( text = "Status: Parcel has...",
                        color = Color(ContextCompat.getColor(LocalContext.current, R.color.green03)),
                        fontSize = 14.sp,)
                    Spacer(modifier = Modifier.height(2.dp))
                    Text ( text = "Estimated delivery time: time",
                        color = Color(ContextCompat.getColor(LocalContext.current, R.color.green03)),
                        fontSize = 14.sp,)
                }
            }
            Spacer(modifier = Modifier.height(8.dp))
            Image(painter = painterResource(id = R.drawable.ic_ot_line), contentDescription = "line")
            repeat(2) {
                OrderProduct()
            }
            Spacer(modifier = Modifier.height(8.dp))
            Row (
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.End
            ) {
                Text ( text = "Total: ₱",
                    color = Color(ContextCompat.getColor(LocalContext.current, R.color.green02)),
                    fontSize = 20.sp,
                    )
                Text ( text = "500.00",
                    color = Color(ContextCompat.getColor(LocalContext.current, R.color.green03)),
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold)
            }
        }
    }
}

@Preview
@Composable
fun OrderProduct (
) {
    Surface() {
        Row (
            modifier = Modifier.padding(top = 10.dp, end = 10.dp)
        ) {
            Image(
                painter = rememberImagePainter(
                    data = null,
                    builder = {
                        crossfade(true)
                        placeholder(R.drawable.fruit_salad_img) // Optional: Placeholder image resource
                    }
                ),
                contentScale = ContentScale.Crop,
                contentDescription = "Product Image",
                modifier = Modifier
                    .height(120.dp)
                    .width(120.dp)
            )
            Spacer( modifier = Modifier.width(10.dp))
            Column(
                modifier = Modifier.padding(top = 10.dp)
            ) {
                Text (text = "Santol",
                    color = Color(ContextCompat.getColor(LocalContext.current, R.color.green03)),
                    fontSize = 20.sp,)
                Text ( text = "Purchase by kilo",
                    color = Color(ContextCompat.getColor(LocalContext.current, R.color.green03)),
                    fontSize = 16.sp,)
                Spacer(modifier = Modifier.height(10.dp))
                Text ( text = "₱25.00/kilo",
                    color = Color(ContextCompat.getColor(LocalContext.current, R.color.green03)),
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold)
            }
            Spacer(modifier = Modifier.weight(1f))
            Text ( modifier = Modifier.padding(top = 10.dp),
                text = "x20",
                color = Color(ContextCompat.getColor(LocalContext.current, R.color.green03)),
                fontSize = 14.sp,)
        }
    }
}