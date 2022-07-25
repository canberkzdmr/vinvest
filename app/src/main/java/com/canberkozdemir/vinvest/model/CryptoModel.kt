package com.canberkozdemir.vinvest.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity (tableName = "COIN")
data class Coin(
    @SerializedName("id")
    val coinId: String,
    @SerializedName("name")
    val currency: String,
    @SerializedName("symbol")
    val symbol: String,
    @SerializedName("current_price")
    var currentPrice: String,
    @SerializedName("image")
    val imageUrl: String,
    @SerializedName("market_cap")
    val marketCap: String,
    @SerializedName("market_cap_rank")
    val marketCapRank: String,
    @SerializedName("total_volume")
    val totalVolume: String,
    @SerializedName("high_24h")
    val high24H: String,
    @SerializedName("low_24h")
    val low24H: String,
    @SerializedName("price_change_24h")
    val priceChange24H: String,
    @SerializedName("price_change_percentage_24h")
    val priceChangePercentage24H: String,
    @SerializedName("last_updated")
    val lastUpdated: String,
) {
    @PrimaryKey(autoGenerate = true)
    var uuid: Int = 0
}
