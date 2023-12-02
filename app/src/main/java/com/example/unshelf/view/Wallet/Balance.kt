package com.example.unshelf.view.Wallet

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.Composable

import androidx.compose.foundation.*
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AddCircle
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.*
import com.example.unshelf.R
import com.example.unshelf.ui.theme.DeepMossGreen
import com.example.unshelf.ui.theme.WatermelonRed
import com.example.unshelf.view.Seller.SellerProfile

class Balance: ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            PageInAppBalance(this)
        }
    }
}

@Composable
fun PageInAppBalance(activity: Activity?) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(color = Color.White)
    ) {
        Image(
            painter = painterResource(id = R.drawable.wallet_bg),
            contentDescription = "Vector 6",
            modifier = Modifier
                .fillMaxWidth(),
            contentScale = ContentScale.Crop
        )
        Column {
            Box (
                modifier = Modifier
                    .fillMaxWidth(0.92f)
                    .padding(top = 16.dp)
                    .height(height = 48.dp)
                    .align(Alignment.CenterHorizontally)
            ) {
                Row {
                    IconButton(
                        modifier = Modifier
                            .width(35.dp),
                        onClick = {
                            if(activity != null)
                                activity.onBackPressed()
                        }
                    ) {
                        Icon(
                            imageVector = Icons.Filled.ArrowBack,
                            contentDescription = "Back button",
                            tint = Color.White,
                            modifier = Modifier
                                .fillMaxSize())
                    }
                    Text(
                        text = "Wallet",
                        color = Color.White,
                        style = TextStyle(
                            fontSize = 18.sp),
                        modifier = Modifier
                            .fillMaxHeight()
                            .wrapContentHeight(align = Alignment.CenterVertically)
                    )
                }
                Box(
                    modifier = Modifier
                        .width(180.dp)
                        .height(height = 40.dp)
                        .clip(shape = RoundedCornerShape(8.dp))
                        .background(color = Color(0xffbce35e))
                        .align(alignment = Alignment.CenterEnd)
                        .clickable {

                        }
                ) {
                    Text(
                        text = "Subscribe to promote",
                        color = Color(0xff2d650b),
                        style = TextStyle(
                            fontSize = 14.sp),
                        modifier = Modifier
                            .align(alignment = Alignment.CenterStart)
                            .padding(start = 10.dp))

                    Image(
                        painter = painterResource(id = R.drawable.subscribe_ic),
                        contentDescription = "Vector",
                        modifier = Modifier
                            .align(alignment = Alignment.CenterEnd)
                            .padding(end = 8.dp)
                    )
                }
            }
            Surface(
                modifier = Modifier
                    .offset(y = 20.dp)
                    .fillMaxWidth(0.9f)
                    .height(height = 150.dp)
                    .clip(shape = RoundedCornerShape(11.dp))
                    .background(color = Color.White)
                    .align(alignment = Alignment.CenterHorizontally),
                shadowElevation = 8.dp
            ) {
                Box() {
                    Text(
                        text = "Account Balance",
                        color = Color(0xff386641),
                        style = TextStyle(
                            fontSize = 15.sp),
                        modifier = Modifier
                            .align(alignment = Alignment.TopCenter)
                            .padding(top = 30.dp)
                    )
                    Text(
                        text = "P300,000.00",
                        color = Color(0xff386641),
                        style = TextStyle(
                            fontSize = 20.sp,
                            fontWeight = FontWeight.Bold),
                        modifier = Modifier
                            .align(alignment = Alignment.TopCenter)
                            .padding(top = 50.dp)
                    )
                }
                Row(modifier = Modifier
                    .fillMaxWidth()){
                    Box(
                        modifier = Modifier
                            .offset(y = 100.dp)
                            .height(height = 50.dp)
                            .clip(shape = RoundedCornerShape(bottomStart = 11.dp))
                            .background(color = Color(0xff8dba72))
                            .weight(1F)
                            .clickable {
                                activity?.startActivity(Intent(activity, SellerProfile::class.java))
                            }
                    ) {
                        Text(
                            text = "SEND",
                            color = Color(0xFF184E20),
                            style = TextStyle(
                                fontSize = 15.sp),
                            modifier = Modifier
                                .align(alignment = Alignment.Center))
                    }
                    Box(
                        modifier = Modifier
                            .offset(y = 100.dp)
                            .height(height = 50.dp)
                            .clip(shape = RoundedCornerShape(bottomEnd = 11.dp))
                            .background(color = Color(0xffAACF94))
                            .weight(1F)
                            .clickable { }
                    ) {
                        Text(
                            text = "REQUEST",
                            color = Color(0xFF184E20),
                            style = TextStyle(
                                fontSize = 15.sp),
                            modifier = Modifier
                                .align(alignment = Alignment.Center))
                    }
                }
            }
            Box(modifier = Modifier
                .fillMaxWidth(0.9f)
                .offset(
                    y = 30.dp
                )
                .height(40.dp)
                .align(alignment = Alignment.CenterHorizontally)
            ) {
                Text(
                    text = "Payment Options",
                    color = DeepMossGreen,
                    style = TextStyle(
                        fontSize = 18.sp,
                        fontWeight = FontWeight.SemiBold),
                    modifier = Modifier
                        .align(alignment = Alignment.CenterStart)
                )
                IconButton (modifier = Modifier
                    .align(alignment = Alignment.CenterEnd),onClick={}) {
                    Icon(
                        imageVector = Icons.Filled.AddCircle,
                        contentDescription = "icon plus circle",
                        tint = DeepMossGreen,
                        modifier = Modifier
                            .align(alignment = Alignment.CenterEnd)
                            .fillMaxHeight()
                            .width(40.dp)
                    )
                }
            }
            Spacer(modifier = Modifier.height(45.dp))
            Column {
                for(i in 1 .. 3) {
                    PaymentMethodsList()
                    Spacer(modifier = Modifier.height(10.dp))
                }
            }
        }

    }

}


@Composable
fun PaymentMethodsList() {
    Box (
        modifier = Modifier
            .fillMaxWidth()
            .padding(start=15.dp,end=15.dp)
            .height(85.dp)
            .clip(shape = RoundedCornerShape(11.dp))
            .background(Color(0xffEAFBE0))
    ){
        Row {
            Image(
                painter = painterResource(id = R.drawable.gcash_logo),
                contentDescription = "Gcash Logo",
                modifier = Modifier
                    .width(60.dp)
                    .fillMaxHeight()
                    .padding(start = 8.dp)
            )
            Column(
                modifier = Modifier
                    .padding(horizontal = 5.dp)
                    .align(Alignment.CenterVertically)
                    .weight(1F)
            ) {
                Text(
                    text = "Gcash",
                    color = Color(0xff386641),
                    style = TextStyle(
                        fontSize = 15.sp,
                        fontWeight = FontWeight.Bold),
                )
                Text(
                    text = "<<Account number>>",
                    color = Color(0xff386641),
                    style = TextStyle(
                        fontSize = 13.sp),
                )
                Text(
                    text = "<<Account name>>",
                    color = Color(0xff386641),
                    style = TextStyle(
                        fontSize = 13.sp),
                )
            }
        }
        Box(
            modifier = Modifier
                .align(Alignment.TopEnd)
        ) {
            Row () {
                IconButton(
                    modifier = Modifier
                        .size(35.dp)
                        .padding(end = 5.dp, top = 8.dp),
                    onClick = {
                    }
                ) {
                    Icon(
                        imageVector = Icons.Filled.Edit,
                        contentDescription = "Edit button",
                        tint = DeepMossGreen,
                        modifier = Modifier
                            .fillMaxSize()
                    )
                }
                IconButton(
                    modifier = Modifier
                        .size(35.dp)
                        .padding(end = 5.dp, top = 8.dp),
                    onClick = {
                    }
                ) {
                    Icon(
                        imageVector = Icons.Filled.Delete,
                        contentDescription = "Delete button",
                        tint = WatermelonRed,
                        modifier = Modifier
                            .fillMaxSize()
                    )
                }
            }

        }

    }
}

@Preview
@Composable
private fun PageInAppBalancePreview() {
    PageInAppBalance(null)
}