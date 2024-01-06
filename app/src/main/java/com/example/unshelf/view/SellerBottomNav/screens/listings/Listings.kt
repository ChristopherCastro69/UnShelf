package com.example.unshelf.view.SellerBottomNav.screens.listings

// or, if you're using StateFlow or LiveData:
import JostFontFamily
import android.content.Context
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Sort
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ScrollableTabRow
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRowDefaults.Indicator
import androidx.compose.material3.TabRowDefaults.tabIndicatorOffset
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import coil.compose.rememberAsyncImagePainter
import com.example.unshelf.R
import com.example.unshelf.model.entities.Product
import com.example.unshelf.model.firestore.seller.listingsModel.ProductViewModel
import com.example.unshelf.ui.theme.DeepMossGreen
import com.example.unshelf.ui.theme.PalmLeaf
import com.example.unshelf.ui.theme.WatermelonRed
import com.example.unshelf.view.SellerBottomNav.screens.dashboard.sellerId
import com.example.unshelf.view.SellerBottomNav.screens.dashboard.storeId


var productID = mutableStateOf<String?>(null)
var showDialog = mutableStateOf(false)

// ViewModel to handle fetching products



// Sample data

@Preview(showBackground = true)
@Composable
fun PreviewListings() {
    // Mock NavController for the preview
    val navController = rememberNavController()


    // Pass the mockSellerId to the Listings composable
    Listings(navController, sellerId.value, storeId.value)
}


@Composable
fun Listings(navController: NavController, sellerId: String, storeId: String) {
    val selectedIndex = remember { mutableStateOf(0) }
    Column {

        TopBar(navController)
        FilterTabs(selectedIndex = selectedIndex) { index ->
            // Handle tab selection
            // Update other parts of your UI based on the selected tab
        }
        ProductList(sellerId = sellerId, storeId = storeId, navController, selectedIndex.value)
    }
}

@Composable
fun TopBar(navController: NavController) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color.Transparent) // Use your desired background color
            .border(
                width = 1.dp, // Set the border width
                color = DeepMossGreen, // Set the border color
                shape = RectangleShape // Use RectangleShape for a straight line
            )
            .padding(bottom = 1.dp) // This is to offset the border's width from the content
    ){
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .background(PalmLeaf), // Replace with your desired background color
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = "Listings",
                fontFamily = JostFontFamily,
                color = Color.White,
                modifier = Modifier.padding(start = 16.dp, top = 16.dp, bottom = 16.dp),
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold
            )
            Spacer(modifier = Modifier.weight(1f))  // This spacer will push the elements to each end of the Row
            Text(
                text = "Add Items",
                color = Color.White,
                modifier = Modifier.align(Alignment.CenterVertically),
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold
            )

            IconButton(onClick = {
                productID.value = null  // Set productID to null for adding a new product
                imageUri.value = null
                flag.value = true
                navController.navigate("addProduct/${productID.value}")},

            ) {
                Icon(
                    painter = painterResource(id = R.drawable.button_plus), // Replace with your add icon resource
                    contentDescription = "Add",
                    tint = Color.White
                )
            }
        }
    }

}


@Composable
fun FilterTabs(selectedIndex: MutableState<Int>, onTabSelected: (Int) -> Unit) {
    val filterOptions = listOf("Active", "Unlisted")

    Row(modifier = Modifier.fillMaxWidth()) {
        // Wrap the ScrollableTabRow in a Box with a weight modifier to ensure it doesn't take up all space
        Box(modifier = Modifier.weight(1f)) {
            ScrollableTabRow(
                selectedTabIndex = selectedIndex.value,
                edgePadding = 0.dp,
                indicator = { tabPositions ->
                    // Draw the indicator here
                    Indicator(
                        Modifier.tabIndicatorOffset(tabPositions[selectedIndex.value]),
                        color = DeepMossGreen, // Set the selected line color to deep moss green
                        height = 3.dp // Set the height of the indicator
                    )
                }
            ) {
                filterOptions.forEachIndexed { index, text ->
                    Tab(
                        selected = selectedIndex.value == index,
                        onClick = {
                            selectedIndex.value = index
                            onTabSelected(index)
                        },
                        text = {
                            Text(
                                text = text,
                                color = if (selectedIndex.value == index) DeepMossGreen else Color.LightGray
                            )
                        }
                    )
                }
            }
        }

        // Now the IconButton should be visible because it is outside the Box
        IconButton(onClick = { /* TODO: Handle sort action */ }) {
            Icon(
                imageVector = Icons.Default.Sort, // Using material icons
                contentDescription = "Sort",
                tint = DeepMossGreen
            )
        }
    }
}
@Composable
fun ProductList(sellerId: String, storeId: String, navController: NavController, selectedIndex: Int) {
    val productViewModel: ProductViewModel = viewModel()
    val products by productViewModel.products.collectAsState()

    // This will reload products based on the active tab
    LaunchedEffect(selectedIndex) {
        when (selectedIndex) {
            0 -> productViewModel.fetchActiveProductsForSeller(sellerId, storeId)
            1 -> productViewModel.fetchInactiveProductsForSeller(sellerId, storeId)
        }
    }

    if (products.isNotEmpty()) {
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .background(Color(0xFFE8F5E9)) // Adjust the background color as needed
        ) {
            items(products) { product ->
                ProductCard(product, navController, productViewModel, LocalContext.current)
            }
        }
    } else {
        // Show a loading indicator or message while waiting for products to load
        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            CircularProgressIndicator() // or a custom loading component
        }
    }
}

@Composable
fun ProductCard(product: Product, navController: NavController, productViewModel: ProductViewModel, context: Context) {


    if (showDialog.value) {
        AlertDialog(
            onDismissRequest = { showDialog.value = false },
            title = { Text("Delete Product") },
            text = { Text("Are you sure you want to delete this product?") },
            confirmButton = {
                TextButton(
                    onClick = {
                        showDialog.value = false
                        productViewModel.deleteProduct(sellerId.value, storeId.value, product.productID, context)
                    }
                ) {
                    Text("Yes")
                }
            },
            dismissButton = {
                TextButton(
                    onClick = { showDialog.value = false }
                ) {
                    Text("No")
                }
            }
        )
    }

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
        shape = MaterialTheme.shapes.medium // This gives the Card rounded corners.
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.background(Color.White)
        ) {
            // Assuming 'product.thumbnail' contains the URL of the image
            val imageUrl = product.thumbnail
            val painter = rememberAsyncImagePainter(model = imageUrl)

            Image(
                painter = painter,
                contentDescription = product.productName,
                modifier = Modifier
                    .size(80.dp)
                    .padding(8.dp)
            )
            Column(
                modifier = Modifier
                    .weight(1f)
                    .padding(start = 8.dp)
            ) {
                Text(
                    text = product.productName,
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold, // Set the font weight to bold
                    fontFamily = FontFamily.Serif
                )
                Text(text = "Quantity: ${product.quantity}", color = Color.Gray)
                Text(text = "₱${String.format("%.2f", product.price)}", color = Color.Gray)

            }
            IconButton(onClick = {
                navController.navigate("addProduct/${product.productID}")
                flag.value = false
            }) {
                Icon(
                    painter = painterResource(id = R.drawable.button_edit),
                    contentDescription = "Edit",
                    tint = Color(0xFF4CAF50) // You can also remove the tint if your drawable has its own colors
                )
            }

            IconButton(onClick = {
                showDialog.value = true
            }) {
                Icon(
                    painter = painterResource(id = R.drawable.button_delete),
                    contentDescription = "Delete",
                    tint = WatermelonRed // Adjust the tint color as needed
                )
            }
        }
    }
}



