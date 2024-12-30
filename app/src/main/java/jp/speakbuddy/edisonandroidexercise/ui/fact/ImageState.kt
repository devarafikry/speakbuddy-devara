package jp.speakbuddy.edisonandroidexercise.ui.fact

sealed class ImageState {
    data class Error(val msg: String): ImageState()
    data object Success: ImageState()
    data object Loading: ImageState()
}