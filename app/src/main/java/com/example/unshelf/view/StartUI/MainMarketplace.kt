package com.example.unshelf.view.StartUI

import MarketplaceContent
import android.content.Intent
import android.os.Bundle
import android.os.PersistableBundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.unshelf.R
import com.example.unshelf.ui.theme.MiddleGreenYellow
import com.example.unshelf.ui.theme.PalmLeaf
import com.example.unshelf.view.authentication.CustomerRegister
import com.example.unshelf.view.authentication.Customer_Login
import com.example.unshelf.view.product.cart

class MainMarketplace: ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent{
            MainMarketplaceContent()
        }
    }
}
@Preview
@Composable
fun MainMarketplaceContent(){
    val context = LocalContext.current
    Scaffold(
        topBar = {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(height = 50.dp)
                    .background(color = PalmLeaf)
                    .padding(horizontal = 10.dp)
            ) {
                Row(modifier = Modifier
                    .clip(RoundedCornerShape(10.dp))
                    .weight(1F)
                    .fillMaxHeight()
                    .padding(vertical = 10.dp)
                    .background(Color.White, RoundedCornerShape(10.dp))
                    .align(Alignment.CenterVertically)
                ) {
                    Icon(
                        imageVector = Icons.Filled.Search,
                        contentDescription = "Search button",
                        tint = PalmLeaf,
                        modifier = Modifier
                            .size(30.dp)
                            .padding(5.dp)
                            .align(Alignment.CenterVertically)
                    )
                    Text(
                        modifier = Modifier
                            .align(Alignment.CenterVertically),
                        text = "Search",
                        color = MiddleGreenYellow,
                        fontSize = 16.sp
                    )
                }
            }
        },
        bottomBar = {
            initialMarketNav()
        }
    ) {innerPadding ->
        Box(modifier = androidx.compose.ui.Modifier.padding(innerPadding)) {
            MarketplaceContent(false)
        }
    }

}

@Composable
fun initialMarketNav() {
    val context = LocalContext.current
    Row {
        Button(onClick = {
            val intent = Intent(context, CustomerRegister::class.java)
            context.startActivity(intent)
        },
            shape = RoundedCornerShape(13.dp),
            colors = ButtonDefaults.buttonColors(containerColor = PalmLeaf),
            modifier = Modifier
                .weight(1F)
                .padding(horizontal = 8.dp)
        ){
            Text(
                text = "Sign up",
                color = Color.White,
                style = TextStyle(
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Black,
                    textAlign = TextAlign.Center,
                ),
                modifier = Modifier
                    .wrapContentHeight(align = Alignment.CenterVertically)
            )

        }
        Button(onClick = {
            val intent = Intent(context, Customer_Login::class.java)
            context.startActivity(intent)
        },
            shape = RoundedCornerShape(13.dp),
            colors = ButtonDefaults.buttonColors(containerColor = PalmLeaf),
            modifier = Modifier
                .weight(1F)
                .padding(horizontal = 8.dp)
        ){
            Text(
                text = "Log in",
                color = Color.White,
                style = TextStyle(
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Black,
                    textAlign = TextAlign.Center,
                ),
                modifier = Modifier
                    .wrapContentHeight(align = Alignment.CenterVertically)
            )

        }
    }
}