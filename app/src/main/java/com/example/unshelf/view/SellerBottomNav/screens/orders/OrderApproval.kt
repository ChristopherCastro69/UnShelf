package com.example.unshelf.view.SellerBottomNav.screens.orders

import JostFontFamily
import android.content.ClipData.Item
import android.content.Intent
import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.annotation.RequiresApi
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.layout.ModifierInfo
import androidx.compose.ui.platform.LocalContext
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
import com.example.unshelf.model.checkout.OrderLineItem
import com.example.unshelf.model.entities.Order
import com.example.unshelf.model.entities.Product
import com.example.unshelf.ui.theme.DarkPalmLeaf
import com.example.unshelf.ui.theme.DeepMossGreen
import com.example.unshelf.ui.theme.PalmLeaf
import com.example.unshelf.ui.theme.WatermelonRed


class OrderApprovalView: ComponentActivity(){
    override fun onCreate(savedInstanceState: Bundle?){
        super.onCreate(savedInstanceState)
        setContent {
            val order: Order? = intent.getParcelableExtra("order")
            OrderApproval(order)
        }
    }
}
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun OrderApproval(order: Order?) {
    val products = order!!.products
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text("Order Approval",
                        color = Color.White,
                        fontFamily = JostFontFamily,
                        fontWeight = FontWeight.Medium,
                    ) },

                colors = TopAppBarDefaults.mediumTopAppBarColors(
                    containerColor = PalmLeaf
                ),

                navigationIcon = {
                    IconButton(onClick = {}) {
                        Icon(Icons.Filled.ArrowBack, "Back Button", tint = Color.White)
                    }
                }
            )
        },
        bottomBar = {
            Column(
                horizontalAlignment =  Alignment.End
            ){
                Row(
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.padding(bottom = 10.dp)
                ){
                    Text(text = "Total: ",
                        fontFamily = JostFontFamily,
                        fontWeight = FontWeight.Bold,
                        fontSize = 20.sp,
                        color = DeepMossGreen,
                        textAlign = TextAlign.Start)
                    Text(text = "₱${order.totalAmount}",
                        fontFamily = JostFontFamily,
                        fontWeight = FontWeight.Bold,
                        fontSize = 20.sp,
                        color = DeepMossGreen,
                        textAlign = TextAlign.End,
                        modifier = Modifier.padding(end = 15.dp))
                }
                Box(
                    modifier = Modifier
                        .fillMaxWidth(1f),
                    contentAlignment = Alignment.Center
                ) {
                    Card(
                        modifier = Modifier
                            .fillMaxWidth(1f)
                            .size(60.dp),
                    ) {
                        Row (
                            modifier = Modifier
                                .padding(5.dp),
                            verticalAlignment = Alignment.CenterVertically,
                        ){
                            Text(text = "Accept Order?",
                                fontFamily = JostFontFamily,
                                fontWeight = FontWeight.Bold,
                                fontSize = 16.sp,
                                modifier = Modifier
                                    .padding(10.dp, 0.dp, 40.dp, 0.dp),
                            )
                            Spacer(modifier = Modifier.weight(1f))
                            Button(
                                onClick = {  },
                                colors = ButtonDefaults.buttonColors(DarkPalmLeaf),
                                modifier = Modifier
                                    .padding(0.dp, 0.dp, 10.dp, 0.dp)
                                    .size(75.dp),
                            )
                            {
                                Text("Yes")
                            }
                            Button(
                                onClick = {  },
                                colors = ButtonDefaults.buttonColors(WatermelonRed),
                                modifier = Modifier
                                    .padding(0.dp, 0.dp, 10.dp, 0.dp)
                                    .size(75.dp),
                                )
                            {
                                Text("No")
                            }
                        }
                    }
                }
            }

        }
    ) { innerPadding ->
        Column(
            modifier = Modifier.padding(innerPadding),
            horizontalAlignment = Alignment.CenterHorizontally
            ) {
            Row(){
                Text(text = "Order#:",
                    fontFamily = JostFontFamily,
                    fontWeight = FontWeight.Bold,
                    fontSize = 17.sp,
                    textAlign = TextAlign.Center,
                    color = DeepMossGreen,
                    modifier = Modifier.padding(top = 25.dp))
                Text(text = " ${order.checkoutID}",
                    fontFamily = JostFontFamily,
                    fontWeight = FontWeight.Normal,
                    fontSize = 16.sp,
                    color = Color.Gray,
                    modifier = Modifier.padding(top = 25.dp))
            }
            Row(){
                Text(text = "Order Date: ",
                    fontFamily = JostFontFamily,
                    fontWeight = FontWeight.Bold,
                    fontSize = 16.sp,
                    color = DeepMossGreen)
                Text(text = "${order.paymentTimestamp}",
                    fontFamily = JostFontFamily,
                    fontWeight = FontWeight.Normal,
                    fontSize = 16.sp,
                    color = Color.Gray
                )
            }
            OrderDetails(products)
        }
    }
}

@Composable
fun OrderDetails(products: List<OrderLineItem>) {
    Card(
        modifier = Modifier
            .fillMaxWidth(1f),
        colors = CardColors(containerColor = Color.White, PalmLeaf,Color.Gray, Color.Gray)
    ) {
        for (product in products) {
            Row(
                modifier = Modifier
                    .padding(20.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                Image(
                    rememberAsyncImagePainter(model = product.images.get(0)),
                    contentDescription = "product image",
                    modifier = Modifier
                        .border(
                            BorderStroke(1.dp, PalmLeaf),
                            RoundedCornerShape(5.dp)

                        )
                        .size(120.dp)
                        .clip(MaterialTheme.shapes.small),
                    contentScale = ContentScale.Crop
                    )
                Spacer(modifier = Modifier.width(16.dp))
                Column(
                    horizontalAlignment = Alignment.Start,
                ) {
                    Text(
                        text = "${product.name}",
                        fontWeight = FontWeight.Bold,
                        fontFamily = JostFontFamily,
                        fontSize = 18.sp
                    )
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(end = 15.dp, top = 10.dp),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text(
                            text = "₱${product.amount}",
                            fontWeight = FontWeight.Bold,
                            fontFamily = JostFontFamily,
                        )
                        Text(
                            text = "x${product.quantity}",
                            fontWeight = FontWeight.Bold,
                            fontFamily = JostFontFamily,
                        )
                    }
                }
            }
        }
    }
}