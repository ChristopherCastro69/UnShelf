package com.example.unshelf.view.SellerBottomNav.screens.store

import JostFontFamily
import android.content.Intent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Home
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.unshelf.R
import com.example.unshelf.model.entities.Order
import com.example.unshelf.ui.theme.DeepMossGreen
import com.example.unshelf.ui.theme.PalmLeaf
import com.example.unshelf.ui.theme.TeaGreen
import org.intellij.lang.annotations.JdkConstants.HorizontalAlignment

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EditStore(){
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = "Store Configuration",
                        fontFamily = JostFontFamily,
                        fontWeight = FontWeight.Bold,
                        color = Color.White)
                },
                colors = TopAppBarDefaults.mediumTopAppBarColors(
                    containerColor = PalmLeaf
                ),
                navigationIcon = {
                    IconButton(onClick = {}) {
                        Icon(Icons.Filled.ArrowBack,
                            "Back Button",
                            tint = Color.White,
                            modifier = Modifier
                                .size(25.dp))
                    }
                }

            )
        }

    ){ paddingValues ->
        Column(modifier = Modifier
            .padding(paddingValues)
            .padding(20.dp, 30.dp, 20.dp, 20.dp)
            .fillMaxWidth(1f)) {
            Row (
                verticalAlignment = Alignment.CenterVertically,
            ){
                Icon(
                    painter = painterResource(id = R.drawable.store),
                    contentDescription = "Store",
                    modifier = Modifier
                        .size(35.dp),
                    tint = DeepMossGreen
                )
                Text(
                    text = "Store Details",
                    modifier = Modifier
                        .padding(start = 20.dp),
                    fontFamily = JostFontFamily,
                    fontWeight = FontWeight.Normal,
                    color = DeepMossGreen,
                    fontSize = 24.sp
                )
            }
            Text(
                text = "Store name",
                modifier = Modifier
                    .padding(top = 20.dp),
                fontFamily = JostFontFamily,
                fontWeight = FontWeight.Normal,
                fontSize = 16.sp
            )
            var name by remember {
                mutableStateOf("")
            }
            TextField(
                value = name,
                onValueChange = {name = it},
                modifier = Modifier
                    .padding(top = 10.dp),
                placeholder = {
                    Text(text = "Enter store name here")
                }
            )

            Text(
                text = "Store Description",
                modifier = Modifier
                    .padding(top = 20.dp),
                fontFamily = JostFontFamily,
                fontWeight = FontWeight.Normal,
                fontSize = 16.sp
            )
            var description by remember {
                mutableStateOf("")
            }
            TextField(
                value = description,
                onValueChange = {description = it},
                modifier = Modifier
                    .padding(top = 10.dp)
                    .fillMaxWidth(1f),
                placeholder = {
                    Text(text = "Enter store description here")
                }
            )
        }
    }}


@Composable
@Preview(showBackground = true)
fun EditStorePreview(){
    Column {
        EditStore()

    }
}
