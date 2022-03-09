package com.canberkozdemir.vinvest.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class User(
    val userName: String,
    val password: String,
    val gender: Boolean,
    val phoneNumber: Int,
    val email: String
) {
    @PrimaryKey(autoGenerate = true)
    var uuid: Int = 0
}