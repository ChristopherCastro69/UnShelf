package com.example.unshelf.view.checkout

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.paint
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.content.ContextCompat
import com.example.unshelf.R
import com.example.unshelf.ui.theme.DarkPalmLeaf
import com.example.unshelf.ui.theme.MiddleGreenYellow
import com.example.unshelf.view.BuyerBottomNav.ui.MainNavigationActivityBuyer
import com.example.unshelf.view.productView.CheckBox

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
        color = Color.White
    ) {
        Column (
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Menu(
                modifier = Modifier
                    .fillMaxWidth()
            )
            Row(
                modifier = Modifier
                    .background(color = Color.White)
                    .padding(16.dp),

            ) {
                Column(
                    modifier = Modifier
                        .weight(0.6f)
                        .background(Color.White)
                        .verticalScroll(rememberScrollState()),
                    verticalArrangement = Arrangement.Center
                ) {
                    Row(
                        modifier = Modifier
                        .fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Image(
                            painter = painterResource(id = R.drawable.ic_calendar),
                            contentDescription = "Calendar"
                        )
                        Text(
                            text = "Set Date",
                            color = Color(ContextCompat.getColor(LocalContext.current, R.color.green03))
                        )
                        Button(
                            onClick = {

                            },
                            colors = ButtonDefaults.buttonColors(DarkPalmLeaf),
                            modifier = Modifier
                        ) {
                            Text(
                                text = "mm/dd/yy",
                            )
                        }
                    }
                    Row (
                        modifier = Modifier
                            .fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Image(
                            painter = painterResource(id = R.drawable.ic_clock),
                            contentDescription = "Calendar"
                        )
                        Text(
                            text = "Set Time",
                            color = Color(ContextCompat.getColor(LocalContext.current, R.color.green03))
                        )
                        Button(
                            onClick = {

                            },
                            colors = ButtonDefaults.buttonColors(DarkPalmLeaf),
                            modifier = Modifier
                        ) {
                            Text(
                                text = "hh:mm",
                            )
                        }
                    }
                }

                // Second Column
                Column(
                    modifier = Modifier
                        .weight(0.4f)
                        .background(Color.White)
                        .verticalScroll(rememberScrollState())
                        .padding(top = 16.dp)
                ) {
                    Row  {
                        val isChecked = remember { mutableStateOf(false) }
                        CheckBox(
                            Modifier.padding(start = 8.dp, end = 8.dp, top = 4.dp),
                            checkedState = isChecked,
                            onCheckedChange = {
                            }
                        )
                        Text(
                            text = "As soon as possible",
                            color = Color(ContextCompat.getColor(LocalContext.current, R.color.green03))
                        )
                    }
                }
            }
            TextInputExample()
            repeat(5) {
                PickUpItem()
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

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TextInputExample() {
    var textValue by remember { mutableStateOf(TextFieldValue()) }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color.White),
    ) {
        Column(
            modifier = Modifier
                .padding(16.dp)
                .background(Color.White)
                .paint(
                    painterResource(id = R.drawable.ic_pickup_container),
                    contentScale = ContentScale.FillBounds
                )
        ) {
            TextField(
                value = textValue,
                onValueChange = {
                    textValue = it
                },
                label = { Text("Additional notes or message...") },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
                    .height(150.dp)
                    .background(Color.White),
                colors = TextFieldDefaults
                    .outlinedTextFieldColors(
                        containerColor = Color.White,
                        unfocusedBorderColor = Color.White),

            )
        }
    }
}


@Preview
@Composable
fun PickUpItem() {
    Row (
        modifier = Modifier
            .padding(top = 16.dp)
            .paint(
            painterResource(id = R.drawable.ic_pickup_container),
            contentScale = ContentScale.FillBounds
        ),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Image(painter = painterResource(id = R.drawable.ic_loc), contentDescription = "Location", modifier = Modifier.padding(start = 16.dp))
        Text (
            text = "Pickup Location \n Address",
            color = Color(ContextCompat.getColor(LocalContext.current, R.color.green03)),
            fontSize = 16.sp
        )
        Button(
            onClick = {

            },
            colors = ButtonDefaults.buttonColors(MiddleGreenYellow),
            modifier = Modifier.padding(end = 16.dp)
        ) {
            Text(
                text = "View in Maps",
            )
        }
    }
}
