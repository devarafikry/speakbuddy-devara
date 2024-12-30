package jp.speakbuddy.edisonandroidexercise.ui.fact.component.common
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material3.Card
import androidx.compose.material3.CardColors
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp


@Preview
@Composable
private fun MultipleCatsStempelPreview() {
    MultipleCatsStempel()
}

@Composable
fun MultipleCatsStempel(
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier.then(
            Modifier.wrapContentSize()
        ),
        colors = CardColors(
            containerColor = Color.Red,
            contentColor = Color.White,
            disabledContainerColor = Color.Gray,
            disabledContentColor = Color.LightGray
        )
    ) {
        Text(
            modifier = Modifier.padding(8.dp),
            text = "Multiple Cats",
            fontSize = 10.sp,
            fontWeight = FontWeight.Bold
        )
    }
}