package com.example.unshelf.view.adminApproval

import JostFontFamily
import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.unshelf.R
import com.example.unshelf.ui.theme.DeepMossGreen
import com.example.unshelf.ui.theme.PalmLeaf
import com.example.unshelf.view.authentication.Seller_Login

class BannedMessage : ComponentActivity(){
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            BannedMessageScreen()
        }
    }
}

@Preview(showBackground = true)
@Composable
fun BannedMessageScreen(){
    val context = LocalContext.current

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
            Spacer(modifier = Modifier.height(16.dp))
            Text(
                text = "Banned",
                modifier = Modifier.padding(start = 8.dp),
                color = DeepMossGreen,
                fontSize = 36.sp,
                fontFamily = JostFontFamily,
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Center
            )
            Spacer(modifier = Modifier.height(40.dp))
            Text(
                text = " We regret to inform you that your account has been banned. Please contact support for further assistance at topitops123@gmail.com. Thank you for your understanding.",
                modifier = Modifier.padding(horizontal = 16.dp),
                color = DeepMossGreen,
                fontSize = 18.sp,
                fontFamily = JostFontFamily,
                fontWeight = FontWeight.Medium,
                textAlign = TextAlign.Center
            )
            Spacer(modifier = Modifier.height(69.dp))

        }
        Column(
            modifier = Modifier.fillMaxWidth()
        ) {

            // Button to proceed (optional)
            Button(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 8.dp),
                onClick = {
                    val intent = Intent(context, Seller_Login::class.java)
                    context.startActivity(intent)
                },
                colors = ButtonDefaults.buttonColors(containerColor = PalmLeaf),
            ) {
                Text(text = "Continue")
            }
        }

    }

    // Message and form section


}
