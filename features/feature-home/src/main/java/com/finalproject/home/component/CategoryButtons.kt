import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.foundation.background
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp

@Composable
fun CategoryButtons(
    categories: List<String>,
    selectedCategory: String,
    onCategorySelected: (String) -> Unit
) {
    val scrollState = rememberScrollState()

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(2.dp)
            .height(35.dp)
            .horizontalScroll(scrollState),
        horizontalArrangement = Arrangement.spacedBy(3.dp)
    ) {
        categories.forEach { category ->
            TextButton(
                onClick = { onCategorySelected(category) },
                modifier = Modifier
                    .background(
                        color = if (selectedCategory == category) colorResource(id = com.finalproject.util.R.color.colorAccent) else colorResource(
                            id = com.finalproject.util.R.color.darkAccent
                        ),
                        shape = RoundedCornerShape(12.dp)
                    )
                    .padding(horizontal = 16.dp, vertical = 1.dp)
            ) {
                Text(
                    text = category,
                    fontWeight = FontWeight.W900,
                    color = if (selectedCategory == category) Color.Black else Color.White,
                    fontSize = 12.sp
                )
            }
        }
    }
}
