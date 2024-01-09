package com.example.unshelf.view.adminApproval

import JostFontFamily
import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Divider
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.unshelf.model.admin.getAdminVerificationStatus
import com.example.unshelf.model.admin.rejectVerificationToAdmin
import com.example.unshelf.model.admin.sendVerificationToAdmin
import com.example.unshelf.ui.theme.PalmLeaf
import com.example.unshelf.ui.theme.WatermelonRed
import kotlinx.coroutines.launch

class PrivacyPolicy : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            PrivacyPolicyScreen()
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Preview
@Composable
fun PrivacyPolicyScreen() {
    var verificationSent by remember { mutableStateOf(false) }

    // Create a coroutine scope
    val coroutineScope = rememberCoroutineScope()
    val context = LocalContext.current

    Scaffold(
        topBar = {
            // TopAppBar code remains unchanged
        }
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize()
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 16.dp)
            ) {
                Box(
                    modifier = Modifier
                        .weight(1f)
                        .verticalScroll(state = rememberScrollState())
                ) {
                    Text(
                        text = privacyPolicyText,
                        modifier = Modifier.fillMaxWidth()  // Limit text size
                    )
                }
                // Add more text content as needed
                // ...
                // Consider using scrollable components for longer policies
                Divider()
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .align(Alignment.CenterHorizontally)
                        .padding(bottom = 16.dp),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Button(
                        onClick = { /* Handle reject action */
                            coroutineScope.launch {
                                rejectVerificationToAdmin()
                                // Show a success message or handle rejection logic if needed
                            }
                            val intent = Intent(context, RejectedMessage::class.java)
                            context.startActivity(intent)
                                  },
                        colors = ButtonDefaults.buttonColors(containerColor = WatermelonRed),
                    ) {
                        Text(text = "Reject")
                    }
                    Button(
                        onClick = {
                            // Handle accept action
                            if (!verificationSent) {
                                // Call sendVerificationToAdmin within the coroutine scope
                                coroutineScope.launch {
                                    sendVerificationToAdmin()
                                    verificationSent = true
                                    // Show a success message or navigate to the next screen if needed
                                }
                            }
                            // Navigate to VerificationMessage using Intent
                            val intent = Intent(context, VerificationMessage::class.java)
                            context.startActivity(intent)
                        },
                        colors = ButtonDefaults.buttonColors(containerColor = PalmLeaf),
                    ) {
                        Text(text = "Accept")
                    }
                }
            }
        }
    }
}