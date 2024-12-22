package jp.speakbuddy.edisonandroidexercise.data.repository.model

sealed class FactDataModelResult() {
    data class Success(
        val factDataModel: FactDataModel
    ): FactDataModelResult()
    data class Error(
        val errMessage: String,
        val dataSourceType: FactDataSourceType
    ): FactDataModelResult()
}
