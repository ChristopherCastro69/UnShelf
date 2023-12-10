import android.content.Intent
import com.example.unshelf.controller.DataFetch.DataFetchController
import com.example.unshelf.model.entities.ProductDetailsModel
import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.Modifier.*
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.ImageShader
import androidx.compose.ui.graphics.ShaderBrush
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.imageResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.rememberAsyncImagePainter
import com.example.unshelf.R
import com.example.unshelf.model.entities.Product
import com.example.unshelf.ui.theme.DeepMossGreen
import com.example.unshelf.ui.theme.MediumSpringBud
import com.example.unshelf.ui.theme.MiddleGreenYellow
import com.example.unshelf.ui.theme.PalmLeaf
import com.example.unshelf.view.Seller.SellerProfile
import com.example.unshelf.view.product.product_main
import com.example.unshelf.view.productView.ProductMainView
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.time.format.TextStyle

@Preview
@Composable
fun Marketplace(){
    Scaffold(
        topBar = {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(height = 50.dp)
                    .background(color = PalmLeaf)
                    .padding(horizontal = 10.dp)
            ) {
                Row(modifier = Modifier
                    .clip(RoundedCornerShape(10.dp))
                    .weight(1F)
                    .fillMaxHeight()
                    .padding(vertical = 10.dp)
                    .background(Color.White, RoundedCornerShape(10.dp))
                    .align(Alignment.CenterVertically)
                ) {
                    Icon(
                        imageVector = Icons.Filled.Search,
                            contentDescription = "Search button",
                            tint = PalmLeaf,
                            modifier = Modifier
                                .size(30.dp)
                                .padding(5.dp)
                                .align(Alignment.CenterVertically)
                    )
                    Text(
                        modifier = Modifier
                            .align(Alignment.CenterVertically),
                        text = "Search",
                        color = MiddleGreenYellow,
                        fontSize = 16.sp
                    )
                }
                Image(
                    painter = painterResource(id = R.drawable.ic_cart),
                    contentDescription = "Cart button",
                    modifier = Modifier
                        .fillMaxHeight()
                        .padding(5.dp)
                        .align(Alignment.CenterVertically)
                )
            }
        }
    ){ innerPadding ->
        LazyColumn{
            item{
                Column(modifier = Modifier.padding(innerPadding)) {
                    Image(
                        painter = painterResource(id = R.drawable.ad),
                        contentDescription = "Cart button",
                        contentScale = ContentScale.Crop,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(20.dp)
                    )
                    Column{
                        Box(Modifier.align(Alignment.CenterHorizontally)){
                            CategoryUI()
                        }
                        val dataFetch = DataFetchController.dataFetch
                        Log.d("Marketplace", "${dataFetch.size}")
                        for(product in dataFetch) {
                            Row(Modifier.padding(horizontal = 10.dp)) {
                                Text(
                                    text = "Selling Out",
                                    color = DeepMossGreen,
                                    fontSize = 16.sp,
                                    fontWeight = FontWeight.Bold,
                                    modifier = Modifier
                                        .padding(vertical = 10.dp)
                                )
                                Image(
                                    painter = painterResource(id = R.drawable.ic_fire),
                                    contentDescription = "hot deals",
                                    contentScale = ContentScale.Crop,
                                    modifier = Modifier
                                        .size(16.dp)
                                        .align(Alignment.CenterVertically)
                                )
                            }
                            ProductGroup(product)
                            Spacer(
                                Modifier.height(10.dp)
                            )
                        }
                    }

                }
            }
        }

    }
}


@Composable
fun CategoryUI() {
    val categoryImgs = listOf(R.drawable.ic_coupon,R.drawable.ic_groceries,R.drawable.ic_watermelon,R.drawable.ic_carrot,R.drawable.ic_bread, R.drawable.ic_meal)
    val categoryNames = listOf("Offers", "Grocery", "Fruits", "Vegetables", "Baked", "Meals")
    Row(horizontalArrangement = Arrangement.spacedBy(5.dp)){
        for(i in 0..5) {
            Column {
                Card(
                    Modifier
                        .background(Color.White)
                        .border(1.dp, MiddleGreenYellow, RoundedCornerShape(10.dp))
                        .align(Alignment.CenterHorizontally)
                ){
                    Image(
                        painterResource(id = categoryImgs[i]),
                        contentDescription = "category",
                        contentScale = ContentScale.Inside,
                        modifier = Modifier
                            .clip(RoundedCornerShape(5.dp))
                            .size(50.dp)
                            .background(Color.White, RoundedCornerShape(10.dp))
                            .clickable { }
                    )
                }
                Text(
                    text = "${categoryNames.get(i)}",
                    color = DeepMossGreen,
                    fontSize = 11.sp,
                    modifier = Modifier
                        .align(Alignment.CenterHorizontally)
                        .padding(vertical = 3.dp)
                )
            }

        }
    }
}
@Composable
fun ProductGroup(product : Product){
    Box(Modifier.padding(horizontal = 10.dp)){
        LazyRow(horizontalArrangement = Arrangement.spacedBy(10.dp)){
            items(6){
                ProductUI(product)
            }
        }
    }

}

@Composable
fun ProductUI(product : Product) {
    val context = LocalContext.current
    Box{
        Box(
            modifier = Modifier
                .border(1.dp, MiddleGreenYellow, RoundedCornerShape(10.dp))
                .width(140.dp)
                .height(190.dp)
        ){
            Text(
                text = product.productName,
                color = DeepMossGreen,
                fontSize = 14.sp,
                fontWeight = FontWeight.ExtraBold,
                modifier = Modifier
                    .align(Alignment.TopCenter)
                    .padding(vertical = 10.dp)
            )
            Image (
                rememberAsyncImagePainter(product.thumbnail),
                contentDescription = "${product.productName}",
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .size(120.dp)
                    .clip(RoundedCornerShape(10.dp))
                    .border(1.dp, color = MediumSpringBud, shape = RoundedCornerShape(10.dp))
                    .align(Alignment.Center)
                    .clickable{
                        val intent = Intent(context, ProductMainView::class.java)
                        context.startActivity(intent)
                    }
            )

            Text(
                text = "₱ ${product.price}",
                color = DeepMossGreen,
                fontSize = 16.sp,
                fontWeight = FontWeight.Black,
                modifier = Modifier
                    .align(Alignment.BottomCenter)
                    .padding(bottom = 10.dp)
            )
        }
        Box(modifier = Modifier.offset(y=30.dp,x=0.dp)) {
            if(product.discount > 0) {
                Image(
                    painterResource(id = R.drawable.ic_banner),
                    contentDescription = "banner",
                    contentScale = ContentScale.FillBounds,
                    modifier = Modifier
                        .width(80.dp)
                        .height(25.dp)
                )

                Text(
                    text = "Save ${product.discount}",
                    color = Color.White,
                    fontSize = 13.sp,
                    modifier = Modifier.padding(start=15.dp,top=5.dp)
                )
            }
        }
    }
}