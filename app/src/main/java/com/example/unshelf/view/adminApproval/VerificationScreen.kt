package com.example.unshelf.view.adminApproval

import JostFontFamily
import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.content.ContextCompat.startActivity
import com.example.unshelf.R
import com.example.unshelf.controller.OrderController
import com.example.unshelf.controller.seller.ui.MainNavigationActivitySeller
import com.example.unshelf.model.admin.getAdminVerificationStatus
import com.example.unshelf.ui.theme.DeepMossGreen
import com.example.unshelf.ui.theme.PalmLeaf
import kotlinx.coroutines.launch


class VerificationS : ComponentActivity(){
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        OrderController.orderList.value = emptyList()
        setContent {
            VerificationScreen()
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun VerificationScreen(){
    val context = LocalContext.current
    var adminVerificationStatus by remember { mutableStateOf("") }

    // Create a coroutine scope
    val coroutineScope = rememberCoroutineScope()

    // Call getAdminVerificationStatus() within the coroutine scope
    LaunchedEffect(Unit) {
        coroutineScope.launch {
            adminVerificationStatus = getAdminVerificationStatus()
        }
    }


    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 24.dp, vertical = 32.dp),
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        // Welcome section
        Column(
            modifier = Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // Welcome logo
            Image(
                modifier = Modifier.size(150.dp),
                painter = painterResource(id = R.drawable.logo),
                contentDescription = "Welcome logo"
            )
            Text(
                text = "Welcome Seller!",
                modifier = Modifier.padding(start = 8.dp),
                color = DeepMossGreen,
                fontSize = 32.sp,
                fontFamily = JostFontFamily,
                fontWeight = FontWeight.Bold
            )
        }

        // Message and form section
        Column(
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(
                text = "Thank you for using our app. Click the button to proceed",
                color = Color.Gray,
                fontSize = 16.sp
            )

            // Button to proceed (optional)
            Button(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 8.dp),
                onClick = {
                    when (adminVerificationStatus) {
                        "Requires Action" -> {
                            val intent = Intent(context, PrivacyPolicy::class.java)
                            context.startActivity(intent)
                        }
                        "Pending" -> {
                            val intent = Intent(context, VerificationMessage::class.java)
                            context.startActivity(intent)
                        }
                        "Approved" -> {
                            val intent = Intent(context, MainNavigationActivitySeller::class.java)
                            context.startActivity(intent)
                        }
                        "Rejected"->{
                            val intent = Intent(context, RejectedMessage::class.java)
                            context.startActivity(intent)
                        }
                        else -> {
                            // Handle other cases if needed
                        }
                    }
                },
                colors = ButtonDefaults.buttonColors(containerColor = PalmLeaf),
            ) {
                when (adminVerificationStatus) {
                    "loading" -> CircularProgressIndicator(color = Color.White)
                    else -> Text(text = "Get Started", color = Color.White)
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    VerificationScreen()
}


