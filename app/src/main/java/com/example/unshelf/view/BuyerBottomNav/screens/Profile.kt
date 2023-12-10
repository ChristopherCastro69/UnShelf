package com.example.unshelf.view.BuyerBottomNav.screens

import JostFontFamily
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowForwardIos
import androidx.compose.material.icons.filled.Assignment
import androidx.compose.material.icons.filled.DeliveryDining
import androidx.compose.material.icons.filled.Discount
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.ExitToApp
import androidx.compose.material.icons.filled.Language
import androidx.compose.material.icons.filled.LocalOffer
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.filled.ShoppingBag
import androidx.compose.material.icons.filled.Wallet
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.paint
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontVariation.width
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.content.ContextCompat
import com.example.unshelf.R
import com.example.unshelf.ui.theme.DeepMossGreen
import com.example.unshelf.view.SellerBottomNav.screens.store.ProfileOptionItem
import com.example.unshelf.view.SellerBottomNav.screens.store.Store

@Composable
fun Profile() {
    Column(
        modifier = Modifier
            .fillMaxSize(),
//            .verticalScroll(rememberScrollState()),
        horizontalAlignment = Alignment.CenterHorizontally,

    ) {
        BuyerProfileDetails()
        LazyColumn (
            Modifier.padding(bottom = 20.dp)
        ){
            item {
                val strings_1 = listOf("Addresses", "Subscriptions", "Referals", "Vouchers")
                val strings_2 = listOf("Help Center", "Settings", "Customer Support", "Log out")
                strings_1.forEach { name ->
                    BuyerSettings(name)
                }
                Spacer(modifier = Modifier.height(10.dp))
                Text(
                    text = "General",
                    fontWeight = FontWeight.Bold,
                    fontSize = 16.sp,
                    color = Color(ContextCompat.getColor(LocalContext.current, R.color.green07))
                )
                Spacer(modifier = Modifier.height(10.dp))
                strings_2.forEach { name ->
                    BuyerSettings(name)
                }
            }
        }
    }
}


@Preview
@Composable
fun BuyerProfileDetails () {
    val buyerImages = listOf(R.drawable.buyer_receipt_ic,R.drawable.buyer_payment_ic,R.drawable.buyer_location_ic,R.drawable.buyer_favorites_ic)
    val buyerNames = listOf("Activity", "Payment", "Order Tracking", "Favorites")

    Column (
        modifier = Modifier
            .fillMaxWidth()
            .height(260.dp)
            .paint(
                // Replace with your image id
                painterResource(id = R.drawable.buyer_profile_background),
                contentScale = ContentScale.FillBounds
            ),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Row (
            horizontalArrangement = Arrangement.Start,
        ){
            Row(
                modifier = Modifier,
            ) {
                Spacer(Modifier.weight(0.2f))
                Image(
                    painter = painterResource(id = R.drawable.buyer_profile_pic),
                    contentDescription = "Back",
                    modifier = Modifier
                        .height(120.dp)
                        .width(120.dp)
                        .padding(10.dp)
                        .align(Alignment.CenterVertically)
                )
                Column(
                    Modifier.align(Alignment.CenterVertically)
                ) {
                    Text(
                        text = "John Doe",
                        color = Color.White,
                        fontWeight = FontWeight.Bold,
                        fontSize = 20.sp
                    )
                    Row (
                        modifier = Modifier
                            .padding(top = 6.dp)
                            .wrapContentHeight()
                            .paint(
                                // Replace with your image id
                                painterResource(id = R.drawable.food_hero_badge),
                                contentScale = ContentScale.FillBounds
                            )
                    ) {

                        Text(
                            text = "Food Hero Badge",
                            modifier = Modifier.padding(
                                top = 6.dp,
                                bottom = 12.dp,
                                start = 10.dp,
                                end = 10.dp
                            )
                        )
                        Image(
                            painterResource(id = R.drawable.food_hero_ic),
                            contentDescription = "Foods Hero Icon",
                            modifier = Modifier.align(Alignment.CenterVertically).padding(end = 8.dp, bottom = 5.dp)
                        )
                    }

                }
                Spacer(Modifier.weight(0.8f))
            }

        }
        Image(
            painterResource(id = R.drawable.buyer_profile_line),
            contentDescription = "Line Image",
            modifier = Modifier.fillMaxWidth(0.8f)
        )
        Row (horizontalArrangement = Arrangement.spacedBy(20.dp)) {
            for(i in 0..3) {
                Column (
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier.padding(top = 20.dp)
                ) {
                    Image(
                        painterResource(id = buyerImages[i]),
                        contentDescription = "Buyer Icon",
                        modifier = Modifier
                            .width(35.dp)
                            .height(35.dp)
                    )
                    Text (
                        text = buyerNames[i],
                        color = Color.White
                    )
                }
            }
        }
    }
}



@Composable
fun BuyerSettings(option: String) {
    Row(
        modifier = Modifier
            .fillMaxWidth(0.8f)
            .clickable { /* Handle click */ }
            .padding(vertical = 16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = option,
            modifier = Modifier.weight(1f),
            fontSize = 18.sp,
            color = DeepMossGreen
        )
        Icon(
            imageVector = Icons.Default.ArrowForwardIos,
            contentDescription = "Go to $option",
            modifier = Modifier.size(20.dp),
            tint = DeepMossGreen
        )
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewProfileScreen() {
    Column  {
        Profile()
    }
}
