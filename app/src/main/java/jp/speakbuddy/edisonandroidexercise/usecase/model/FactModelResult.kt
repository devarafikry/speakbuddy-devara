package jp.speakbuddy.edisonandroidexercise.usecase.model

sealed class FactModelResult {
    data class Success(
        val factModel: FactModel
    ): FactModelResult()
    data class Error(
        val errMessage: String,
    ): FactModelResult()
}
