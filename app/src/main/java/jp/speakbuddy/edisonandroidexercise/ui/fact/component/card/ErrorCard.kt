package jp.speakbuddy.edisonandroidexercise.ui.fact.component.card

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.runtime.Composable
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Text
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import jp.speakbuddy.edisonandroidexercise.R
import jp.speakbuddy.edisonandroidexercise.usecase.model.FactModelViewData

@Composable
fun ErrorCard(
    modifier: Modifier = Modifier,
    errorModel: FactModelViewData.Error
) {
    Box (
        modifier = modifier.then(Modifier.size(300.dp, 200.dp)
            .background(
                color = Color(0xFFF5E1A4)
            )),
    ) {
        Text(
            modifier = Modifier
                .align(
                    Alignment.Center
                )
                .padding(16.dp),
            color = Color.Red,
            text = stringResource(R.string.error_card_prompt, errorModel.errMessage)
        )
    }
}

@Preview
@Composable
private fun EmptyCardPreview(){
    ErrorCard(
        errorModel = FactModelViewData.Error("Error!")
    )
}