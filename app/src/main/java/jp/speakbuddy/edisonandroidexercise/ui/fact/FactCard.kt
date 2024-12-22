package jp.speakbuddy.edisonandroidexercise.ui.fact

import androidx.compose.runtime.Composable
import jp.speakbuddy.edisonandroidexercise.usecase.model.FactModel
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material3.Card
import androidx.compose.material3.CardColors
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import jp.speakbuddy.edisonandroidexercise.R
import jp.speakbuddy.edisonandroidexercise.usecase.model.LengthInfo


@Composable
fun FactCard(
    factModel: FactModel?
) {
    Card (
        modifier = Modifier.size(300.dp, 200.dp),
        colors = CardColors(
            containerColor = Color(0xFFF5E1A4),
            contentColor = Color.Black,
            disabledContainerColor = Color.Gray,
            disabledContentColor = Color.LightGray
        )
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(12.dp)
        ) {
            factModel?.let {
                Text(
                    modifier = Modifier.align(Alignment.CenterHorizontally),
                    text = "Fact",
                    fontWeight = FontWeight.Bold,
                    style = MaterialTheme.typography.titleLarge
                )

                Text(
                    modifier = Modifier
                        .fillMaxSize()
                        .weight(1f),
                    fontStyle = FontStyle.Italic,
                    text = factModel.fact,
                    style = MaterialTheme.typography.bodyLarge
                )
                if (factModel.lengthInfo.shouldShowLength) {
                    Text(
                        modifier = Modifier
                            .wrapContentSize()
                            .align(Alignment.End),
                        text = stringResource(
                            R.string.fact_screen_length_label,
                            factModel.lengthInfo.length
                        ),
                        style = MaterialTheme.typography.bodyLarge,
                        fontSize = 10.sp
                    )
                }
            }
        }
    }
}

@Preview
@Composable
private fun FactCardPreview(){
    FactCard(
        factModel = FactModel(
            fact = "Testing Fact",
            lengthInfo = LengthInfo(
                length = 11,
                shouldShowLength = false
            ),
            multipleCats = false
        )
    )
}