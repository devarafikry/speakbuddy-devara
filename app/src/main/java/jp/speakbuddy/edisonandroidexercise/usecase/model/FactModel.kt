package jp.speakbuddy.edisonandroidexercise.usecase.model

data class FactModel(
    val fact: String,
    val lengthInfo: LengthInfo,
    val multipleCats: Boolean
)

sealed class FactModelViewData {
    data class Data(
        val factModel: FactModel
    ): FactModelViewData()

    data object Empty: FactModelViewData()
}



data class LengthInfo(
    val length: Int,
    val shouldShowLength: Boolean
)