package jp.speakbuddy.edisonandroidexercise.ui.fact.component.card

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.runtime.Composable
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Text
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalInspectionMode
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import jp.speakbuddy.edisonandroidexercise.R

@Composable
fun EmptyCard(modifier: Modifier = Modifier) {
    Box (
        modifier = modifier.then(Modifier.size(300.dp, 200.dp))
    ) {
        if (!LocalInspectionMode.current) {
            AsyncImage(
                modifier = Modifier.fillMaxSize(),
                model = "https://www.google.com/url?sa=i&url=https%3A%2F%2Fshopee.ph%2Flist%2Fcat%2520meme&psig=AOvVaw0wL5cu-WhHuYq8sP1Ee_KP&ust=1734982567164000&source=images&cd=vfe&opi=89978449&ved=0CBQQjRxqFwoTCMjU1--PvIoDFQAAAAAdAAAAABAQ",
                contentDescription = "Cat Meme",
                contentScale = ContentScale.Crop,
            )
        } else {
            Box(
                modifier = Modifier.background(
                    color = Color.Gray
                ).fillMaxSize()
            )
        }
        Text(
            modifier = Modifier.align(
                Alignment.Center
            ).background(
                color = Color.White
            ).padding(4.dp),
            text = stringResource(R.string.empty_card_prompt)
        )
    }
}

@Preview
@Composable
private fun EmptyCardPreview(){
    EmptyCard()
}