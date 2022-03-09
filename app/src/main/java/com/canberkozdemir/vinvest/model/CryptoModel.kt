package com.canberkozdemir.vinvest.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity
data class Coin(
    @SerializedName("name")
    val currency: String,
    @SerializedName("symbol")
    val symbol: String,
    @SerializedName("current_price")
    val currentPrice: String,
    @SerializedName("image")
    val imageUrl: String
) {
    @PrimaryKey(autoGenerate = true)
    var uuid: Int = 0
}
