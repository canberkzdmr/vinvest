package com.canberkozdemir.vinvest.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.*

@Entity
data class UserWalletHistory(
    val userId: Int,
    val currency: String,
    val buy_amount: Long,
    val sell_amount: Long,
    val trade_date: Date
) {
    @PrimaryKey(autoGenerate = true)
    var uuid: Int = 0
}