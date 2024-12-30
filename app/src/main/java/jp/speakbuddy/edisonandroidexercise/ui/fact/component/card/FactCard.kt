package jp.speakbuddy.edisonandroidexercise.ui.fact.component.card

import android.app.Activity
import android.content.res.Resources.Theme
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.Box
import androidx.compose.runtime.Composable
import jp.speakbuddy.edisonandroidexercise.usecase.model.FactModel
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Card
import androidx.compose.material3.CardColors
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalView
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.semantics.testTagsAsResourceId
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import jp.speakbuddy.edisonandroidexercise.R
import jp.speakbuddy.edisonandroidexercise.ui.theme.CardColor
import jp.speakbuddy.edisonandroidexercise.usecase.model.FactModelViewDataSource
import jp.speakbuddy.edisonandroidexercise.usecase.model.LengthInfo


@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun FactCard(
    factModel: FactModel?,
    modifier: Modifier = Modifier
) {
    Card (
        modifier = modifier
            .then(Modifier.size(300.dp, 200.dp)),
        colors = CardColors(
            containerColor = CardColor,
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
            if (factModel == null) {
                Spacer(Modifier.height(32.dp))
                CircularProgressIndicator(
                    modifier = Modifier
                        .width(64.dp)
                        .align(Alignment.CenterHorizontally),
                    color = Color.Black,
                    trackColor = Color.White,
                )

                Spacer(Modifier.height(50.dp))

                FlickeringText(
                    modifier = Modifier.align(Alignment.CenterHorizontally),
                    text = stringResource(R.string.fact_card_fetch_cloud_state)
                )
            } else {
                factModel?.let {
                    Text(
                        modifier = Modifier.align(Alignment.CenterHorizontally),
                        text = "Fact",
                        fontWeight = FontWeight.Bold,
                        style = MaterialTheme.typography.titleLarge
                    )

                    Spacer(Modifier.height(8.dp))
                    val scrollState = rememberScrollState()

                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .verticalScroll(scrollState)
                            .weight(1f)
                            .semantics { testTagsAsResourceId = true }
                            .testTag(stringResource(R.string.fact_card))
                    ) {
                        Text(
                            modifier = Modifier
                                .fillMaxSize(),
                            fontStyle = FontStyle.Italic,
                            text = factModel.fact,
                            style = MaterialTheme.typography.bodyLarge
                        )
                        (LocalContext.current as? Activity)?.reportFullyDrawn()
                    }

                    Row( modifier = Modifier
                        .wrapContentSize()
                        .align(Alignment.End)) {
                        if (scrollState.canScrollForward) {
                            Text(
                                modifier = Modifier
                                    .wrapContentSize(),
                                text = stringResource(R.string.fact_card_scroll_prompt),
                                style = MaterialTheme.typography.bodyLarge,
                                fontSize = 10.sp,
                            )
                        }

                        Spacer(Modifier.width(8.dp))

                        if (factModel.lengthInfo.shouldShowLength) {
                            Text(
                                modifier = Modifier
                                    .wrapContentSize(),
                                text = stringResource(
                                    R.string.fact_screen_length_label,
                                    factModel.lengthInfo.length
                                ),
                                style = MaterialTheme.typography.bodyLarge,
                                fontSize = 10.sp
                            )
                        }
                        Spacer(Modifier.width(8.dp))
                        Text(
                            modifier = Modifier
                                .wrapContentSize(),
                            text = stringResource(R.string.fact_card_source, factModel.source),
                            style = MaterialTheme.typography.bodyLarge,
                            fontSize = 10.sp,
                            fontWeight = FontWeight.Bold
                        )
                    }

                }
            }
        }
    }
}

@Composable
private fun FlickeringText(
    modifier: Modifier,
    text: String
) {
    val infiniteTransition = rememberInfiniteTransition()

    val alpha by infiniteTransition.animateFloat(
        initialValue = 1f,
        targetValue = 0f,
        animationSpec = infiniteRepeatable(
            animation = tween(
                durationMillis = 200,
                easing = FastOutSlowInEasing
            ),
            repeatMode = RepeatMode.Reverse
        ),
        label = ""
    )
    Text(
        color = Color.Black.copy(
            alpha = alpha
        ),
        modifier = modifier,
        text = text
    )
}

@Preview
@Composable
private fun FactCardPreview(){
    FactCard(
        factModel = FactModel(
            fact = "Testing Fact",
            lengthInfo = LengthInfo(
                length = 151,
                shouldShowLength = true
            ),
            multipleCats = false,
            source = FactModelViewDataSource.Database
        )
    )
}

@Preview
@Composable
private fun FactCardLoadingPreview(){
    FactCard(
        factModel = null
    )
}