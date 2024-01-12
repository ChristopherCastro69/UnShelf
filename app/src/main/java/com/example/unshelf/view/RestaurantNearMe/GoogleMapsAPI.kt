package com.example.unshelf.view.RestaurantNearMe

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.platform.SoftwareKeyboardController
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import com.example.unshelf.R
import com.google.android.gms.maps.MapView
import com.google.android.libraries.places.api.model.Place

@Preview 
@Composable
fun MapScreen() {
    var searchQuery by remember { mutableStateOf(TextFieldValue()) }
    var selectedPlace by remember { mutableStateOf<Place?>(null) }
    val context = LocalContext.current

    val mapView = rememberMapViewWithLifecycle()


    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        // Search bar
        SearchBar(
            searchQuery = searchQuery,
            onSearchQueryChange = { searchQuery = it },
            onSearchClicked = {
                // Perform the search and update the map accordingly
                // Use the searchQuery to geocode the address and update the map
            }
        )

        // Map composable
        AndroidView(
            modifier = Modifier
                .fillMaxSize(),
            factory = { context ->
                mapView.apply {
                    onCreate(null)
                    getMapAsync { googleMap ->
                        // Handle map setup, markers, etc.
                    }
                }
            }
        ) { mapView ->
            // Handle lifecycle events
            mapView.onResume()
        }
    }
}

@Composable
fun SearchBar(
    searchQuery: TextFieldValue,
    onSearchQueryChange: (TextFieldValue) -> Unit,
    onSearchClicked: () -> Unit
) {
    var keyboardController by remember { mutableStateOf<SoftwareKeyboardController?>(null) }

    OutlinedTextField(
        value = searchQuery,
        onValueChange = {
            onSearchQueryChange(it)
            keyboardController?.show()
        },
        label = { Text("Search for a place") },
        modifier = Modifier
            .fillMaxWidth()
            .onGloballyPositioned { coordinates ->
                // Ensure the keyboard is showing when the search bar is focused
                if (keyboardController == null) {
//                    keyboardController = LocalSoftwareKeyboardController.current
                }
            },
        trailingIcon = {
            IconButton(onClick = onSearchClicked) {
                Icon(imageVector = Icons.Default.Search, contentDescription = null)
            }
        }
    )
}

@Composable
fun rememberMapViewWithLifecycle(): MapView {
    val context = LocalContext.current
    return remember {
        MapView(context).apply {
//            id = R.id.mapView
        }
    }
}
