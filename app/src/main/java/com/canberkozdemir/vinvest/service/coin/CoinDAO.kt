package com.canberkozdemir.vinvest.service.coin

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.canberkozdemir.vinvest.model.Coin

@Dao
interface CoinDAO {
    @Insert
    suspend fun insertAll(vararg coins: Coin): List<Long>

    @Query("select * from Coin")
    suspend fun getAllCoins(): List<Coin>

    @Query("select * from Coin where uuid = :coinId")
    suspend fun getCoin(coinId: Int): Coin

    @Query("delete from Coin")
    suspend fun deleteAllCoins()
}