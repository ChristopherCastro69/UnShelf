package com.example.unshelf.view.productView

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
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
import com.example.unshelf.R
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
@Preview
@Composable
fun OrderTrackingUI() {
    Scaffold(
        topBar = {
            MenuOT()

        }
    ) {it
        // Content of the OrderTracking screen
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
    Surface (
        modifier = Modifier.fillMaxWidth()
    ) {
        Row (
            modifier = Modifier.padding(16.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ){
            Column (
                modifier = Modifier
                    .fillMaxWidth(0.6f)
            ) {
                Row {
                    Image(
                        painter = painterResource(id = R.drawable.ic_ot_shop),
                        contentDescription = "Shop Icon",

                        )
                    Text(
                        text = "John Doe's Market",
                        fontSize = 20.sp,
                        color = Color(ContextCompat.getColor(LocalContext.current, R.color.green02)),
                    )
                }
                Spacer(modifier = Modifier.height(8.dp))
                Row (
                    modifier = Modifier.paint(
                        painterResource(id = R.drawable.ic_order_button),
                        contentScale = ContentScale.FillBounds
                    ),
                    horizontalArrangement = Arrangement.Center
                ) {
                    Text( text = "For Delivery")
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

    }
}