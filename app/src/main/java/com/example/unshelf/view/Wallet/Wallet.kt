package com.example.unshelf.view.Wallet

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.Composable

import androidx.compose.foundation.*
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.scrollable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBackIos
import androidx.compose.material3.Card
import androidx.compose.material3.CardColors
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.*
import com.example.unshelf.R
import com.example.unshelf.controller.User.UserController
import com.example.unshelf.controller.Wallet.WalletController
import com.example.unshelf.model.entities.Order
import com.example.unshelf.ui.theme.DeepMossGreen
import com.example.unshelf.ui.theme.PalmLeaf
import com.example.unshelf.ui.theme.WatermelonRed
import com.example.unshelf.view.Seller.SellerProfile

class Wallet: ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val user = intent.getStringExtra("user")!!
        if(user.equals("seller")) {
            WalletController.getReceipts()
        } else if(user.equals("buyer")) {
            WalletController.getPayments()
        }
        setContent {
            PageInAppBalance(user)
        }
    }

}

@Composable
fun PageInAppBalance(user : String) {
    val context = LocalContext.current
    var totalBalance = remember { WalletController.totalBalance }
    var sales = remember { WalletController.salesList }
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
                            (context as? Activity)?.finish()
                        }
                    ) {
                        Icon(
                            imageVector = Icons.Filled.ArrowBackIos,
                            contentDescription = "Back button",
                            tint = Color.White,
                            modifier = Modifier
                                .fillMaxSize())
                    }
                    Text(
                        text = "${
                            if(user.equals("seller")) {
                                "Wallet"
                            } else {
                                "Payments"
                            }
                        }",
                        color = Color.White,
                        style = TextStyle(
                            fontSize = 18.sp),
                        modifier = Modifier
                            .fillMaxHeight()
                            .wrapContentHeight(align = Alignment.CenterVertically)
                    )
                }
                if(user.equals("seller")) {
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
                                fontSize = 14.sp
                            ),
                            modifier = Modifier
                                .align(alignment = Alignment.CenterStart)
                                .padding(start = 10.dp)
                        )

                        Image(
                            painter = painterResource(id = R.drawable.subscribe_ic),
                            contentDescription = "Vector",
                            modifier = Modifier
                                .align(alignment = Alignment.CenterEnd)
                                .padding(end = 8.dp)
                        )
                    }
                }
            }
            var surfaceSize = 150.dp
            if(user.equals("buyer")) {
                surfaceSize = 100.dp
            }
            if(WalletController.isLoading.value == true && user.equals("buyer")) {
                CircularProgressIndicator(color = PalmLeaf, modifier = Modifier
                    .align(Alignment.CenterHorizontally)
                    .size(50.dp)
                    .padding(top = 10.dp))
            } else {
                Surface(
                    modifier = Modifier
                        .offset(y = 20.dp)
                        .fillMaxWidth(0.9f)
                        .height(height = surfaceSize)
                        .clip(shape = RoundedCornerShape(11.dp))
                        .background(color = Color.White)
                        .align(alignment = Alignment.CenterHorizontally),
                    shadowElevation = 8.dp
                ) {
                    Box() {
                        var text = "Account Balance"
                        if (user.equals("buyer")) {
                            text = "Total payments for this month"
                        }
                        Text(
                            text = text,
                            color = PalmLeaf,
                            style = TextStyle(
                                fontSize = 15.sp
                            ),
                            modifier = Modifier
                                .align(alignment = Alignment.TopCenter)
                                .padding(top = 30.dp, bottom = 5.dp)
                        )
                        Text(
                            text = "₱ ${String.format("%.2f", totalBalance.value)}",
                            color = DeepMossGreen,
                            style = TextStyle(
                                fontSize = 20.sp,
                                fontWeight = FontWeight.Bold
                            ),
                            modifier = Modifier
                                .align(alignment = Alignment.TopCenter)
                                .padding(top = 50.dp)
                        )
                    }
                    if (user.equals("seller")) {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                        ) {
                            Box(
                                modifier = Modifier
                                    .offset(y = 100.dp)
                                    .height(height = 50.dp)
                                    .clip(shape = RoundedCornerShape(bottomStart = 11.dp))
                                    .background(color = Color(0xff8dba72))
                                    .weight(1F)
                                    .clickable {
                                        context.startActivity(
                                            Intent(
                                                context,
                                                SellerProfile::class.java
                                            )
                                        )
                                    }
                            ) {
                                Text(
                                    text = "SEND",
                                    color = Color(0xFF184E20),
                                    style = TextStyle(
                                        fontSize = 15.sp
                                    ),
                                    fontWeight = FontWeight.Bold,
                                    modifier = Modifier
                                        .align(alignment = Alignment.Center)
                                )
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
                                        fontSize = 15.sp
                                    ),
                                    fontWeight = FontWeight.Bold,
                                    modifier = Modifier
                                        .align(alignment = Alignment.Center)
                                )
                            }
                        }
                    }

                }
                Box(
                    modifier = Modifier
                        .fillMaxWidth(0.9f)
                        .offset(
                            y = 30.dp
                        )
                        .height(40.dp)
                        .align(alignment = Alignment.CenterHorizontally)
                ) {
                    Text(
                        text = "Recent Transactions",
                        color = DeepMossGreen,
                        style = TextStyle(
                            fontSize = 18.sp,
                            fontWeight = FontWeight.SemiBold
                        ),
                        modifier = Modifier
                            .align(alignment = Alignment.CenterStart)
                    )
                }
                Spacer(modifier = Modifier.height(45.dp))
                Column(modifier = Modifier.verticalScroll(rememberScrollState())) {
                    for (sale in sales.value) {
                        PaymentMethodsList(sale, user)
                        Spacer(modifier = Modifier.height(10.dp))
                    }
                }
            }
        }

    }

}


@Composable
fun PaymentMethodsList(sale : Order, user : String) {
    Box (
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 15.dp, end = 15.dp)
            .height(85.dp)
            .clip(shape = RoundedCornerShape(11.dp))
            .background(Color(0xffEAFBE0))
    ){
        Row {
            var paymentMethodLogo = R.drawable.gcash_logo
            if(sale.paymentMethod.equals("paymaya")) {
                paymentMethodLogo = R.drawable.maya_logo
            }
            Image(
                painter = painterResource(id = paymentMethodLogo),
                contentDescription = "Gcash Logo",
                contentScale = ContentScale.Inside,
                modifier = Modifier
                    .width(60.dp)
                    .fillMaxHeight()
                    .padding(start = 8.dp)
            )

            Column(
                modifier = Modifier
                    .padding(start = 10.dp,end = 5.dp)
                    .align(Alignment.CenterVertically)
                    .weight(1F)
            ) {
                Row {
                    Text(
                        text = "Ref #: ${sale.refNo}",
                        color = DeepMossGreen,
                        style = TextStyle(
                            fontSize = 15.sp,
                            fontWeight = FontWeight.Bold),
                    )
                    Text(
                        text = "₱ ${
                            if (user == "seller") {
                                String.format("%.2f", sale.netAmount)
                            } else {
                                String.format("%.2f", sale.totalAmount)
                            }
                        }",
                        color = DeepMossGreen,
                        style = TextStyle(
                            fontSize = 17.sp,
                            fontWeight = FontWeight.Bold),
                        textAlign = TextAlign.Right,
                        modifier = Modifier
                            .weight(1F)
                            .padding(end = 10.dp)
                    )
                }
                if(user.equals("seller")) {
                    Row {
                        Text(
                            text = "Total: ",
                            color = DeepMossGreen,
                            style = TextStyle(
                                fontSize = 13.sp
                            ),
                        )
                        Text(
                            text = "+ ₱ ${String.format("%.2f", sale.totalAmount)}",
                            color = Color(0xFF50C907),
                            style = TextStyle(
                                fontSize = 13.sp
                            ),
                        )
                    }
                    Row {
                        Text(
                            text = "Paymongo fee: ",
                            color = DeepMossGreen,
                            style = TextStyle(
                                fontSize = 13.sp
                            ),
                        )
                        Text(
                            text = "- ₱ ${String.format("%.2f", sale.paymongoFee)}",
                            color = WatermelonRed,
                            style = TextStyle(
                                fontSize = 13.sp
                            ),
                        )
                    }

                }
                Row {
                    Text(
                        text = "${
                            if(user.equals("seller")) {
                            "UnShelf fee:"
                            } else {
                                sale.paymentTimestamp.toString().substring(0..18)
                            }} ",
                        color = DeepMossGreen,
                        style = TextStyle(
                            fontSize = 13.sp
                        ),
                    )
                    sale.paymentTimestamp.toString().substringAfter("_")
                    Text(
                        text = "${
                            if(user.equals("seller")){
                            "- ₱ ${String.format("%.2f", sale.unshelfFee)}"}
                            else {
                                ""
                            }}",
                        color = WatermelonRed,
                        style = TextStyle(
                            fontSize = 13.sp
                        ),
                    )
                    Text(
                        text = "${sale.orderStatus}",
                        color = PalmLeaf,
                        style = TextStyle(
                            fontSize = 15.sp,
                        ),
                        textAlign = TextAlign.End,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier
                            .weight(1F)
                            .padding(end = 10.dp)
                    )
                }
            }
        }
//        Box(
//            modifier = Modifier
//                .align(Alignment.TopEnd)
//        ) {
//            Row () {
//                IconButton(
//                    modifier = Modifier
//                        .size(35.dp)
//                        .padding(end = 5.dp, top = 8.dp),
//                    onClick = {
//                    }
//                ) {
//                    Icon(
//                        imageVector = Icons.Filled.Edit,
//                        contentDescription = "Edit button",
//                        tint = DeepMossGreen,
//                        modifier = Modifier
//                            .fillMaxSize()
//                    )
//                }
//                IconButton(
//                    modifier = Modifier
//                        .size(35.dp)
//                        .padding(end = 5.dp, top = 8.dp),
//                    onClick = {
//                    }
//                ) {
//                    Icon(
//                        imageVector = Icons.Filled.Delete,
//                        contentDescription = "Delete button",
//                        tint = WatermelonRed,
//                        modifier = Modifier
//                            .fillMaxSize()
//                    )
//                }
//            }
//
//        }

    }
}

//@Preview
//@Composable
//private fun PageInAppBalancePreview() {
//    PageInAppBalance()
//}