package com.example.unshelf.view.SellerBottomNav.screens.orders

import JostFontFamily
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.unshelf.R
import com.example.unshelf.ui.theme.DeepMossGreen

// Assuming drawable resources exist for these icons
// ...

data class Order(
    val productImageRes: Int,
    val productName: String,
    val orderID: String,
    val orderDate: String,
    val price: Double,
    val status: String
)

// Sample data
val ordersList = listOf(
    Order(R.drawable.loaf, "Product", "12194bdkl", "10/19/2023", 399.00, "Pending Order"),
    // Add more orders as per your data
    Order(R.drawable.loaf, "Product", "12194bdkl", "10/19/2023", 399.00, "Pending Order"),

    Order(R.drawable.loaf, "Product", "12194bdkl", "10/19/2023", 399.00, "Pending Order"),

    Order(R.drawable.loaf, "Product", "12194bdkl", "10/19/2023", 399.00, "Pending Order"),

    Order(R.drawable.loaf, "Product", "12194bdkl", "10/19/2023", 399.00, "Pending Order"),
    Order(R.drawable.loaf, "Product", "12194bdkl", "10/19/2023", 399.00, "Pending Order"),

    Order(R.drawable.loaf, "Product", "12194bdkl", "10/19/2023", 399.00, "Pending Order"),

    )

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Orders() {
    var selectedTabIndex by remember { mutableStateOf(0) }
    val filterOptions = listOf("Pending", "Approved", "Cancelled", "Refunded", "Paid")

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text("Orders",
                    color = Color.White,
                        fontFamily = JostFontFamily,
                        fontWeight = FontWeight.Medium,
                    ) },

                colors = TopAppBarDefaults.mediumTopAppBarColors(
                    containerColor = Color(0xFF4CAF50)
                ),

                navigationIcon = {
                    IconButton(onClick = { /* TODO: Handle back action */ }) {
//                        Icon(
//                            painter = painterResource(id = R.drawable.ic_back_arrow), // Use the correct back icon resource
//                            contentDescription = "Back",
//
//                        )
                        Icon(Icons.Filled.ArrowBack, "Menu", tint = Color.White)
                    }
                },

                actions = {
                    IconButton(onClick = { /* TODO: Handle menu action */ }) {
                        Icon(Icons.Filled.Menu, "Menu", tint = Color.White)
                    }
                }
            )
        }
    ) { innerPadding ->
        Column(modifier = Modifier.padding(innerPadding)) {
            ScrollableTabRow(selectedTabIndex = selectedTabIndex) {
                filterOptions.forEachIndexed { index, text ->
                    Tab(
                        selected = selectedTabIndex == index,
                        onClick = { selectedTabIndex = index },
                        text = { Text(text, color = if (selectedTabIndex == index) DeepMossGreen else Color.LightGray) }
                    )
                }
            }
            LazyColumn {
                items(ordersList) { order ->
                    OrderCard(order)
                }
            }
        }
    }
}

@Composable
fun OrderCard(order: Order) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Row(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Image(
                painter = painterResource(id = order.productImageRes),
                contentDescription = order.productName,
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
                Text(text = order.productName, fontWeight = FontWeight.Bold)
                Text(text = "Order ID: ${order.orderID}")
                Text(text = order.orderDate)
            }
            Column(
                horizontalAlignment = Alignment.End,
                modifier = Modifier.fillMaxHeight()
            ) {
                Text(text = "₱${order.price}", fontWeight = FontWeight.Bold)
                Text(text = order.status, color = Color(0xFF4CAF50), fontWeight = FontWeight.Bold)
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewOrdersScreen() {
    Column {
        Orders()
    }
}
