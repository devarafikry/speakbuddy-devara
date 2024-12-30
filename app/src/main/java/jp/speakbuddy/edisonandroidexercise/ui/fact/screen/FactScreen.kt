package jp.speakbuddy.edisonandroidexercise.ui.fact.screen

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.scaleIn
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.imageLoader
import coil.util.DebugLogger
import jp.speakbuddy.edisonandroidexercise.R
import jp.speakbuddy.edisonandroidexercise.ui.fact.ImageState
import jp.speakbuddy.edisonandroidexercise.ui.fact.component.card.EmptyCard
import jp.speakbuddy.edisonandroidexercise.ui.fact.component.card.ErrorCard
import jp.speakbuddy.edisonandroidexercise.ui.fact.component.card.FactCard
import jp.speakbuddy.edisonandroidexercise.ui.fact.component.common.MultipleCatsStempel
import jp.speakbuddy.edisonandroidexercise.ui.theme.EdisonAndroidExerciseTheme
import jp.speakbuddy.edisonandroidexercise.usecase.model.FactModel
import jp.speakbuddy.edisonandroidexercise.usecase.model.FactModelViewData
import jp.speakbuddy.edisonandroidexercise.usecase.model.FactModelViewDataSource
import jp.speakbuddy.edisonandroidexercise.usecase.model.LengthInfo
import kotlinx.coroutines.delay

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
        var buttonContainerColor = ButtonDefaults.buttonColors(
            containerColor = Color.Black
        )

        if (factModelViewData is FactModelViewData.Data) {
            val imageUrl = remember { mutableStateOf<String>(factModelViewData.factModel.imgUrl) }
            val imageState = remember { mutableStateOf<ImageState>(ImageState.Loading) }
            CatImage(imageState, imageUrl.value)
        } else {
            Box(
                modifier = Modifier.size(
                    width = 200.dp, height = 150.dp
                )
            )
        }

        Box(
            Modifier.size(
                width = 360.dp, height = 260.dp
            )
        ) {
            when (factModelViewData) {
                is FactModelViewData.Data -> {
                    CatFactComponent(
                        modifier = Modifier.align(Alignment.Center),
                        factModelViewData = factModelViewData
                    )
                    val isMultipleCatsVisible = remember { mutableStateOf(false) }
                    LaunchedEffect(Unit) {
                        delay(10)
                        isMultipleCatsVisible.value = true
                    }

                    this@Column.AnimatedVisibility(
                        visible = isMultipleCatsVisible.value,
                        enter = scaleIn(
                            initialScale = 0.1f,
                            animationSpec = tween(durationMillis = 500)
                        )
                    ) {
                        MultipleCatsStempel(
                            modifier = Modifier
                        )
                    }
                    promptText = stringResource(R.string.fact_screen_default_prompt_text_button)
                }
                is FactModelViewData.Empty -> {
                    EmptyCard(
                        modifier = Modifier.align(Alignment.Center),)
                    promptText = stringResource(R.string.fact_screen_empty_state_prompt_text_button)
                    buttonContainerColor = ButtonDefaults.buttonColors()
                }
                is FactModelViewData.Error -> {
                    ErrorCard(
                        modifier = Modifier.align(Alignment.Center),
                        errorModel = factModelViewData
                    )
                    promptText = stringResource(R.string.fact_screen_error_state_prompt_text_button)
                    buttonContainerColor = ButtonDefaults.buttonColors(
                        containerColor = Color.Red
                    )
                }

                FactModelViewData.Loading -> {
                    CatFactComponent(
                        modifier = Modifier.align(Alignment.Center),
                        factModelViewData = factModelViewData,
                    )
                }
            }
        }

        val onClick = {
            onUpdateFact.invoke()
        }

        Button(
            onClick = onClick,
            colors = buttonContainerColor
        ) {
            Text(text = promptText)
        }
    }
}

@Composable
private fun CatImage(
    imageState: MutableState<ImageState>,
    imageUrl: String
    ) {
    Card(
        Modifier.size(
            width = 200.dp, height = 150.dp
        )
    ) {
        Box {
            val imageLoader = LocalContext.current.imageLoader.newBuilder()
                .logger(DebugLogger())
                .build()

            AsyncImage(
                modifier = Modifier.size(
                    width = 200.dp, height = 150.dp
                ),
                imageLoader = imageLoader,
                model = imageUrl,
                contentDescription = null,
                contentScale = ContentScale.Crop,
                onLoading = {
                    imageState.value = ImageState.Loading
                },
                onSuccess = {
                    imageState.value = ImageState.Success
                },
                onError = {
                    imageState.value =
                        ImageState.Error(msg = it.result.throwable.message.toString())
                }
            )
            Text(
                modifier = Modifier.padding(12.dp),
                text = when (imageState.value) {
                    is ImageState.Loading -> "Loading..."
                    is ImageState.Error -> {
                        "Image error: ${(imageState.value as ImageState.Error).msg}"
                    }

                    is ImageState.Success -> ""
                }
            )
        }
    }
}

@Composable
private fun CatFactComponent(
    modifier: Modifier = Modifier,
    factModelViewData: FactModelViewData,
) {
    FactCard(
        modifier = modifier,
        factModel = if (factModelViewData is FactModelViewData.Data) {
            factModelViewData.factModel
        } else null,
    )
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
                        shouldShowLength = false,
                    ),
                    multipleCats = true,
                    source = FactModelViewDataSource.Database
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

@Preview
@Composable
private fun FactScreenErrorPreview() {
    EdisonAndroidExerciseTheme {
        FactScreen(
            factModelViewData = FactModelViewData.Error("Network error."),
            onUpdateFact = {}
        )
    }
}

@Preview
@Composable
private fun FactScreenLoadingPreview() {
    EdisonAndroidExerciseTheme {
        FactScreen(
            factModelViewData = FactModelViewData.Loading,
            onUpdateFact = {}
        )
    }
}



