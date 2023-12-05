    package com.example.unshelf.view.SellerBottomNav.screens.dashboard

    import JostFontFamily
    import android.os.Build
    import androidx.annotation.RequiresApi
    import androidx.compose.foundation.Image
    import androidx.compose.foundation.background
    import androidx.compose.foundation.layout.*
    import androidx.compose.foundation.rememberScrollState
    import androidx.compose.foundation.verticalScroll

    import androidx.compose.material3.*
    import androidx.compose.runtime.Composable
    import androidx.compose.ui.Alignment
    import androidx.compose.ui.Modifier

    import androidx.compose.ui.graphics.Color
    import androidx.compose.ui.graphics.painter.Painter
    import androidx.compose.ui.layout.ContentScale
    import androidx.compose.ui.res.painterResource


    import androidx.compose.ui.text.font.FontWeight
    import androidx.compose.ui.text.style.TextAlign
    import androidx.compose.ui.tooling.preview.Preview
    import androidx.compose.ui.unit.dp
    import androidx.compose.ui.unit.sp
    import com.example.unshelf.R
    import com.example.unshelf.ui.theme.DeepMossGreen
    import java.time.LocalDate
    import java.time.format.DateTimeFormatter


    @RequiresApi(Build.VERSION_CODES.O)
    @Preview(showBackground = true, heightDp = 1050, widthDp = 390) // Adjust the height as needed
    @Composable
    fun DashboardPreview() {
        Dashboard()
    }


    @RequiresApi(Build.VERSION_CODES.O)
    @Composable
    fun Dashboard() {
        val scrollState = rememberScrollState()

        Column(modifier = Modifier
            .fillMaxSize()
            .verticalScroll(scrollState)) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 10.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween // This ensures the space between the items
            ) {
                // Chat icon on the left
                Icon(
                    painter = painterResource(id = R.drawable.ic_chat),
                    contentDescription = "Chat",
                    tint = Color.Unspecified, // Add tint color if required
                    modifier = Modifier.size(32.dp) // Increased size as needed
                )

                Text(
                    text = "DASHBOARD",
                    fontFamily = JostFontFamily,
                    fontWeight = FontWeight.Bold,
                    fontSize = 32.sp,
                    textAlign = TextAlign.Center, // This will center the text
                    color = DeepMossGreen,
                    modifier = Modifier.weight(1f) // This ensures the text centers between the icons
                )

                // Notification bell icon on the right
                Icon(
                    painter = painterResource(id = R.drawable.ic_notification_bell),
                    contentDescription = "Notifications",
                    tint = Color.Unspecified, // Add tint color if required
                    modifier = Modifier.size(32.dp) // Increased size as needed
                )
            }
            // Use the CardWithBackgroundImage for each Card you want to have the background
            CardWithBackgroundImage(
                backgroundImagePainter = painterResource(R.drawable.card_background)
            ) {
                // Place your content for the 'Daily Analytics' card here
                // Example:
                DailyAnalyticsContent()

            }

            // Use the CardWithBackgroundImage for each Card you want to have the background
            CardWithBackgroundImage(
                backgroundImagePainter = painterResource(R.drawable.card_background)
            ) {
                // Place your content for the 'Daily Analytics' card here
                // Example:
                StoreInsightsContent()

            }

        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    @Composable
    fun DailyAnalyticsContent() {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
                .background(Color.Transparent) // Use the appropriate color from the design
        ) {
            Text(
                text = "Daily Analytics",
                fontSize = 20.sp,
                fontFamily = JostFontFamily,
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Center,
                modifier = Modifier.align(Alignment.CenterHorizontally),
                color = Color.White
            )
            Spacer(modifier = Modifier.height(16.dp))
            Text(
                text = "Date: ${getCurrentDate()}",
                fontSize = 14.sp,
                fontFamily = JostFontFamily,
                fontWeight = FontWeight.Medium,
                textAlign = TextAlign.Center,
                modifier = Modifier.align(Alignment.CenterHorizontally),
                color = Color.White.copy(alpha = 0.7f) // Slightly transparent white
            )

            Spacer(modifier = Modifier.height(8.dp))

            // Date text if needed
            // Text(text = "Date: mm/dd/yyyy", ...)

            Spacer(modifier = Modifier.height(16.dp))

            // Create two columns for the two sides
            Row(
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(
                    modifier = Modifier.weight(1f),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    // Left side items
                    AnalyticsItem("Unpaid", 0)
                    AnalyticsItem("Processed Orders", 0)
                    AnalyticsItem("Cancellation Requests", 0)
                    AnalyticsItem("Sold Out Products", 0)
                }

                Divider(
                    color = Color.Transparent,
                    modifier = Modifier
                        .fillMaxHeight()
                        .width(1.dp)
                        .padding(vertical = 2.dp)
                )

                Column(
                    modifier = Modifier.weight(1f),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    // Right side items
                    AnalyticsItem("To-Process Orders", 0)
                    AnalyticsItem("Refund Requests", 0)
                    AnalyticsItem("Unlisted Products", 0)
                    AnalyticsItem("Pending Campaign", 0)
                }
            }
        }
    }

    @Composable
    fun AnalyticsItem(title: String, count: Int) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.padding(vertical = 8.dp) // Adjust spacing as necessary
        ) {
            Text(
                text = count.toString(),
                fontSize = 24.sp,
                fontFamily = JostFontFamily,
                fontWeight = FontWeight.Bold,
                color = Color(0xFFFFEB3B) // Adjust the color as necessary
            )
            Text(
                text = title,
                fontSize = 12.sp,
                fontFamily = JostFontFamily,
                fontWeight = FontWeight.Medium,
                color = Color.White
            )
        }
    }


    @Composable
    fun StoreInsightsContent() {

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
                    .background(Color.Transparent) // Use the appropriate color from the design
            ) {
                Text(
                    text = "Store Insights",
                    fontSize = 24.sp,
                    fontFamily = JostFontFamily,
                    fontWeight = FontWeight.Bold,
                    color = Color.White
                )
                Text(
                    text = "An overview of the shop data for MONTH",
                    fontSize = 16.sp,
                    fontFamily = JostFontFamily,
                    fontWeight = FontWeight.Medium,
                    color = Color.White.copy(alpha = 0.7f)
                )

                // Placeholder for the line chart, you'll replace this with your actual chart implementation
                LineChartPlaceholder()

                Divider(color = Color.White.copy(alpha = 0.5f), thickness = 1.dp, modifier = Modifier.padding(vertical = 16.dp))

                // Daily statistics
                DailyStatistics()

                Divider(color = Color.White.copy(alpha = 0.5f), thickness = 1.dp, modifier = Modifier.padding(vertical = 16.dp))

                // Placeholder for the pie chart, you'll replace this with your actual chart implementation
                PieChartPlaceholder()

                // Product portfolio, replace with actual data
                ProductPortfolio()
            }

    }

    @Composable
    fun LineChartPlaceholder() {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(200.dp)
                .background(Color.White.copy(alpha = 0.2f)),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = "Line chart placeholder",
                color = Color.White,
                fontSize = 20.sp
            )
            // You will replace this with your actual line chart composable
        }
    }

    @Composable
    fun DailyStatistics() {
        // Implementation of daily statistics
        // Use the StatisticItem composable here
    }

    @Composable
    fun PieChartPlaceholder() {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(150.dp)
                .background(Color.White.copy(alpha = 0.2f)),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = "Pie chart placeholder",
                color = Color.White,
                fontSize = 20.sp
            )
            // You will replace this with your actual pie chart composable
        }
    }

    @Composable
    fun ProductPortfolio() {
        // Implementation of product portfolio
        // You can use a Row with Image composables or a custom implementation for your pie chart legend
    }



    @Composable
    fun CardWithBackgroundImage(
        backgroundImagePainter: Painter,
        content: @Composable () -> Unit
    ) {
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            elevation = CardDefaults.cardElevation(4.dp)
        ) {
            Box {
                Image(
                    painter = backgroundImagePainter,
                    contentDescription = null,
                    contentScale = ContentScale.Crop,
                    modifier = Modifier.matchParentSize()
                )
                content()
            }
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    @Composable
    fun getCurrentDate(): String {
        val currentDate = LocalDate.now()
        val formatter = DateTimeFormatter.ofPattern("MM/dd/yyyy")
        return currentDate.format(formatter)
    }