package com.example.unshelf.view.SellerBottomNav.screens.orders

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.content.ContextCompat
import androidx.lifecycle.lifecycleScope
import com.example.unshelf.R
import com.example.unshelf.view.BuyerBottomNav.ui.MainNavigationActivityBuyer
import com.example.unshelf.view.productView.CartItem
import com.example.unshelf.view.productView.MyComposable
import com.example.unshelf.view.productView.getProducts
import com.example.unshelf.view.productView.storesInfo
import kotlinx.coroutines.launch

class PickUp : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            PickUPCompose()
        }
    }
}

@Preview
@Composable
fun PickUPCompose() {
    Surface(
    modifier = Modifier.fillMaxSize(),
    color = MaterialTheme.colorScheme.background
    ) {
        Column (
            modifier = Modifier
                .fillMaxSize()
                .background(color = Color.Gray) // Optional: Set background color
        ) {
            Menu(
                modifier = Modifier
                    .fillMaxWidth()
            )
            Column(
                modifier = Modifier
                    .background(Color.LightGray)
                    .weight(1f)
                    .fillMaxWidth()
                    .verticalScroll(rememberScrollState())
            ) {

            }
        }
    }
}


@Composable
fun Menu(modifier: Modifier = Modifier) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(60.dp)
            .background(
                color = Color(
                    ContextCompat.getColor(
                        LocalContext.current,
                        R.color.green02
                    )
                )
            )
            .padding(8.dp)
    ) {
        // Back button
        val context = LocalContext.current
        Image(
            painter = painterResource(id = R.drawable.ic_backbtn),
            contentDescription = "Back",
            contentScale = ContentScale.Fit,
            modifier = Modifier
                .padding(start = 16.dp, end = 8.dp)
                .align(Alignment.CenterVertically)
                .clickable {
                    val intent = Intent(context, MainNavigationActivityBuyer::class.java)
                    context.startActivity(intent)
                }
        )

        // Text
        Text(
            text = "Pickup",
            fontSize = 30.sp,
            color = Color.White,
            fontWeight = FontWeight.Bold,
            modifier = Modifier
                .weight(1f) // Take remaining space
                .align(Alignment.CenterVertically)

        )

        // Messages icon
        Image(
            painter = painterResource(id = R.drawable.ic_checkout),
            contentDescription = "Messages",
            contentScale = ContentScale.Fit,
            modifier = Modifier
                .padding(end = 16.dp)
                .align(Alignment.CenterVertically)
        )
    }
}

