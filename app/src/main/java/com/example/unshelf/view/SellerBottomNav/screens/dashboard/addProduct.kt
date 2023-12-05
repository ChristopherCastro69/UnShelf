package com.example.unshelf.view.SellerBottomNav.screens.dashboard

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
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Add
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
import com.example.unshelf.ui.theme.DeepMossGreen
import com.example.unshelf.ui.theme.PalmLeaf
import com.google.firebase.Firebase
import com.google.firebase.auth.auth
import com.google.firebase.firestore.firestore
import com.vanpra.composematerialdialogs.MaterialDialog
import com.vanpra.composematerialdialogs.datetime.date.datepicker
import com.vanpra.composematerialdialogs.rememberMaterialDialogState
import java.time.LocalDate
import java.time.format.DateTimeFormatter

// First, define your Product data class to match the Firestore structure

var productName = mutableStateOf("")
var selectedCategory = mutableStateOf("Grocery") // Default to the first item in your categories list
var imageUri = mutableStateOf<Uri?>(null)
var galleryImageUris = mutableStateListOf<Uri>()
var productDescription = mutableStateOf("")
var productHashtags = mutableStateListOf<String>()
var marketPrice = mutableStateOf("")
var voucherCode = mutableStateOf("")
var discountPercent = mutableStateOf("")
@RequiresApi(Build.VERSION_CODES.O)
var pickedDate = mutableStateOf(LocalDate.now())

val stringDate = mutableStateOf("")
var sellerId = mutableStateOf("")
var storeId = mutableStateOf("")
var productAdditionSuccess = mutableStateOf(false)


@OptIn(ExperimentalMaterial3Api::class)
@Composable
@RequiresApi(Build.VERSION_CODES.O)
fun AddProducts() {
    LaunchedEffect(key1 = Unit) {
        fetchUserDetails { sId, stId ->
            sellerId.value = sId
            storeId.value = stId
            Log.d("AddProducts", "LaunchedEffect Seller ID: ${sellerId.value}, Store ID: ${storeId.value}")
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
            ProductGallery()
            ProductDescription()
            Marketprice()
            Voucher()
            ExpirationDate()
            // Pass the current value of sellerId and storeId to AddButton
            AddButton(sellerId = sellerId.value, storeId = storeId.value)
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
        imageUri.value = uri
    }

    Column(
        modifier = Modifier
            .fillMaxWidth()
//            .padding(16.dp)
            .background(Color.Transparent), // This is the green background color
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.height(30.dp)) // Spacing from the top

        imageUri.value?.let {
            Image(
                painter = rememberAsyncImagePainter(model = it),
                contentDescription = "Selected Thumbnail",
                modifier = Modifier
                    .fillMaxWidth()
                    .size(250.dp) // Size of the circle
                    .clip(RectangleShape)
                    .border(2.dp, DeepMossGreen, RectangleShape)
                    .border(2.dp, Color.Gray, RectangleShape)
                    .clickable { launcher.launch("image/*") } // Change thumbnail on click
            )
        } ?: Box(
            // If no image is selected, show the 'add' icon
            contentAlignment = Alignment.Center,
            modifier = Modifier
                .fillMaxWidth()
                .height(250.dp)
//                .fillMaxHeight()
//                .size(250.dp) // Size of the circle
                .background(DeepMossGreen, RectangleShape) // White circle
                .border(2.dp, Color.Gray, RectangleShape)
                .clickable { launcher.launch("image/*") } // Open image picker when clicking on the box
        ) {
            Icon(
                imageVector = Icons.Default.Add,
                contentDescription = "Add Thumbnail",
                tint = Color.White,
                modifier = Modifier.size(24.dp) // Size of the plus icon
            )
        }

        Spacer(modifier = Modifier.height(10.dp)) // Spacing between the circle and text

        // Text under the image/thumbnail
        Text(
            text = if (imageUri.value == null) "Choose a thumbnail for your product" else "Tap to change thumbnail",
            fontFamily = JostFontFamily,
            fontWeight = FontWeight.Light,
            fontSize = 12.sp,
            color = Color.Black,
            modifier = Modifier.padding(12.dp)
        )

        // Button to remove the selected image
        if (imageUri.value != null) {
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

    val imageUris = remember { mutableStateListOf<Uri>() }
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
//                .padding(16.dp),
        ) {
            galleryImageUris.forEach { uri ->
                Image(
                    painter = rememberAsyncImagePainter(uri),
                    contentDescription = "Selected Product Image",
                    modifier = Modifier
//                        .size(130.dp)
//                        .padding(end = 8.dp)
//                        .border(2.dp, Color.Gray, RoundedCornerShape(8.dp))
                        .fillMaxWidth()  // Makes the image stretch to fill the width of its container
                        .aspectRatio(1f) // Maintains the aspect ratio of the image

                )



            }

            // Button to add new images
            Box(
                modifier = Modifier
                    .size(150.dp)
                    .height(100.dp)
                    .padding(16.dp)
                    .background(DeepMossGreen,  shape = RoundedCornerShape(10.dp))
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

        Row(
            modifier = Modifier
                .wrapContentWidth()
                .background(Color.Transparent),
//                .padding(8.dp),

//            horizontalArrangement = Arrangement.spacedBy(8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Hashtags list
            productHashtags.forEach { hashtag ->
                HashtagChip(hashtag)
            }
            // Hashtag add button
            HashtagAddButton(onAdd = {
                if (hashtagText.isNotBlank()) {
                    productHashtags.add(hashtagText)
                    hashtagText = ""
                }
            }, text = hashtagText, onTextChange = { hashtagText = it })
        }
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
            .background(Color.Transparent, RoundedCornerShape(16.dp)) // Set the background to transparent
            .padding(16.dp)
    ) {
        Text(text, fontSize = 12.sp, color = Color(0xFF555555))
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
fun AddButton(sellerId: String, storeId: String) {

    Button(
        onClick = {
            Log.d("AddButton", "Button clicked with Seller ID: $sellerId, Store ID: $storeId")
            // Call the function to save the product to Firestore
            if (sellerId.isNotBlank() && storeId.isNotBlank()){
                // Convert global states to match the types in the Product class
                val product = Product(
                    productName = productName.value,
                    categories = listOf(selectedCategory.value), // Assuming a single category for simplicity
                    thumbnail = imageUri.value.toString(), // Convert Uri to String
                    gallery = galleryImageUris.joinToString(",") { it.toString() }, // Convert List<Uri> to comma-separated String
                    description = productDescription.value,
                    marketPrice = marketPrice.value.toLongOrNull() ?: 0L,
                    hashtags = productHashtags.toList(),
                    expirationDate = stringDate.value,
                    discount = discountPercent.value.toLongOrNull() ?: 0L
                )

                saveProductToFirestore(sellerId, storeId, product)
            }
            else {
                Log.d("AddButton", "Seller ID or Store ID is missing")
            }
        },
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 8.dp),
        colors = ButtonDefaults.buttonColors(containerColor = PalmLeaf)
    ) {
        Text(
            text = "Add Product",
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

fun saveProductToFirestore(sellerId: String, storeId: String, product: Product) {

    val db = Firebase.firestore
    val productRef = db.collection("sellers").document(sellerId)
        .collection("store").document(storeId)
        .collection("products")

    productRef.add(product)
        .addOnSuccessListener {
            Log.d("Firestore", "Product added successfully")
            productAdditionSuccess.value = true // Set success state to true
        }
        .addOnFailureListener {
            Log.e("Firestore", "Error adding product", it)
            productAdditionSuccess.value = false // Optionally handle failure
        }
}


fun fetchUserDetails(onComplete: (String, String) -> Unit) {
    val userId = Firebase.auth.currentUser?.uid ?: return

    // Assuming 'userId' is your 'sellerId'
    val sellerId = userId

    // Query Firestore to get the store ID
    Firebase.firestore.collection("sellers").document(sellerId)
        .collection("store").get()
        .addOnSuccessListener { querySnapshot ->
            // Assuming you need the first store's ID
            val storeId = querySnapshot.documents.firstOrNull()?.id ?: ""
            Log.d("UserDetails", "Seller ID: $sellerId, Store ID: $storeId")

            // Return the seller ID and store ID
            onComplete(sellerId, storeId)
        }
        .addOnFailureListener {
            Log.e("Firestore", "Error fetching store details", it)
        }
}





