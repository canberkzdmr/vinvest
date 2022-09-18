package com.canberkozdemir.vinvest.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class UserWallet(
    val userId: Int,
    val currency: String,
    val amount: Long,
) {
    @PrimaryKey(autoGenerate = true)
    var uuid: Int = 0
}