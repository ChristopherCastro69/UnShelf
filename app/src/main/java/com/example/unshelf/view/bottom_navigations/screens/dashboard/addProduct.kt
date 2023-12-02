package com.example.unshelf.view.bottom_navigations.screens.dashboard

import JostFontFamily
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
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
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
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
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.unshelf.R
import com.example.unshelf.ui.theme.DeepMossGreen

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddProducts() {

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text("Product Details",
                        color = Color.White,
                        fontFamily = JostFontFamily,
                        fontWeight = FontWeight.Medium,
                    ) },

                colors = TopAppBarDefaults.mediumTopAppBarColors(
                    containerColor = Color(0xFF4CAF50)
                ),

                navigationIcon = {
                    IconButton(onClick = { /* TODO: Handle back action */ }) {
//
                        Icon(Icons.Filled.ArrowBack, "Menu", tint = Color.White)
                    }
                },
                actions = {

                }
            )
        }
    ) { innerPadding ->
        Column(modifier = Modifier.padding(innerPadding)) {
            Details()
            Thumbnail()
            ProductGallery()
            ProductDescription()
            Marketprice()
            Voucher()
            NextButton()
            }

        }
    }

@Composable
fun Details(){
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        verticalAlignment = Alignment.CenterVertically

    ){
        Image(
            painter = painterResource(id = R.drawable.ic_progress), // Replace with the actual profile picture resource ID
            contentDescription = "Profile Picture",
            modifier = Modifier
                .size(50.dp) // Adjust size as needed
                .clip(CircleShape)
                .background(DeepMossGreen) // Replace with the color of the profile picture background if needed
        )
        Spacer(modifier = Modifier.width(24.dp))
        Column(
            modifier = Modifier.weight(1f)
        ) {
            Text(
                text = "Add product details",
                fontWeight = FontWeight.Bold,
                fontSize = 20.sp,
                color = DeepMossGreen
            )
            Text(
                text = "Enter your product details below",
                fontSize = 14.sp,
                color = DeepMossGreen
            )

        }
    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Thumbnail() {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
            .background(DeepMossGreen), // This is the green background color
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.height(50.dp)) // Spacing from the top

        // Circle with plus icon, acting as a button for thumbnail selection
        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier
                .size(50.dp) // Size of the circle
                .background(Color.White, CircleShape) // White circle
                .clickable { /* TODO: Implement thumbnail selection */ }
        ) {
            Icon(
                imageVector = Icons.Default.Add,
                contentDescription = "Add Thumbnail",
                tint = Color.Black,
                modifier = Modifier.size(20.dp) // Size of the plus icon
            )
        }

        Spacer(modifier = Modifier.height(16.dp)) // Spacing between the circle and text

        // Text under the circle
        Text(
            text = "Choose a thumbnail for your product",
            fontFamily = JostFontFamily,
            fontWeight = FontWeight.Light,
            fontSize = 12.sp,
            color = Color.White,
            modifier = Modifier.padding(12.dp)
        )

        Spacer(modifier = Modifier.height(16.dp)) // Spacing from the bottom
    }
}


@Composable
fun ProductGallery() {
    Column(modifier = Modifier.fillMaxWidth()) {
        Text(
            text = "Product gallery",
            fontFamily = JostFontFamily,
            fontWeight = FontWeight.Bold,
            fontSize = 16.sp,
            color = Color.Black,
            modifier = Modifier.padding(16.dp)
        )

        // Horizontal scrollable row for product images
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .horizontalScroll(rememberScrollState())
                .padding(16.dp)
        ) {
            // Placeholder for images in the gallery
            // Replace with actual images from your product data
            repeat(3) { index ->
                Image(
                    painter = painterResource(id = R.drawable.fruit), // Replace with actual image resource
                    contentDescription = "Product Image $index",
                    modifier = Modifier
                        .size(130.dp)
                        .padding(end = 8.dp)
                        .border(2.dp, Color.Gray, RoundedCornerShape(8.dp))
                )
            }

            // Button to add new images
            Box(
                modifier = Modifier
                    .size(100.dp)
                    .padding(end = 8.dp)
                    .border(2.dp, Color.Gray, RoundedCornerShape(8.dp))
                    .background(Color.White)
                    .clickable { /* TODO: Handle add image action */ },
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = Icons.Default.Add,
                    contentDescription = "Add Image",
                    tint = Color.Black,
                    modifier = Modifier.size(24.dp)
                )
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProductDescription() {
    var description by remember { mutableStateOf("") }
    val hashtags = remember { mutableStateListOf<String>() }
    var hashtagText by remember { mutableStateOf("") }

    Column(modifier = Modifier.fillMaxWidth()) {
        Text(
            text = "Product description",
            fontFamily = JostFontFamily,
            fontWeight = FontWeight.Bold,
            fontSize = 16.sp,
            color = Color.Black,
            modifier = Modifier.padding(8.dp)
        )
        OutlinedTextField(
            value = description,
            onValueChange = { description = it },
            label = { Text("Write a product description.") },
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            keyboardOptions = KeyboardOptions.Default.copy(imeAction = androidx.compose.ui.text.input.ImeAction.Done),
            textStyle = TextStyle(fontSize = 16.sp, fontFamily = JostFontFamily),
//            colors = TextFieldDefaults.textFieldColors(backgroundColor = Color(0xFFF0F0F0))
        )

        Row(
            modifier = Modifier
                .wrapContentWidth()
                .background(Color.Transparent)
                .padding(horizontal = 8.dp),

            horizontalArrangement = Arrangement.spacedBy(8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Hashtags list
            for (hashtag in hashtags) {
                HashtagChip(hashtag)
            }

            // Hashtag add button
            HashtagAddButton(onAdd = {
                if (hashtagText.isNotBlank()) {
                    hashtags.add(hashtagText)
                    hashtagText = "" // Reset input field after adding
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
        containerColor = Color.Transparent,  // Background color is set to transparent

    )

    Box(
        contentAlignment = Alignment.CenterStart,
        modifier = Modifier
            .background(DeepMossGreen, RoundedCornerShape(16.dp))  // Set the background color of the Box, not the TextField
            .padding(horizontal = 8.dp, vertical = 4.dp)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .wrapContentWidth()
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
                    .padding(end = 8.dp)
                    .width(150.dp)  // Set a fixed width for the TextField
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
            .background(Color(0xFFE0E0E0), RoundedCornerShape(16.dp))
            .padding(horizontal = 8.dp, vertical = 4.dp)
    ) {
        Text(text, fontSize = 12.sp, color = Color(0xFF555555))
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Marketprice() {
    var marketPrice by remember { mutableStateOf("") }

    Column(modifier = Modifier.fillMaxWidth()) {
        Text(
            text = "Market price",
            fontFamily = JostFontFamily,
            fontWeight = FontWeight.Bold,
            fontSize = 16.sp,
            color = Color.Black,
            modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp)
        )
        OutlinedTextField(
            value = marketPrice,
            onValueChange = { marketPrice = it },
            label = { Text("Enter market price") },
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp),
            keyboardOptions = KeyboardOptions.Default.copy(imeAction = ImeAction.Done),
            textStyle = TextStyle(fontSize = 16.sp, fontFamily = JostFontFamily),
            colors = TextFieldDefaults.textFieldColors(
                containerColor = Color.Transparent,
                cursorColor = Color.Black,
                focusedIndicatorColor = Color.Gray, // You can change this color to match your design
                unfocusedIndicatorColor = Color.Gray // You can change this color to match your design
            ),
            singleLine = true
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Voucher() {
    var voucherCode by remember { mutableStateOf("") }
    var discountPercent by remember { mutableStateOf("10%") } // Initial discount value

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 8.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        OutlinedTextField(
            value = voucherCode,
            onValueChange = { voucherCode = it },
            label = { Text("Voucher") },
            modifier = Modifier
                .weight(1f)
                .padding(end = 8.dp), // Add padding to the right
            singleLine = true
        )

        OutlinedTextField(
            value = discountPercent,
            onValueChange = { discountPercent = it },
            label = { Text("Discount") },
            trailingIcon = {
                Icon(
                    imageVector = Icons.Filled.ArrowDropDown,
                    contentDescription = "Dropdown",
                    modifier = Modifier.clickable { /* Open discount selector */ }
                )
            },
            readOnly = true, // Make this field read-only if it's a dropdown
            modifier = Modifier
                .weight(1f)
                .padding(start = 8.dp), // Add padding to the left
            singleLine = true
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NextButton() {
    Button(
        onClick = { /* TODO: Implement next button action */ },
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 8.dp),
        colors = ButtonDefaults.buttonColors(containerColor = DeepMossGreen)
    ) {
        Text(
            text = "Next",
            color = Color.White,
            fontSize = 18.sp,
            fontWeight = FontWeight.Bold
        )
    }
}
@Preview(showBackground = true, heightDp = 1250)
@Composable
fun PreviewAddProducts() {
    Column {
        AddProducts()
    }
}