package jp.speakbuddy.edisonandroidexercise.ui.fact

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.scaleIn
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import jp.speakbuddy.edisonandroidexercise.R
import jp.speakbuddy.edisonandroidexercise.ui.theme.EdisonAndroidExerciseTheme
import jp.speakbuddy.edisonandroidexercise.usecase.model.FactModel
import jp.speakbuddy.edisonandroidexercise.usecase.model.FactModelViewData
import jp.speakbuddy.edisonandroidexercise.usecase.model.LengthInfo

@Composable
fun FactScreen(
    factModelViewData: FactModelViewData,
    onUpdateFact: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(
            space = 16.dp,
            alignment = Alignment.CenterVertically
        )
    ) {
        var promptText = stringResource(R.string.fact_screen_default_prompt_text_button)
        when (factModelViewData) {
            is FactModelViewData.Data -> {
                CatFactComponent(factModelViewData)
                promptText = stringResource(R.string.fact_screen_default_prompt_text_button)
            }
            is FactModelViewData.Empty -> {
                EmptyCard()
                promptText = stringResource(R.string.fact_screen_empty_state_prompt_text_button)
            }
        }

        val onClick = {
            onUpdateFact.invoke()
        }

        Button(onClick = onClick) {
            Text(text = promptText)
        }
    }
}

@Composable
private fun CatFactComponent(
    factModelViewData: FactModelViewData.Data,
) {
    val isMultipleCatsVisible = remember {
        mutableStateOf(
            factModelViewData.factModel.multipleCats
        )
    }

    Box {
        FactCard(
            factModel = factModelViewData.factModel
        )
        AnimatedVisibility(
            visible = isMultipleCatsVisible.value,
            enter = scaleIn(
                initialScale = 0.1f,
                animationSpec = tween(durationMillis = 500)
            )
        ) {
            MultipleCatsStempel(
                modifier = Modifier
                    .align(Alignment.TopEnd)
                    .offset(
                        y = -10.dp
                    )
                    .graphicsLayer {
                        rotationZ = 20f
                    }
            )
        }
    }
}

@Preview
@Composable
private fun FactScreenPreview() {
    EdisonAndroidExerciseTheme {
        FactScreen(
            factModelViewData = FactModelViewData.Data(
                factModel = FactModel(
                    fact = "Testing Fact",
                    lengthInfo = LengthInfo(
                        length = 11,
                        shouldShowLength = false
                    ),
                    multipleCats = false
                )
            ),
            onUpdateFact = {}
        )
    }
}

@Preview
@Composable
private fun FactScreenNoDataPreview() {
    EdisonAndroidExerciseTheme {
        FactScreen(
            factModelViewData = FactModelViewData.Empty,
            onUpdateFact = {}
        )
    }
}
