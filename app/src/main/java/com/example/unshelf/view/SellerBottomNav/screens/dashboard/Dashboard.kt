    package com.example.unshelf.view.SellerBottomNav.screens.dashboard

    import JostFontFamily
    import android.os.Build
    import android.util.Log
    import androidx.annotation.RequiresApi
    import androidx.compose.foundation.Image
    import androidx.compose.foundation.background
    import androidx.compose.foundation.layout.*
    import androidx.compose.foundation.rememberScrollState
    import androidx.compose.foundation.verticalScroll

    import androidx.compose.material3.*
    import androidx.compose.runtime.Composable
    import androidx.compose.runtime.LaunchedEffect
    import androidx.compose.runtime.mutableStateOf
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
    import com.example.unshelf.model.firestore.seller.dashboardmodel.fetchUserDetails
    import com.example.unshelf.model.firestore.seller.dashboardmodel.getStoreName
    import com.example.unshelf.ui.theme.DeepMossGreen
    import java.time.LocalDate
    import java.time.format.DateTimeFormatter

    import androidx.compose.foundation.Canvas

    import androidx.compose.foundation.Canvas
    import androidx.compose.foundation.background
    import androidx.compose.foundation.layout.Box
    import androidx.compose.foundation.layout.fillMaxWidth
    import androidx.compose.foundation.layout.height
    import androidx.compose.ui.geometry.Offset
    import androidx.compose.ui.geometry.Size


    import androidx.compose.ui.graphics.Path
    import androidx.compose.ui.graphics.drawscope.DrawScope
    import androidx.compose.ui.graphics.drawscope.Stroke
    import androidx.compose.ui.text.TextStyle
    import androidx.compose.ui.text.font.FontFamily
    import androidx.compose.ui.graphics.nativeCanvas
    import androidx.compose.ui.graphics.toArgb
    import com.example.unshelf.ui.theme.Champagne
    import com.example.unshelf.ui.theme.PalmLeaf
    import com.example.unshelf.ui.theme.WatermelonRed
    import com.example.unshelf.ui.theme.YellowGreen
    import kotlin.random.Random



    var sellerId = mutableStateOf("")
    var storeId = mutableStateOf("")
    var storeName = mutableStateOf("")
    @RequiresApi(Build.VERSION_CODES.O)
    @Preview(showBackground = true, heightDp = 1500, widthDp = 390) // Adjust the height as needed
    @Composable
    fun DashboardPreview() {
        Dashboard()

    }


    @RequiresApi(Build.VERSION_CODES.O)
    @Composable
    fun Dashboard() {
        LaunchedEffect(key1 = Unit) {
            fetchUserDetails { sId, stId ->
                sellerId.value = sId
                storeId.value = stId
                Log.d("AddProducts", "LaunchedEffect Seller ID: ${sellerId.value}, Store ID: ${storeId.value}")
            }
            getStoreName{stName ->
                storeName.value = stName
                Log.d("StoreName", "LaunchedEffect Seller ID: ${sellerId.value}, Store Name: ${storeName.value}")
            }
        }
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
                    AnalyticsItem("Unpaid", 5)
                    AnalyticsItem("Processed Orders", 3)
                    AnalyticsItem("Cancellation Requests", 4)
                    AnalyticsItem("Sold Out Products", 6)
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
                    AnalyticsItem("To-Process Orders", 9)
                    AnalyticsItem("Refund Requests", 8)
                    AnalyticsItem("Unlisted Products", 4)
                    AnalyticsItem("Pending Campaign", 0)
                }
            }
        }
    }

    @Composable
    fun AnalyticsItem(title: String, count: Int = (10..100).random()) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.padding(vertical = 8.dp)
        ) {
            Text(
                text = count.toString(),
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                color = Color.Yellow
            )
            Text(
                text = title,
                fontSize = 14.sp,
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
                    text = "An overview of the shop data for December",
                    fontSize = 16.sp,
                    fontFamily = JostFontFamily,
                    fontWeight = FontWeight.Medium,
                    color = Color.White.copy(alpha = 0.7f)
                )
                Spacer(modifier = Modifier.height(15.dp)) // Spacing between the circle and text

                // Placeholder for the line chart, you'll replace this with your actual chart implementation
                LineChartPlaceholder()

                Divider(color = Color.White.copy(alpha = 0.5f), thickness = 1.dp, modifier = Modifier.padding(vertical = 16.dp))

                // Daily statistics
                DailyStatistics()

                Divider(color = Color.White.copy(alpha = 0.5f), thickness = 1.dp, modifier = Modifier.padding(vertical = 16.dp))

                // Placeholder for the pie chart, you'll replace this with your actual chart implementation
                PieChartWithLegendSideBySide()

                // Product portfolio, replace with actual data
//                ProductPortfolio()
            }

    }

    @Composable
    fun BarChartPlaceholder() {
        val dataPoints = List(10) { Random.nextInt(100) }

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(200.dp)
                .background(Color.DarkGray)
        ) {
            Canvas(modifier = Modifier.fillMaxSize()) {
                val barWidth = size.width / (dataPoints.size * 2f)
                val maxBarHeight = size.height

                dataPoints.forEachIndexed { index, value ->
                    val barHeight = (value / 100f) * maxBarHeight
                    val barTop = maxBarHeight - barHeight
                    val barLeft = (index * barWidth * 2) + barWidth / 2f
                    drawRect(
                        Color.White,
                        topLeft = Offset(barLeft, barTop),
                        size = androidx.compose.ui.geometry.Size(barWidth, barHeight)
                    )
                }
            }
        }
    }
    @Composable
    fun DailyStatistics() {
        // Sample data for daily statistics
        val stats = listOf(
            Pair("Total Sales", "$3,450"),
            Pair("New Orders", "87"),
            Pair("Returns", "5"),
            Pair("Customer Queries", "12")
        )

        Column {
            stats.forEach { (title, value) ->
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(text = title, color = Color.White)
                    Text(text = value, color = Color.White)
                }
                Spacer(modifier = Modifier.height(8.dp))
            }
        }
    }

    @Composable
    fun PieChartWithLegendSideBySide() {
        val dataPoints = listOf(30f, 10f, 25f, 35f) // Replace with actual data
        val sliceColors = listOf(WatermelonRed, Color.Yellow, Champagne, YellowGreen) // Replace with actual colors
        val sliceLegends = listOf("Frozen", "Dairy", "Beverages", "Snacks") // Replace with actual legends

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
                .height(IntrinsicSize.Min),
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Pie chart
            Canvas(
                modifier = Modifier
                    .weight(0.4f)
                    .size(150.dp)
            ) {
                drawPieChart(dataPoints, sliceColors, this)
            }

            // Legends
            Column(
                modifier = Modifier
                    .weight(0.4f)
                    .fillMaxHeight()
                    .padding(start = 16.dp),
                verticalArrangement = Arrangement.Center
            ) {
                sliceLegends.forEachIndexed { index, legend ->
                    LegendItem(sliceColors[index], "$legend ${dataPoints[index].toInt()}%")
                }
            }
        }
    }

    @Composable
    fun LegendItem(color: Color, text: String) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Canvas(modifier = Modifier.size(16.dp), onDraw = {
                drawCircle(color)
            })
            Spacer(modifier = Modifier.width(8.dp))
            Text(
                text = text,
                color = Color.White,
                fontSize = 14.sp
            )
        }
    }

    fun DrawScope.drawPieChart(dataPoints: List<Float>, colors: List<Color>, drawScope: DrawScope) {
        val total = dataPoints.sum()
        var startAngle = 0f // Start from 3 o'clock
        for ((index, data) in dataPoints.withIndex()) {
            val sweepAngle = (data / total) * 360f
            drawArc(
                color = colors[index],
                startAngle = startAngle,
                sweepAngle = sweepAngle,
                useCenter = true,
                size = Size(size.width, size.width) // Ensure the pie chart is circular
            )
            startAngle += sweepAngle
        }
    }


    @Composable
    fun ProductPortfolio() {
        val products = listOf("Product A", "Product B", "Product C")

        Column {
            products.forEach { product ->
                Text(text = product, color = Color.White)
                // Add more details as necessary (like images, descriptions)
            }
        }
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


    @Composable
    fun LineChartPlaceholder() {
        // Sample data for the line chart
        val dataPoints = List(5) { Random.nextInt(0, 450) } // Values between 50 and 450

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(200.dp)
                .padding(16.dp)
                .background(Color.Transparent) // Using the PalmLeaf color from your theme
        ) {
            Canvas(modifier = Modifier
                .size(200.dp)

            ) {
                val yAxisLabels = listOf("0", "100", "200", "300", "400")
                drawYAxisLabels(yAxisLabels, this)
                drawLineChart(dataPoints, this)
            }
        }
    }

    fun DrawScope.drawYAxisLabels(labels: List<String>, drawScope: DrawScope) {
        val textStyle = TextStyle(
            color = Color.White,
            fontSize = 12.sp,
            fontFamily = FontFamily.Monospace,
            fontWeight = FontWeight.Bold
        )

        val textPaint = android.graphics.Paint().apply {
            textSize = textStyle.fontSize.toPx()
            color = textStyle.color.toArgb()
//            typeface = android.graphics.Typeface.create(textStyle.fontFamily, android.graphics.Typeface.BOLD)
        }

        labels.forEachIndexed { index, label ->
            val x = 0f
            val y = size.height - (size.height / (labels.size - 1)) * index
            drawContext.canvas.nativeCanvas.drawText(label, x, y, textPaint)
        }
    }

    fun DrawScope.drawLineChart(dataPoints: List<Int>, drawScope: DrawScope) {
        val maxValue = 500 // Assuming 500 is the max value for the data points
        val points = dataPoints.map { it.toFloat() / maxValue * drawScope.size.height }

        // Calculate the coordinates for each point
        val stepX = drawScope.size.width / (dataPoints.size - 1)
        val coordinates = points.mapIndexed { index, y ->
            Offset(x = stepX * index + stepX, y = drawScope.size.height - y) // Offset stepX to the right for labels
        }

        // Draw the line
        val linePath = Path().apply {
            moveTo(coordinates.first().x, coordinates.first().y)
            coordinates.forEach { lineTo(it.x, it.y) }
        }
        drawPath(
            path = linePath,
            color = Color.White,
            style = Stroke(width = 3.dp.toPx())
        )

        // Draw the points
        coordinates.forEach { point ->
            drawCircle(
                color = Color(0xFFFFEB3B), // Yellow color for the points
                radius = 8.dp.toPx(),
                center = point
            )
        }
    }
