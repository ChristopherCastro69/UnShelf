package com.example.unshelf.view.SellerBottomNav.screens.orders

import JostFontFamily
import android.content.ClipData.Item
import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.unshelf.R
import com.example.unshelf.model.entities.Order
import com.example.unshelf.ui.theme.DarkPalmLeaf
import com.example.unshelf.ui.theme.PalmLeaf
import com.example.unshelf.ui.theme.WatermelonRed


class OrderApprovalView: ComponentActivity(){
    override fun onCreate(savedInstanceState: Bundle?){
        super.onCreate(savedInstanceState)
        setContent {
            OrderApproval()
        }
    }
}
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun OrderApproval() {
    val context = LocalContext.current;
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
        }
    ) { innerPadding ->
        LazyColumn(modifier = Modifier.padding(innerPadding)) {
            item {
                Image(painterResource(id = R.drawable.bread),
                    contentDescription = null,
                    modifier = Modifier.fillMaxWidth(),
                    contentScale = ContentScale.Crop)
                Row(modifier = Modifier.padding(20.dp, 20.dp)) {
                    Column {
                        Text(text = "${""}", // Product name
                            fontFamily = JostFontFamily,
                            fontWeight = FontWeight.Bold,
                            fontSize = 24.sp)
                        Text(text = "Total: ${""}", // Total Price
                            fontFamily = JostFontFamily,
                            fontWeight = FontWeight.Bold,
                            fontSize = 20.sp)
                    }
                }
                Box(
                    modifier = Modifier
                        .fillMaxWidth(1f),
                    contentAlignment = Alignment.Center
                ){
                    Card(
                        modifier = Modifier
                            .padding(20.dp, 10.dp, 20.dp, 0.dp)
                            .size(500.dp, 150.dp)
                            .fillMaxWidth(1f),
                    ){
                        Row {
                            Image(painterResource(
                                id = R.drawable.profile),
                                modifier = Modifier
                                    .padding(20.dp),
                                contentDescription = null
                            )
                            Column(
                                modifier = Modifier
                                    .padding(20.dp)
                            ) {
                                Text(text = "Christopher",
                                    fontFamily = JostFontFamily,
                                    fontWeight = FontWeight.Bold,
                                    fontSize = 20.sp,
                                    modifier = Modifier
                                        .padding(0.dp, 0.dp, 0.dp, 5.dp))
                                Text(text = "Order#: ",
                                    fontFamily = JostFontFamily,
                                    fontWeight = FontWeight.Normal,
                                    fontSize = 13.sp)
                                Text(text = "Order Date: ",
                                    fontFamily = JostFontFamily,
                                    fontWeight = FontWeight.Normal,
                                    fontSize = 13.sp)
                                Text(text = "Voucher: ",
                                    fontFamily = JostFontFamily,
                                    fontWeight = FontWeight.Normal,
                                    fontSize = 13.sp)
                            }
                        }
                    }
                }
                Box(
                    modifier = Modifier
                        .fillMaxWidth(1f)
                        .padding(20.dp, 20.dp, 20.dp, 0.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Card(
                        modifier = Modifier
                            .fillMaxWidth(1f)
                            .size(50.dp),
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
                            Button(
                                onClick = {  },
                                colors = ButtonDefaults.buttonColors(DarkPalmLeaf),
                                modifier = Modifier
                                    .padding(0.dp, 0.dp, 10.dp, 0.dp)
                            )
                            {
                                Text("Yes")
                            }
                            Button(
                                onClick = {  },
                                colors = ButtonDefaults.buttonColors(WatermelonRed))
                            {
                                Text("No")
                            }
                        }
                    }
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewOrdersApproval() {
    Column {
        OrderApproval()
    }
}
