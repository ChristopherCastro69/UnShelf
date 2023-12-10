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
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
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
import com.example.unshelf.ui.theme.MiddleGreenYellow
import com.example.unshelf.ui.theme.WatermelonRed
import com.example.unshelf.view.SellerBottomNav.screens.dashboard.sellerId
import com.example.unshelf.view.SellerBottomNav.screens.dashboard.storeId


var productID = mutableStateOf<String?>(null)


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
    Column {

        TopBar(navController)
        FilterTabs()
        ProductList(sellerId = sellerId, storeId = storeId, navController)
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
                .background(DeepMossGreen), // Replace with your desired background color
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
fun FilterTabs() {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)

    ) {
        Button(

            onClick = { /* TODO: Handle Active filter action */ },
            colors = ButtonDefaults.buttonColors(MiddleGreenYellow), // Replace with your active tab color
        ) {
            Text(text = "Active")
        }
        Spacer(modifier = Modifier.size(8.dp))
        Button(
            onClick = { /* TODO: Handle Unlisted filter action */ },
            colors = ButtonDefaults.buttonColors(WatermelonRed) // Replace with your unlisted tab color
        ) {
            Text(text = "Unlisted")
        }
        Spacer(modifier = Modifier.weight(1f))
        IconButton(onClick = { /* TODO: Handle sort action */ }) {
            Icon(
                painter = painterResource(id = R.drawable.button_sort), // Replace with your sort icon resource
                contentDescription = "Sort",
                tint = DeepMossGreen
            )
        }
    }
}

@Composable
fun ProductList(sellerId: String, storeId: String, navController: NavController) {
    // Use a ViewModel to manage the state and business logic
    val productViewModel: ProductViewModel = viewModel()
    val context = LocalContext.current

    // Call a function in your ViewModel to fetch products for the given sellerId
    LaunchedEffect(sellerId) {
        productViewModel.fetchProductsForSeller(sellerId, storeId)
    }

    // Observe the product list from your ViewModel
    val products = productViewModel.products.collectAsState()

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFE8F5E9)) // Adjust the background color as needed
    ) {
        items(products.value) { product ->
            ProductCard(product, navController, productViewModel, context)
        }
    }
}
@Composable
fun ProductCard(product: Product, navController: NavController, productViewModel: ProductViewModel, context: Context) {
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
                Text(text = "₱${product.price}", color = Color.Gray)
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

            IconButton(onClick = {   productViewModel.deleteProduct(sellerId.value, storeId.value, product.productID, context)
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



