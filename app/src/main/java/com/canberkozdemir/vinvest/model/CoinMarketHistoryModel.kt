package com.canberkozdemir.vinvest.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

data class CoinMarketHistory(
    @SerializedName("prices") val prices: List<List<Double>>,
    @SerializedName("market_caps") val market_caps: List<List<Double>>,
    @SerializedName("total_volumes") val total_volumes: List<List<Double>>
)

@Entity(tableName = "COIN_HISTORY_PRICES")
data class CoinMarketHistoryPrices(
    var coinId:String,
    var time: String,
    var price: Double
) {
    @PrimaryKey(autoGenerate = true)
    var uuid: Int = 0
}

@Entity(tableName = "COIN_HISTORY_MARKET_CAPS")
data class CoinMarketHistoryMarketCaps(
    var coinId:String,
    var time: String,
    var marketCap: Double
) {
    @PrimaryKey(autoGenerate = true)
    var uuid: Int = 0
}

@Entity(tableName = "COIN_HISTORY_TOTAL_VOLUME")
data class CoinMarketHistoryTotalVolume(
    var coinId:String,
    var time: String,
    var totalVolume: Double
) {
    @PrimaryKey(autoGenerate = true)
    var uuid: Int = 0
}
