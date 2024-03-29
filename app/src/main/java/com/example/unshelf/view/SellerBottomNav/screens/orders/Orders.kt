package com.example.unshelf.view.SellerBottomNav.screens.orders

import JostFontFamily
import android.content.Intent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import coil.compose.rememberAsyncImagePainter
import com.example.unshelf.R
import com.example.unshelf.controller.OrderApprovalController
import com.example.unshelf.controller.OrderController
import com.example.unshelf.model.checkout.OrderLineItem
import com.example.unshelf.model.entities.Order
import com.example.unshelf.ui.theme.DarkMiddleGreenYellow
import com.example.unshelf.ui.theme.DeepMossGreen
import com.example.unshelf.ui.theme.MiddleGreenYellow
import com.example.unshelf.ui.theme.PalmLeaf
import com.example.unshelf.view.Wallet.Wallet


//val ordersList = null
// Add more orders as per your data

@OptIn(ExperimentalMaterial3Api::class)
@Composable

fun Orders() {
    val orderViewModel:OrderController = viewModel()
    val context = LocalContext.current
    if(OrderController.orderList.value.isEmpty()) {
        OrderController.fetchOrder()
    }
    //OrderController.fetchOrder()
    val isLoading = remember { OrderController.isLoading }
    val orders = remember{ orderViewModel.orderList }
    var selectedTabIndex by remember { mutableStateOf(0) }
    val filterOptions = listOf("Pending", "Approved",  "Completed", "Cancelled", "Refunded")

    Scaffold(
        topBar = {
            Row(modifier = Modifier
                .background(PalmLeaf)
                .height(55.dp)) {
                Text("Orders",
                    modifier = Modifier
                        .padding(start = 10.dp)
                        .align(Alignment.CenterVertically),
                    color = Color.White,
                    fontFamily = JostFontFamily,
                    fontWeight = FontWeight.Medium,
                    fontSize = 23.sp
                )
                Spacer(modifier = Modifier.weight(1f))
                Icon(
                    painter = painterResource(id = R.drawable.wallet),
                    contentDescription = "Wallet",
                    tint = Color.Unspecified, // Add tint color if required
                    modifier = Modifier
                        .align(Alignment.CenterVertically)
                        .padding(end = 10.dp)
                        .clickable {
                            val intent = Intent(context, Wallet::class.java)
                            intent.putExtra("user", "seller")
                            context.startActivity(intent)
                        }
                )
            }
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
            Row(
                modifier = Modifier.padding(start = 10.dp, top = 10.dp)
            ){
                var code by remember{ mutableStateOf("") }
                OutlinedTextField(
                    value = code ,
                    onValueChange = {code = it},
                    modifier = Modifier.clip(RoundedCornerShape(5.dp)).weight(1f),
                    colors = TextFieldDefaults.outlinedTextFieldColors(
                        focusedBorderColor = PalmLeaf,
                        unfocusedBorderColor = DeepMossGreen)
                    )
                Button(
                    onClick = {
                        if(code.isNotEmpty()) {
                            for (order in orders.value) {
                                if (order.refNo == code) {
                                    val intent = Intent(context, OrderApprovalView::class.java)
                                    intent.putExtra("order", order)
                                    intent.putExtra("code", code)
                                    OrderApprovalController.completeOrder(order, code)
                                    context.startActivity(intent)
                                    println("Code " + code)
                                    break;
                                }
                            }
                        }
                    },
                    shape = RoundedCornerShape(5.dp),
                    modifier = Modifier.clip(shape = RectangleShape).height(55.dp).padding(start = 5.dp).weight(0.5f),
                    colors = ButtonDefaults.buttonColors(containerColor = PalmLeaf)
                ) {
                    Text(text = "Confirm")
                }

            }
            if(orders.value.isNotEmpty()) {
                LazyColumn {
                    items(orders.value) { order ->
                        OrderCard(order.products, order)
                    }
                }
            } else {
//                CircularProgressIndicator(color = PalmLeaf, modifier = Modifier
//                    .align(Alignment.CenterHorizontally)
//                    .size(50.dp)
//                    .padding(top = 10.dp))
                Text(
                    text = "No orders available.",
                    fontSize = 20.sp,
                    color = Color.Gray,
                    modifier = Modifier.padding(16.dp)
                )

            }
        }
    }
}

@Composable
fun OrderCard(products: List<OrderLineItem>, order: Order) {
    val context = LocalContext.current
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
            .clickable {
                val intent = Intent(context, OrderApprovalView::class.java)
                intent.putExtra("order", order)
                context.startActivity(intent)
            },
        colors = CardColors(containerColor = Color.White, DeepMossGreen,Color.Gray,Color.Gray),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
    ) {
        Row(modifier = Modifier
            .padding(start = 16.dp, top = 20.dp)
        ){
            Text(text = "Order ID:", fontWeight = FontWeight.Bold, fontSize = 16.sp, fontFamily = JostFontFamily)
            Text(text = " ${order.orderID}", fontWeight = FontWeight.Normal, fontSize = 16.sp, fontFamily = JostFontFamily, color = Color.Gray)
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
                    Text(text = "${product.name}",
                        fontWeight = FontWeight.Normal,
                        fontFamily = JostFontFamily,
                        fontSize = 16.sp,
                        color = DeepMossGreen)
                }
                Column(
                    horizontalAlignment = Alignment.End,
                    modifier = Modifier.fillMaxHeight()
                ) {
                    Text(text = "₱${String.format("%.2f", product.amount)}",
                        fontWeight = FontWeight.Normal,
                        fontFamily = JostFontFamily,
                        color = DeepMossGreen)
                    Text(text = "x${product.quantity}",
                        fontWeight = FontWeight.Normal,
                        fontFamily = JostFontFamily,
                        color = DeepMossGreen)
                }
            }
        }
        Row(modifier = Modifier
            .padding(start = 16.dp, top = 15.dp),
            verticalAlignment = Alignment.CenterVertically){
            Text(text = "Status:",
                fontWeight = FontWeight.Bold,
                fontSize = 18.sp,
                fontFamily = JostFontFamily)
            Text(text = " ${order.orderStatus}",
                fontWeight = FontWeight.Normal,
                fontSize = 18.sp,
                fontFamily = JostFontFamily,
                color = Color.Gray,
                modifier = Modifier.weight(1f))
            Text(text = "Total: ",
                fontWeight = FontWeight.Bold,
                fontSize = 16.sp,
                fontFamily = JostFontFamily,
                modifier = Modifier.padding(end = 20.dp),
                textAlign = TextAlign.End,
            )
            Text(text = "₱${order.totalAmount}",
                fontWeight = FontWeight.Bold,
                fontSize = 16.sp,
                fontFamily = JostFontFamily,
                modifier = Modifier.padding(end = 20.dp),
                textAlign = TextAlign.End,
                color = DeepMossGreen,
            )
        }
        Row(
            modifier = Modifier.padding(start = 16.dp)
        ){
            Text(text = "Date:",
                fontWeight = FontWeight.Bold,
                fontSize = 16.sp,
                fontFamily = JostFontFamily
            )
            Text(text = " ${order.paymentTimestamp}",
                fontWeight = FontWeight.Normal,
                fontSize = 16.sp,
                fontFamily = JostFontFamily,
                modifier = Modifier.padding(bottom = 10.dp),
                color = Color.Gray,
                )
        }

    }
}


