package com.example.unshelf.view.authentication
import JostFontFamily
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.unshelf.ui.theme.DeepMossGreen
import com.example.unshelf.ui.theme.PalmLeaf
import com.google.firebase.auth.FirebaseAuth


class ResetPassword : ComponentActivity(){
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ResetPasswordScreen()
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ResetPasswordScreen() {
    var email by remember { mutableStateOf("") }
    val context = LocalContext.current
    var isLoading by remember { mutableStateOf(false) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Reset Password") },
                navigationIcon = {
                    IconButton(onClick = { /* Handle back press */ }) {
                        Icon(Icons.Filled.ArrowBack, contentDescription = "Back")
                    }
                }
            )
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding) // Use the padding provided by Scaffold
                .padding(horizontal = 16.dp), // Add additional horizontal padding if needed
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "Please enter your email. A verification code will be sent to the account's email.",
                color = PalmLeaf,
                fontFamily = JostFontFamily,
                fontWeight = FontWeight.Medium,
                modifier = Modifier.padding(16.dp)
            )

            OutlinedTextField(
                value = email,
                onValueChange = { email = it },
                label = { Text("Email") },
                singleLine = true,
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(16.dp))

            Button(
                onClick = {
                    // Start loading
                    isLoading = true
                    val auth = FirebaseAuth.getInstance()
                    auth.sendPasswordResetEmail(email)
                        .addOnCompleteListener { task ->
                            isLoading = false // Stop loading after operation
                            if (task.isSuccessful) {
                                Toast.makeText(context, "A password reset has been sent. Please check your email.", Toast.LENGTH_LONG).show()
                            } else {
                                Toast.makeText(context, "Failed to send reset email", Toast.LENGTH_LONG).show()
                            }
                        }
                },
                colors = ButtonDefaults.buttonColors(containerColor = PalmLeaf),
                // Existing Button implementation
                enabled = !isLoading // Disable the button when loading
            ) {
                if (isLoading) {
                    // Show loading animation
                    CircularProgressIndicator(
                        color = PalmLeaf,
                        modifier = Modifier.size(24.dp)
                    )
                } else {
                    // Show button text
                    Text("SEND")
                }
            }
        }
    }
}
@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    ResetPasswordScreen()
}
