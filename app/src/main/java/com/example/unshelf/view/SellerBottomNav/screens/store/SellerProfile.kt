package com.example.unshelf.view.SellerBottomNav.screens.store

import JostFontFamily
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.RequiresApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Event
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.rememberAsyncImagePainter
import com.example.unshelf.model.firestore.seller.dashboardmodel.uploadImage
import com.example.unshelf.ui.theme.PalmLeaf
import com.example.unshelf.ui.theme.WatermelonRed
import com.example.unshelf.view.SellerBottomNav.screens.listings.Category
import com.example.unshelf.view.SellerBottomNav.screens.listings.FocusManagerBox
import com.example.unshelf.view.SellerBottomNav.screens.listings.flag
import com.example.unshelf.view.SellerBottomNav.screens.listings.isImageLoading
import com.vanpra.composematerialdialogs.MaterialDialog
import com.vanpra.composematerialdialogs.datetime.date.datepicker
import com.vanpra.composematerialdialogs.rememberMaterialDialogState
import java.time.LocalDate
import java.time.format.DateTimeFormatter



@RequiresApi(Build.VERSION_CODES.O)
var pickedBirthDate = mutableStateOf(LocalDate.now())
val stringBirthDate = mutableStateOf("")
val selectedGender = mutableStateOf("Other")
var ImageLoading = mutableStateOf(false)
var imageUriSeller = mutableStateOf<Uri?>(null)
var flagPP = mutableStateOf(false)
class SellerProfile : ComponentActivity() {
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            SellerProfileScreen()
        }
    }
}
@RequiresApi(Build.VERSION_CODES.O)
@OptIn(ExperimentalMaterial3Api::class)
@Preview(showBackground = true)
@Composable
fun SellerProfileScreen(){
    FocusManagerBox {
        Scaffold(
            topBar = {
                TopAppBar(
                    title = {
                        Text(
                            "Seller Profile",
                            color = Color.White,
                            fontFamily = JostFontFamily,
                            fontWeight = FontWeight.Medium,
                        )
                    },

                    colors = TopAppBarDefaults.mediumTopAppBarColors(
                        containerColor = PalmLeaf
                    ),

                    navigationIcon = {
                        IconButton(onClick = { }) {
                            Icon(Icons.Default.ArrowBack, "Back", tint = Color.White)
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

                SellerProfilePic()
                Gender()
                Birthdate()
                UpdateProfile()
            }

        }
    }
}

@Composable
fun SellerProfilePic() {
    // State to hold the selected image Uri
//    var imageUri by remember { mutableStateOf<Uri?>(null) }
    LocalContext.current
    val launcher = rememberLauncherForActivityResult(contract = ActivityResultContracts.GetContent()) { uri: Uri? ->
        uri?.let {
            ImageLoading.value = true // Set loading to true when image upload starts
            imageUriSeller.value = null
            uploadImage(it, onSuccess = { downloadUrl ->
                imageUriSeller.value = Uri.parse(downloadUrl)
                ImageLoading.value = false
                flagPP.value = true // Set flag to true as the image is successfully uploaded
            }, onFailure = { exception ->
                Log.e("SellerPP", "Upload failed: ${exception.message}")
                ImageLoading.value = false
            })

        }
    }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
            .background(Color.Transparent), // This is the green background color
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.height(16.dp)) // Spacing from the top

        if (flagPP.value) {
            Image(
                painter = rememberAsyncImagePainter(model = imageUriSeller.value),
                contentDescription = "Selected SellerPP",
                modifier = Modifier
                    .size(200.dp)
                    .clip(CircleShape) // Clip the image to a circle
                    .border(2.dp, PalmLeaf, CircleShape) // Add border to the circle
                    .border(2.dp, Color.Gray, CircleShape)
                    .clickable { launcher.launch("image/*") },
            )

        }

        // Check if an image URI is available and call DisplayImage
        else{
            Box(
                // If no image is selected, show the 'add' icon
                contentAlignment = Alignment.Center,
                modifier = Modifier
                    .size(200.dp) // Consistent circular thumbnail size
                    .clip(CircleShape) // Clip the box to a circle
                    .border(2.dp, PalmLeaf, CircleShape)
                    .clickable { launcher.launch("image/*") } // Open image picker when clicking on the box
            ) {

                if (ImageLoading.value) {
                    // Show CircularProgressIndicator while the image is loading
                    CircularProgressIndicator(color = PalmLeaf)
                } else{
                    Icon(
                        imageVector = Icons.Default.Add,
                        contentDescription = "Add Profile Picture",
                        tint = PalmLeaf,
                        modifier = Modifier.size(24.dp) // Size of the plus icon
                    )
                }

                Log.d("imageUriSeller", "No image URI present, showing 'add' icon")
            }
        }
        Spacer(modifier = Modifier.height(10.dp)) // Spacing between the circle and text

        // Text under the image/thumbnail
        Text(

            text = if (!flagPP.value) "Choose a picture for your product" else "",
            fontFamily = JostFontFamily,
            fontWeight = FontWeight.Light,
            fontSize = 12.sp,
            color = Color.Black,
            modifier = Modifier
                .padding(12.dp)

        )

    }
    Log.d("SellerPP2", "imageUri value: ${imageUriSeller.value}")
    Log.d("SellerPP2", "Flag value: $flagPP.value") // Log the flag value



    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 4.dp),
        horizontalArrangement = Arrangement.Center // Center the button
    ) {
        // Button to remove the selected image
        if ( flagPP.value) {
            Button(
                onClick = {
                    imageUriSeller.value = null
                    flagPP.value = false
                    ImageLoading.value = false
                },
                colors = ButtonDefaults.buttonColors(containerColor = WatermelonRed)
            ) {
                Text("Remove Profile Picture", color = Color.White)
            }
        }
    }

    Spacer(modifier = Modifier.height(10.dp)) // Spacing from the bottom

}

@Composable
fun Gender(){
    var expand by remember { mutableStateOf(false)}
    val gender = listOf("Male", "Female", "Other")
    var indexSelected by remember {
        mutableStateOf(gender.indexOf(selectedGender.value))
    }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
    ) {
        Text(
            text = "Gender",
            fontFamily = JostFontFamily,
            fontWeight = FontWeight.Bold,
            fontSize = 16.sp,
            color = Color.Black
        )
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .background(Color(0xFFF0F0F0), RoundedCornerShape(4.dp))
                .clickable { expand = true }
                .padding(16.dp)
        ){
            Text(
                text = gender[indexSelected],
                fontFamily = JostFontFamily,
                fontSize = 16.sp,
                color = Color.Black
            )
            DropdownMenu(
                expanded = expand,
                onDismissRequest = { expand = false}
            ) {
             gender.forEachIndexed {
                 index, genderCat ->
                 DropdownMenuItem(
                     text = { Text(genderCat) },
                     onClick = {
                         indexSelected = index
                         selectedGender.value = gender[indexSelected]
                         expand = false
                     }
                 )
             }
            }
        }
        

    }
}
@OptIn(ExperimentalMaterial3Api::class)
@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun Birthdate() {
    val context = LocalContext.current
    val dateDialogState = rememberMaterialDialogState()
    var textBirthDate by remember { mutableStateOf(
        DateTimeFormatter.ofPattern("MM/dd/yyyy").format(
            pickedBirthDate.value)) }

    val updateDate = { date: LocalDate ->
        pickedBirthDate.value = date
        textBirthDate = DateTimeFormatter.ofPattern("MM/dd/yyyy").format(date)
        stringBirthDate.value = textBirthDate
        Log.d("Birthdate", "Updated pickedDate: ${pickedBirthDate.value}")
    }
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
    ){
        Text(
            text = "Birthdate",
            fontFamily = JostFontFamily,
            fontWeight = FontWeight.Bold,
            fontSize = 16.sp,
            color = Color.Black
        )
        OutlinedTextField(
            value = textBirthDate,
            onValueChange = { textBirthDate = it },
            readOnly = true, // Make the TextField read-only since we're using the dialog to set the date
            trailingIcon = {
                IconButton(onClick = { dateDialogState.show() }) {
                    Icon(Icons.Default.Event, contentDescription = "Select date")
                }
            },
            placeholder = { Text("Birth Date") },
            modifier = Modifier
                .fillMaxWidth(),

            textStyle = TextStyle(fontSize = 16.sp, fontFamily = JostFontFamily),
            colors = TextFieldDefaults.outlinedTextFieldColors(
                containerColor = Color(0xFFF0F0F0), // Apply the background color here
                unfocusedBorderColor = Color.Transparent,
                focusedBorderColor = Color.Transparent,
                cursorColor = Color.Black
            ),
            shape = RoundedCornerShape(4.dp) // Apply rounded shape here
        )

        MaterialDialog(
            dialogState = dateDialogState,
            buttons = {
                positiveButton("Ok")
                negativeButton("Cancel")
            }
        ) {
            datepicker(initialDate = pickedBirthDate.value, title = "Pick a date") { date ->
                updateDate(date)
            }
        }
    }

}

@Composable
fun UpdateProfile() {
    Button(
        onClick = {},
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        colors = ButtonDefaults.buttonColors(containerColor = PalmLeaf)
    ) {
        Text("Update Profile", color = Color.White)
    }
}