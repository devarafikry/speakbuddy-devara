package jp.speakbuddy.edisonandroidexercise.usecase.model

data class FactModel(
    val fact: String,
    val lengthInfo: LengthInfo,
    val multipleCats: Boolean,
    val source: FactModelViewDataSource,
    val imgUrl: String = ""
)

sealed class FactModelViewData {
    data class Data(
        val factModel: FactModel
    ): FactModelViewData()

    data object Empty: FactModelViewData()

    data object Loading: FactModelViewData()

    data class Error(val errMessage: String): FactModelViewData()
}

sealed class FactModelViewDataSource {
    data object Database: FactModelViewDataSource() {
        override fun toString(): String = "Database"
    }
    data object Cloud: FactModelViewDataSource() {
        override fun toString(): String = "Cloud"
    }
}


data class LengthInfo(
    val length: Int,
    val shouldShowLength: Boolean
)