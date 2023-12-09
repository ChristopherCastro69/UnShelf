package com.example.unshelf.view.SellerBottomNav.screens.listings

import JostFontFamily
import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.util.Log
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.RequiresApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.rememberAsyncImagePainter
import com.example.unshelf.model.entities.Product
import com.example.unshelf.model.firestore.seller.dashboardmodel.fetchProductDetails
import com.example.unshelf.model.firestore.seller.dashboardmodel.saveProductToFirestore
import com.example.unshelf.model.firestore.seller.dashboardmodel.updateProductToFirestore
import com.example.unshelf.model.firestore.seller.dashboardmodel.uploadImage
import com.example.unshelf.ui.theme.PalmLeaf
import com.example.unshelf.view.SellerBottomNav.screens.dashboard.sellerId
import com.example.unshelf.view.SellerBottomNav.screens.dashboard.storeId
import com.vanpra.composematerialdialogs.MaterialDialog
import com.vanpra.composematerialdialogs.datetime.date.datepicker
import com.vanpra.composematerialdialogs.rememberMaterialDialogState
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.time.format.DateTimeParseException

// First, define your Product data class to match the Firestore structure
var product = mutableStateOf("")
var sellerId = mutableStateOf("")
val sellerID = mutableStateListOf("")
var productName = mutableStateOf("")
var selectedCategory = mutableStateOf("Grocery") // Default to the first item in your categories list
var imageUri = mutableStateOf<Uri?>(null)
var galleryImageUris = mutableStateListOf<Uri>()
var productDescription = mutableStateOf("")
var productHashtags = mutableStateListOf<String>()
var marketPrice = mutableStateOf("0.00")
var voucherCode = mutableStateOf("0.00")
var discountPercent = mutableStateOf("")
@RequiresApi(Build.VERSION_CODES.O)
var pickedDate = mutableStateOf(LocalDate.now())

val stringDate = mutableStateOf("")

var productAdditionSuccess = mutableStateOf(false)
var flag = mutableStateOf(false)
var productQuantity = mutableStateOf("")
var status = mutableStateOf(false)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
@RequiresApi(Build.VERSION_CODES.O)
fun AddProducts(productId: String? = null) {
    Log.d("AddProducts", "LaunchedEffect Seller ID: ${sellerId.value}, Store ID: ${storeId.value}")
    // Log the current state of imageUri at the start of the function
    Log.d("AddProducts", "Initial imageUri: ${imageUri.value ?: "null or empty"}")

//    imageUri.value?.let {
//        DisplayImage(imageUri = it)
//    }
    // State variables

    var isLoadingProduct by remember { mutableStateOf(false) }
    // Fetch product details if productId is not null
    LaunchedEffect(productId) {
        productId?.let {


            isLoadingProduct = true
            fetchProductDetails(it, onSuccess = { product ->
                // Populate state variables with fetched product details
                productName.value = product.productName
                selectedCategory.value = product.categories.firstOrNull() ?: "Grocery"
                imageUri.value = Uri.parse(product.thumbnail)
                productDescription.value = product.description
                marketPrice.value = product.price.toString()
                discountPercent.value = product.discount.toString()
                voucherCode.value = product.voucherCode
               // pickedDate.value = LocalDate.parse(product.expirationDate, DateTimeFormatter.ofPattern("MM/dd/yyyy"))
                productQuantity.value = product.quantity.toString()
                status.value = product.isActive // Assuming isActive is a boolean in your Product data class
                val expirationDateString = product.expirationDate
                if (expirationDateString.isNotEmpty()) {
                    try {
                        pickedDate.value = LocalDate.parse(expirationDateString, DateTimeFormatter.ofPattern("MM/dd/yyyy"))
                    } catch (e: DateTimeParseException) {
                        Log.e("AddProduct", "Error parsing date: $expirationDateString", e)
                        // Set to default date or handle the error as needed
                        pickedDate.value = LocalDate.now()
                    }
                } else {
                    // Handle case where expiration date is empty
                    pickedDate.value = LocalDate.now() // You can set a default date or handle as required
                    Log.d("AddProduct", "Expiration date is empty, setting to current date")
                }

                isLoadingProduct = false
            }, onFailure = { exception ->
                Log.e("AddProducts", "Error fetching product details: ${exception.message}", exception)
                isLoadingProduct = false
            })
        }
    }

    if (productAdditionSuccess.value) {
        Toast.makeText(LocalContext.current, "Product added successfully", Toast.LENGTH_LONG).show()
        // Reset the flag so the toast won't show again unless a new product is added
        productAdditionSuccess.value = false
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        "Product Details",
                        color = Color.White,
                        fontFamily = JostFontFamily,
                        fontWeight = FontWeight.Medium,
                    ) },

                colors = TopAppBarDefaults.mediumTopAppBarColors(
                    containerColor = PalmLeaf
                ),

                navigationIcon = {
                    IconButton(onClick = { /* TODO: Handle back action */ }) {
//
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, "Menu", tint = Color.White)
                    }
                },
                actions = {

                }
            )
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .verticalScroll(rememberScrollState()) // Add vertical scroll
        ) {
            ProdName()
            Category()
            Thumbnail()
//            ProductGallery()
            ProductDescription()
            Marketprice()
            Voucher()
            QuantityInput()
            ExpirationDate()
            // Pass the current value of sellerId and storeId to AddButton
            AddButton(sellerId = sellerId.value, storeId = storeId.value, productId = productId)
        }

    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProdName(){
    Column(modifier = Modifier
        .fillMaxWidth()
        .padding(16.dp)
    ) {
        Text(
            text = "Product Name",
            fontFamily = JostFontFamily,
            fontWeight = FontWeight.Bold,
            fontSize = 16.sp,
            color = Color.Black,

            )
        OutlinedTextField(
            value = productName.value,
            onValueChange = { productName.value = it },
            label = { Text("Write a product name.") },
            modifier = Modifier
                .fillMaxWidth(),
//                .padding(4.dp), // You can adjust padding as needed
            keyboardOptions = KeyboardOptions.Default.copy(imeAction = ImeAction.Done),
            textStyle = TextStyle(fontSize = 16.sp, fontFamily = JostFontFamily),
            singleLine = true, // Add this line if you want the TextField to be single line
            colors = TextFieldDefaults.outlinedTextFieldColors(
                containerColor = Color(0xFFF0F0F0), // Apply the background color here
                unfocusedBorderColor = Color.Transparent,
                focusedBorderColor = Color.Transparent,
                cursorColor = Color.Black
            ),
            shape = RoundedCornerShape(4.dp) // Apply rounded shape here
        )


    }
}

@Composable
fun Category() {
    var expanded by remember { mutableStateOf(false) }
    val categories = listOf("Grocery", "Fruits", "Vegetables", "Baked Goods", "Meals")
    var selectedIndex by remember { mutableStateOf(categories.indexOf(selectedCategory.value)) }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
    ) {
        Text(
            text = "Category",
            fontFamily = JostFontFamily,
            fontWeight = FontWeight.Bold,
            fontSize = 16.sp,
            color = Color.Black
        )
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .background(Color(0xFFF0F0F0), RoundedCornerShape(4.dp))
                .clickable { expanded = true }
                .padding(16.dp)
        ) {
            Text(
                text = categories[selectedIndex],
                fontFamily = JostFontFamily,
                fontSize = 16.sp,
                color = Color.Black
            )
            DropdownMenu(
                expanded = expanded,
                onDismissRequest = { expanded = false }
            ) {
                categories.forEachIndexed { index, category ->
                    DropdownMenuItem(
                        text = { Text(category) },
                        onClick = {
                            selectedIndex = index
                            selectedCategory.value = categories[index]
                            expanded = false
                        }
                    )
                }
            }
        }
    }
}



@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Thumbnail() {
    // State to hold the selected image Uri
//    var imageUri by remember { mutableStateOf<Uri?>(null) }
    LocalContext.current

    // This launcher is used to start the image picker activity
    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) { uri: Uri? ->
        uri?.let {
            uploadImage(it, onSuccess = { downloadUrl ->
                imageUri.value = Uri.parse(downloadUrl)
            }, onFailure = { exception ->
                Log.e("Thumbnail", "Upload failed: ${exception.message}")
            })
        }
    }


    Log.d("Thumbnail", "imageUri value: ${imageUri.value}")
    Log.d("Thumbnail", "Flag value: $flag") // Log the flag value

    Column(
        modifier = Modifier
            .fillMaxWidth()
//            .padding(16.dp)
            .background(Color.Transparent), // This is the green background color
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.height(30.dp)) // Spacing from the top

        if(flag.value == false) {
            Image(
                painter = rememberAsyncImagePainter(model = imageUri.value),
                contentDescription = "Selected Thumbnail",
                modifier = Modifier
                    .fillMaxWidth()
                    .size(250.dp) // Size of the circle
                    .clip(RectangleShape)
                    .border(2.dp, PalmLeaf, RectangleShape)
                    .border(2.dp, Color.Gray, RectangleShape)
                    .clickable { launcher.launch("image/*") } // Change thumbnail on click
            )
            Log.d("Thumbnail", "Displaying image with URI: ${imageUri.value}")
        }
        // Check if an image URI is available and call DisplayImage
        else{
             Box(
                // If no image is selected, show the 'add' icon
                contentAlignment = Alignment.Center,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(250.dp)
//                .fillMaxHeight()
//                .size(250.dp) // Size of the circle
                    .background(PalmLeaf, RectangleShape) // White circle
                    .border(2.dp, Color.Gray, RectangleShape)
                    .clickable { launcher.launch("image/*") } // Open image picker when clicking on the box
            ) {
                Icon(
                    imageVector = Icons.Default.Add,
                    contentDescription = "Add Thumbnail",
                    tint = Color.White,
                    modifier = Modifier.size(24.dp) // Size of the plus icon
                )
                 Log.d("Thumbnail", "No image URI present, showing 'add' icon")
            }
    }
        Spacer(modifier = Modifier.height(10.dp)) // Spacing between the circle and text

        // Text under the image/thumbnail
        Text(
            text = if (flag.value == true) "Choose a thumbnail for your product" else "Tap to change thumbnail",
            fontFamily = JostFontFamily,
            fontWeight = FontWeight.Light,
            fontSize = 12.sp,
            color = Color.Black,
            modifier = Modifier.padding(12.dp)
        )

        // Button to remove the selected image
        if (imageUri.value != null && flag.value == false) {
            Button(
                onClick = { imageUri.value = null },
                colors = ButtonDefaults.buttonColors(containerColor = Color.Red),
                modifier = Modifier.padding(top = 4.dp)
            ) {
                Text("Remove Thumbnail", color = Color.White)
            }
        }

        Spacer(modifier = Modifier.height(10.dp)) // Spacing from the bottom
    }
}



@Composable
fun ProductGallery() {
    val pickImageLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.StartActivityForResult(),
        onResult = { result ->
            if (result.resultCode == Activity.RESULT_OK) {
                val imageUri: Uri? = result.data?.data
                imageUri?.let { galleryImageUris.add(it) }
            }
        }
    )

    Column(modifier = Modifier
        .fillMaxWidth()
        .padding(16.dp)) {
        Text(
            text = "Product gallery",
            fontFamily = JostFontFamily,
            fontWeight = FontWeight.Bold,
            fontSize = 16.sp,
            color = Color.Black,
        )

        // Horizontal scrollable row for product images
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .horizontalScroll(rememberScrollState())
        ) {
            galleryImageUris.forEachIndexed { index, uri ->
                if (!uri.toString().isNullOrEmpty()) {
                    // DisplayImage(imageUri = uri)
                }
                Box {
                    Image(
                        painter = rememberAsyncImagePainter(uri),
                        contentDescription = "Selected Product Image",
                        modifier = Modifier
                            .size(150.dp)
                            .clip(RoundedCornerShape(8.dp))
                            .border(2.dp, Color.Gray, RoundedCornerShape(8.dp)),
                        contentScale = ContentScale.Crop // Adjust the scaling to fit the size of the container
                    )
                    // Position the delete button on the bottom right of the image
                    IconButton(
                        onClick = {
                            // Remove the image from the list based on index
                            galleryImageUris.removeAt(index)
                        },
                        modifier = Modifier
                            .align(Alignment.BottomEnd)
                            .size(24.dp)
                            .background(
                                color = Color.Black.copy(alpha = 0.5f),
                                shape = CircleShape
                            )
                    ) {
                        Icon(
                            imageVector = Icons.Default.Delete,
                            contentDescription = "Delete Image",
                            tint = Color.White
                        )
                    }
                }
                Spacer(modifier = Modifier.width(8.dp))
            }

            // Button to add new images
            Box(
                modifier = Modifier
                    .size(150.dp)
                    .padding(16.dp)
                    .background(PalmLeaf, shape = RoundedCornerShape(10.dp))
                    .clickable {
                        val intent = Intent(Intent.ACTION_PICK)
                        intent.type = "image/*"
                        pickImageLauncher.launch(intent)
                    },
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = Icons.Default.Add,
                    contentDescription = "Add Image",
                    tint = Color.White,
                    modifier = Modifier.size(24.dp)
                )
            }
        }
    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProductDescription() {
//    var description by remember { mutableStateOf("") }
//    val hashtags = remember { mutableStateListOf<String>() }
    var hashtagText by remember { mutableStateOf("") }

    Column(modifier = Modifier
        .fillMaxWidth()
        .padding(16.dp)) {
        Text(
            text = "Product description",
            fontFamily = JostFontFamily,
            fontWeight = FontWeight.Bold,
            fontSize = 16.sp,
            color = Color.Black,

            )
        OutlinedTextField(
            value = productDescription.value,
            onValueChange = { productDescription.value = it },
            label = { Text("Write a product description.") },
            modifier = Modifier
                .fillMaxWidth(),
//                .padding(8.dp),
            keyboardOptions = KeyboardOptions.Default.copy(imeAction = ImeAction.Done),
            textStyle = TextStyle(fontSize = 16.sp, fontFamily = JostFontFamily),
            singleLine = false, // Add this line if you want the TextField to be single line
            colors = TextFieldDefaults.outlinedTextFieldColors(
                containerColor = Color(0xFFF0F0F0), // Apply the background color here
                unfocusedBorderColor = Color.Transparent,
                focusedBorderColor = Color.Transparent,
                cursorColor = Color.Black
            ),
            shape = RoundedCornerShape(4.dp)
        )

    }
}

@OptIn(ExperimentalComposeUiApi::class, ExperimentalMaterial3Api::class)
@Composable
fun HashtagAddButton(onAdd: () -> Unit, text: String, onTextChange: (String) -> Unit) {
    val keyboardController = LocalSoftwareKeyboardController.current

    // Define the colors for the TextField
    val textFieldColors = TextFieldDefaults.textFieldColors(
        containerColor = Color.Transparent, // Background color is set to transparent
        unfocusedIndicatorColor = Color.Transparent, // This will remove the underline
        focusedIndicatorColor = Color.Transparent // This will remove the underline when the TextField is focused
    )

    Box(
        contentAlignment = Alignment.CenterStart,
        modifier = Modifier
            .padding(12.dp)
            .background(
                PalmLeaf,
                RoundedCornerShape(16.dp)
            )  // Set the background color of the Box, not the TextField

    ) {
        Row(

        ) {
            TextField(
                value = text,
                onValueChange = onTextChange,
                placeholder = { Text("#hashtags", fontSize = 12.sp, color = Color.White) },
                textStyle = TextStyle(fontSize = 12.sp),
                colors = textFieldColors,
                keyboardOptions = KeyboardOptions.Default.copy(imeAction = ImeAction.Done),
                keyboardActions = KeyboardActions(onDone = {
                    onAdd()
                    keyboardController?.hide()
                }),
                modifier = Modifier
                    .background(Color.Transparent)
                    .width(100.dp)

                // Set a fixed width for the TextField
            )
            IconButton(onClick = {
                onAdd()
                keyboardController?.hide()
            }) {
                Icon(Icons.Default.Add, contentDescription = "Add Hashtag", tint = Color.White)
            }
        }
    }
}

@Composable
fun HashtagChip(text: String) {
    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier
            .wrapContentWidth()
            .border(2.dp, Color.Green, RoundedCornerShape(16.dp)) // Set the border color to green
            .background(
                Color.Transparent,
                RoundedCornerShape(16.dp)
            ) // Set the background to transparent
            .padding(16.dp)
    ) {
        Text(text, fontSize = 12.sp, color = PalmLeaf)
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Marketprice() {
//    var marketPrice by remember { mutableStateOf("") }

    Column(modifier = Modifier
        .fillMaxWidth()
        .padding(16.dp)) {
        Text(
            text = "Market price",
            fontFamily = JostFontFamily,
            fontWeight = FontWeight.Bold,
            fontSize = 16.sp,
            color = Color.Black,
//            modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp)
        )
        OutlinedTextField(
            value = marketPrice.value,
            onValueChange = { marketPrice.value = it },
            label = { Text("Enter market price") },
            modifier = Modifier
                .fillMaxWidth()
                .padding(0.dp),
//            keyboardOptions = KeyboardOptions.Default.copy(imeAction = ImeAction.Done),
            keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Number),
            textStyle = TextStyle(fontSize = 16.sp, fontFamily = JostFontFamily),
            singleLine = true, // Add this line if you want the TextField to be single line
            colors = TextFieldDefaults.outlinedTextFieldColors(
                containerColor = Color(0xFFF0F0F0), // Apply the background color here
                unfocusedBorderColor = Color.Transparent,
                focusedBorderColor = Color.Transparent,
                cursorColor = Color.Black
            ),
            shape = RoundedCornerShape(4.dp) // Apply rounded shape here
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Voucher() {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 8.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        OutlinedTextField(
            value = voucherCode.value,
            onValueChange = { voucherCode.value = it },
            label = { Text("Voucher") },
            modifier = Modifier
                .weight(1f)
                .padding(end = 8.dp),
            textStyle = TextStyle(fontSize = 16.sp, fontFamily = JostFontFamily),
            singleLine = true, // Add this line if you want the TextField to be single line
            colors = TextFieldDefaults.outlinedTextFieldColors(
                containerColor = Color(0xFFF0F0F0), // Apply the background color here
                unfocusedBorderColor = Color.Transparent,
                focusedBorderColor = Color.Transparent,
                cursorColor = Color.Black
            ),
            shape = RoundedCornerShape(4.dp) // Apply rounded shape here
        )

        OutlinedTextField(
            value = discountPercent.value,
            onValueChange = { discountPercent.value = it },
            label = { Text("Discount (%)") },
            modifier = Modifier
                .weight(1f)
                .padding(start = 8.dp),
            keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Number),
            textStyle = TextStyle(fontSize = 16.sp, fontFamily = JostFontFamily),
            singleLine = true, // Add this line if you want the TextField to be single line
            colors = TextFieldDefaults.outlinedTextFieldColors(
                containerColor = Color(0xFFF0F0F0), // Apply the background color here
                unfocusedBorderColor = Color.Transparent,
                focusedBorderColor = Color.Transparent,
                cursorColor = Color.Black
            ),
            shape = RoundedCornerShape(4.dp) // Apply rounded shape here

        )
    }
}

@Composable
fun QuantityInput() {
    Column(modifier = Modifier
        .fillMaxWidth()
        .padding(16.dp)
    ) {
        Text(
            text = "Quantity",
            fontFamily = JostFontFamily,
            fontWeight = FontWeight.Bold,
            fontSize = 16.sp,
            color = Color.Black
        )
        OutlinedTextField(
            value = productQuantity.value,
            onValueChange = { productQuantity.value = it },
            label = { Text("Enter quantity") },
            modifier = Modifier.fillMaxWidth(),
            keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Number),
            textStyle = TextStyle(fontSize = 16.sp, fontFamily = JostFontFamily),
            singleLine = true
        )
    }
}


@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun ExpirationDate() {
//    var pickedDate by remember { mutableStateOf(LocalDate.now()) }
    val context = LocalContext.current // Use LocalContext.current for context

    val formattedDate by remember {
        derivedStateOf { DateTimeFormatter.ofPattern("MM/dd/yyyy").format(pickedDate.value) }
    }

    val dateDialogState = rememberMaterialDialogState()

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 8.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Button(onClick = { dateDialogState.show() },
            colors = ButtonDefaults.buttonColors(containerColor = PalmLeaf)
        ) {
            Text(text = "Expiration Date")
        }
        Text(text = formattedDate)

        MaterialDialog(
            dialogState = dateDialogState,
            buttons = {
                positiveButton("Ok") {
                    Toast.makeText(context, "Date Picked: $formattedDate", Toast.LENGTH_LONG).show()
                }
                negativeButton("Cancel")
            }
        ) {
            datepicker(initialDate = LocalDate.now(), title = "Pick a date") {
                pickedDate.value = it
                stringDate.value = formattedDate
            }
        }
    }
}



@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddButton(sellerId: String, storeId: String, productId: String? = null) {
    Log.d("AddButton", "Button clicked. Product ID: $productId, Seller ID: $sellerId, Store ID: $storeId")


    var flag = false
    var buttonText = "Update Product"

    if (productId == "null"){
        flag = true
        buttonText = "Add Product"
        // Log the flag value
        Log.d("AddButton", "Flag value: $flag, Product ID: $productId")
    }

    Button(
        onClick = {
            Log.d("AddButton", "Button clicked. Product ID: $productId, Flag: $flag")

            val product = Product(
                productId ?: "",
                sellerId,
                storeId,
                productName = productName.value,
                quantity = productQuantity.value.toIntOrNull() ?: 0,
                price = marketPrice.value.toDoubleOrNull() ?: 0.00,
                sellingPrice = marketPrice.value.toDoubleOrNull() ?: 0.00,
                discount = discountPercent.value.toDoubleOrNull() ?: 0.00,
                voucherCode = voucherCode.value,
                categories = listOf(selectedCategory.value),
                thumbnail = imageUri.value.toString(),
                description = productDescription.value,
                expirationDate = stringDate.value,
                isActive = if (productQuantity.value.toIntOrNull() ?: 0 > 0) true else false
            )
            if(flag){
                saveProductToFirestore(sellerId, storeId, product)
            }
            else{
                updateProductToFirestore(sellerId, storeId, product, productId.toString())
            }


        },
        // Other Button properties
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 8.dp),
        colors = ButtonDefaults.buttonColors(containerColor = PalmLeaf)
    ) {
        Text(
            text = buttonText,
            color = Color.White,
            fontSize = 18.sp,
            fontWeight = FontWeight.Bold
        )
    }
}

//Hello
@RequiresApi(Build.VERSION_CODES.O)
@Preview(showBackground = true, heightDp = 1500)
@Composable
fun PreviewAddProducts() {
    Column {
        AddProducts()
    }
}



// ----> FUNCTIONS TO CLOUD FIRESTORE <-----






