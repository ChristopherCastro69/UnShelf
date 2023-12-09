package com.example.unshelf.view.productView

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.contentColorFor
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.paint
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.DefaultShadowColor
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.content.ContextCompat
import com.example.unshelf.R
import com.example.unshelf.helper.UI_Tester_Helper
import com.example.unshelf.ui.theme.DarkPalmLeaf
import com.example.unshelf.view.BuyerBottomNav.main.BuyerScreens
import com.example.unshelf.view.authentication.Customer_Login
import com.example.unshelf.view.product.cart
import com.example.unshelf.view.product.product_main

class ProductMainView : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            // Your Compose UI goes here
            ProductMain()
        }
    }
}

@Preview
@Composable
fun ProductMain() {
    Surface(
        modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.colorScheme.background
    ) {
        Column {
            Column(
                modifier = Modifier
                    .background(Color.White)
                    .fillMaxWidth()
                    .weight(1f)
                    .verticalScroll(rememberScrollState())
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(color = Color.Gray) // Optional: Set background color
                ) {
                    Image(
                        painterResource(id = R.drawable.bread),
                        contentDescription = null,
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(370.dp), // Adjust the height as needed
                        contentScale = ContentScale.Crop
                    )
                    PMNavigation()
                }
                PMContent()
            }
            PMMenu()
        }
    }
}

@Composable
fun PMMenu() {
    Row(
        modifier = Modifier.run {
            padding(top = 10.dp)
                .fillMaxWidth()
                .shadow(
                    elevation = 50.dp,
                    shape = RectangleShape,
                    clip = false,
                    ambientColor = DefaultShadowColor,
                    spotColor = DefaultShadowColor,
                )
        },
        horizontalArrangement = Arrangement.Center, // justify-content: center
    ) {
        Button(
            onClick = { /* Handle button click */ },
            colors = ButtonDefaults.buttonColors(DarkPalmLeaf),
            modifier = Modifier
                .height(50.dp)
                .padding(bottom = 10.dp)
                .align(Alignment.CenterVertically)

        ) {
            Text(
                text = "ADD TO BASKET",
                color = Color.Black
            )
        }
        Button(
            onClick = { /* Handle button click */ },
            colors = ButtonDefaults.buttonColors(DarkPalmLeaf),
            modifier = Modifier
                .height(50.dp)
                .padding(bottom = 10.dp, start = 20.dp)
                .align(Alignment.CenterVertically)

        ) {
            Text(
                text = "Buy Now",
                color = Color.White
            )
        }
    }
}

@Composable
fun PMNavigation() {
    val context = LocalContext.current
    Row (
        modifier = Modifier
            .fillMaxWidth()
            .height(100.dp)
    ) {

        Image (
            painter = painterResource(id = R.drawable.ic_back_button),
            contentDescription = "Back",
            modifier = Modifier
                .padding(15.dp)
                .height(55.dp)
                .width(55.dp)
            ,
            contentScale = ContentScale.Crop
        )
        Spacer(
            modifier = Modifier.weight(1f) // Add some space between images
        )
        Image (
            painter = painterResource(id = R.drawable.ic_cart),
            contentDescription = "Back",
            modifier = Modifier
                .padding(15.dp)
                .height(55.dp)
                .width(55.dp),
            contentScale = ContentScale.Crop
        )
    }
}

@Preview
@Composable
fun PMContent() {
    Row (
        modifier = Modifier
            .fillMaxWidth()
    ) {
        Column(
            modifier = Modifier
                .padding(16.dp)
                .weight(0.5f)
        ) {
            Text (
                text = "Sliced Bread",
                color = Color(
                            ContextCompat.getColor(
                                LocalContext.current,
                                R.color.green02
                            )
                        ),
                fontSize = 30.sp
            )
            Row (
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.padding(top = 8.dp)
            ) {
                Text (
                    text = "₱380",
                    color = Color(
                        ContextCompat.getColor(
                            LocalContext.current,
                            R.color.green03
                        )
                    ),
                    fontSize = 30.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(end = 10.dp)
                )
                Box {
                    Image(
                        painterResource(id = R.drawable.pm_banner_ic),
                        contentDescription = "banner",
                        contentScale = ContentScale.FillBounds,
                        modifier = Modifier
                            .width(80.dp)
                            .height(25.dp)
                    )
                    Text(
                        text = "15% off",
                        color = Color.White,
                        fontSize = 13.sp,
                        modifier = Modifier.padding(start=15.dp,top=5.dp)
                    )
                }

            }
            Row (
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 8.dp),
                verticalAlignment = Alignment.CenterVertically
            ){
                Image(
                    painter = painterResource(
                        id = R.drawable.seller_ic),
                    contentDescription = "SellerIc",
                    contentScale = ContentScale.FillBounds,
                    modifier = Modifier.padding(end = 10.dp)
                )
                Text (
                    text = "Julie's Bakeshop",
                    fontSize = 16.sp,
                    color = Color(
                        ContextCompat.getColor(
                            LocalContext.current,
                            R.color.green02
                        )
                    )
                )
            }
            Text (
                text = "Description",
                color = Color(
                    ContextCompat.getColor(
                        LocalContext.current,
                        R.color.green02
                    )
                ),
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(top = 8.dp)
            )
        }
        Row () {
            PMHeart(Modifier.padding(20.dp), false, onCheckedChange = {})
        }
    }
    Column (
        modifier = Modifier.padding(end = 16.dp, start = 16.dp)
    ) {
        Text (
            text = "Julie’s sliced bread is a celebration of flavor, texture, and tradition. Handcrafted with love and expertise, each loaf is a testament to our commitment to bringing you the finest in bakery delights.Note: Store in room temperature",
            color = Color(ContextCompat.getColor(LocalContext.current, R.color.green02)),
            fontSize = 16.sp,
            modifier = Modifier.padding(bottom = 10.dp)
        )
        Text (
            text = "Select Variation",
            color = Color(
                ContextCompat.getColor(
                    LocalContext.current,
                    R.color.green03
                )
            ),
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold,
        )
        Column (
            modifier = Modifier
                .align(Alignment.CenterHorizontally)
                .padding(top = 10.dp)
                .fillMaxWidth()
                .fillMaxHeight()
                .paint(
                    painterResource(id = R.drawable.rs_pmvariationcontainer),
                    contentScale = ContentScale.FillBounds
                ),
        ) {
            VariationItem()
            VariationItem()
        }
    }
}


@Composable
fun VariationItem(
    qty: Int = 0
) {
    var (qty, setQty)  = remember { mutableStateOf(qty) }
    Row (
        modifier = Modifier.padding(start = 15.dp, end = 15.dp, top = 10.dp)
    ) {
            VariationCheckBox(
                modifier = Modifier
                    .padding(end = 10.dp)
                    .height(20.dp)
                    .width(20.dp),
                false,
                onCheckedChange = {

                }
            )
        Text (
            text = "Plain Bread",
            color = Color(
                ContextCompat.getColor(
                    LocalContext.current,
                    R.color.green03
                )
            ),
            modifier = Modifier.weight(1f),
            fontSize = 18.sp
        )
        Image(
            painter = painterResource(id = R.drawable.ic_plust_active),
            contentDescription = "Increase Quantity",
            modifier = Modifier
                .padding(end = 10.dp)
                .height(25.dp)
                .width(25.dp)
                .clickable {
                    setQty(qty + 1)
                }
        )
        Text(
            text = "${qty}",
            color = Color(ContextCompat.getColor(LocalContext.current, R.color.green02)),
            fontSize = 18.sp,
            
        )
        Image(
            painter = painterResource(id = R.drawable.ic_minus_active),
            contentDescription = "Decrease Quantity",
            modifier = Modifier
                .padding(start = 10.dp)
                .height(25.dp)
                .width(25.dp)
                .clickable {
                    setQty(qty - 1)
                }
        )
    }
}


@Composable
fun VariationCheckBox(
    modifier: Modifier = Modifier,
    checkedState: Boolean,
    onCheckedChange: () -> Unit
) {
    val (checked, setChecked) = remember { mutableStateOf(checkedState) }

    if (checked) {
        Image (
            painter = painterResource(id = R.drawable.variation_selected_ic),
            contentDescription = "Checkbox",
            modifier = modifier
                .clickable {
                    setChecked(!checked)
                    onCheckedChange()
                }

        )
    } else {
        Image (
            painter = painterResource(id = R.drawable.variation_not_selected_ic),
            contentDescription = "Checkbox",
            modifier = modifier
                .clickable {
                    setChecked(!checked)
                }
        )
    }
}


@Composable
fun PMHeart(
    modifier: Modifier = Modifier,
    checkedState: Boolean,
    onCheckedChange: () -> Unit
) {
    val (checked, setChecked) = remember { mutableStateOf(checkedState) }

    if (checked) {
        Image (
            painter = painterResource(id = R.drawable.selected_icon_heart),
            contentDescription = "Checkbox",
            modifier = modifier
                .clickable {
                    setChecked(!checked)
                    onCheckedChange()
                }

        )
    } else {
        Image (
            painter = painterResource(id = R.drawable.__icon__heart_),
            contentDescription = "Checkbox",
            modifier = modifier
                .clickable {
                    setChecked(!checked)
                }
        )
    }

}