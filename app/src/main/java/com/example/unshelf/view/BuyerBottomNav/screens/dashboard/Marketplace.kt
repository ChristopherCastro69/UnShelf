import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.unshelf.R
import com.example.unshelf.ui.theme.MiddleGreenYellow
import com.example.unshelf.ui.theme.PalmLeaf
import java.time.format.TextStyle

@Preview
@Composable
fun Marketplace(){
    Scaffold(
        topBar = {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(height = 48.dp)
                    .background(color = PalmLeaf)
                    .padding(horizontal = 10.dp)
            ) {
                Row(modifier = Modifier
                    .clip(RoundedCornerShape(10.dp))
                    .weight(1F)
                    .background(Color.White)
                    .align(Alignment.CenterVertically)
                ) {
                    Icon(
                        imageVector = Icons.Filled.Search,
                            contentDescription = "Search button",
                            tint = PalmLeaf,
                            modifier = Modifier
                                .size(25.dp)
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
                        .size(40.dp)
                        .padding(5.dp)
                        .align(Alignment.CenterVertically)
                )
            }
        }
    ){ innerPadding ->
        Row(modifier = Modifier.padding(innerPadding)) {

        }
    }
}