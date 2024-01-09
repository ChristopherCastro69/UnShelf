package com.example.unshelf.view.SellerBottomNav.screens.orders

import JostFontFamily
import android.content.Intent
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import coil.compose.rememberAsyncImagePainter
import com.example.unshelf.controller.OrderController
import com.example.unshelf.model.checkout.OrderLineItem
import com.example.unshelf.model.entities.Order
import com.example.unshelf.ui.theme.PalmLeaf


//val ordersList = null
// Add more orders as per your data


@OptIn(ExperimentalMaterial3Api::class)
@Composable

fun Orders(navController: NavController) {
    val orderViewModel:OrderController = viewModel()
//    OrderController.orderList.value = emptyList()
    if(OrderController.orderList.value.isEmpty()) {
        OrderController.fetchOrder()
    }
//    OrderController.fetchOrder()
    val orders = remember{ orderViewModel.orderList }
    var selectedTabIndex by remember { mutableStateOf(0) }
    val filterOptions = listOf("Pending", "Approved",  "Completed", "Cancelled", "Refunded")

    for(order in orders.value) {
        println("Order " + order.checkoutID + " amount: " + order.netAmount)
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text("Orders",
                        modifier = Modifier
                            .padding(start = 5.dp),
                        color = Color.White,
                        fontFamily = JostFontFamily,
                        fontWeight = FontWeight.Medium,
                    ) },

                colors = TopAppBarDefaults.mediumTopAppBarColors(
                    containerColor = PalmLeaf
                ),

                )
        }
    ) { innerPadding ->
        Column(modifier = Modifier.padding(innerPadding)) {
            ScrollableTabRow(selectedTabIndex = selectedTabIndex) {
                filterOptions.forEachIndexed { index, text ->
                    Tab(
                        selected = selectedTabIndex == index,
                        onClick = { selectedTabIndex = index },
                        text = { Text(text, color = if (selectedTabIndex == index) PalmLeaf else Color.LightGray) }
                    )
                }
            }
            if(orders.value.isNotEmpty()) {
                LazyColumn {
                    items(orders.value) { order ->
                        OrderCard(order.products, order)
                    }
                }
            } else {
                CircularProgressIndicator(color = PalmLeaf, modifier = Modifier
                    .align(Alignment.CenterHorizontally)
                    .size(50.dp)
                    .padding(top = 10.dp))
            }
        }
    }
}

@Composable
fun OrderCard(products: List<OrderLineItem>, order: Order) {
    val context = LocalContext.current;
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
            .clickable {
                val intent = Intent(context, OrderApprovalView::class.java)
                context.startActivity(intent)
            },
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Column(modifier = Modifier
            .padding(start = 16.dp, top = 20.dp)){
            Text(text = "Order ID: ${order.checkoutID}", fontWeight = FontWeight.Bold, fontSize = 14.sp)
            Text(text = "Order Date: ${order.paymentTimestamp}", fontWeight = FontWeight.Bold, fontSize = 14.sp)
        }
        for(product in products){
            Row(
                modifier = Modifier
                    .padding(16.dp)
                    .fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Image(
                    rememberAsyncImagePainter(model = product.images.get(0)),
                    contentDescription = "product image",
                    modifier = Modifier
                        .size(60.dp)
                        .clip(MaterialTheme.shapes.small)
                )
                Spacer(modifier = Modifier.width(16.dp))
                Column(
                    modifier = Modifier
                        .weight(1f)
                        .padding(end = 16.dp) // Padding to ensure text does not touch the edge of the screen
                ) {
                    Text(text = "${product.name}", fontWeight = FontWeight.Bold)
                }
                Column(
                    horizontalAlignment = Alignment.End,
                    modifier = Modifier.fillMaxHeight()
                ) {
                    Text(text = "₱${String.format("%.2f", product.amount)}", fontWeight = FontWeight.Bold)
                    Text(text = "${order.orderStatus}", color = Color(0xFF4CAF50), fontWeight = FontWeight.Bold)
                }
            }
        }
    }
}

