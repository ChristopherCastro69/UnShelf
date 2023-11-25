package com.example.unshelf.ui.theme


//fun customIcon(iconName: String): ImageVector {
//    // Logic to load the custom icon dynamically based on the provided name
//    // For instance, using resource identifiers
//    val resourceId = when (iconName) {
//        "dashboard_icon" -> R.drawable.ic_dashboard
//        // Add more cases for other icons
//        else -> R.drawable.default_icon // Default icon in case the specified icon is not found
//    }
//    return ImageVector.vectorResource(id = resourceId)
//}

import androidx.compose.foundation.Image
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.painterResource

@Composable
fun CustomIcon(iconRes: Int) {
    Image(
        painter = painterResource(id = iconRes),
        contentDescription = null // Provide a meaningful description if needed
    )
}
