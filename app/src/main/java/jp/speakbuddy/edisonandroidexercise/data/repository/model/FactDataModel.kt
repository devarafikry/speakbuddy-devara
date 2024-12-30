package jp.speakbuddy.edisonandroidexercise.data.repository.model

data class FactDataModel(
    val fact: String = "",
    val length: Int,
    val type: FactType,
    val datasource: FactDataSourceType? = null
)

sealed class FactType {
    object CatFact: FactType()
}

sealed class FactDataSourceType {
    data class Database(val lastUpdateMillis: Long): FactDataSourceType() {
        override fun toString(): String = "Database"
    }
    data class Network(val apiHost: String): FactDataSourceType() {
        override fun toString(): String = "Network"
    }
}