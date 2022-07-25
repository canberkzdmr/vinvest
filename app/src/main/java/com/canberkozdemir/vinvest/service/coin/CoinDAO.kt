package com.canberkozdemir.vinvest.service.coin

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.canberkozdemir.vinvest.model.Coin
import com.canberkozdemir.vinvest.service.BaseDAO

@Dao
interface CoinDAO: BaseDAO<Coin> {
    @Query("select * from Coin")
    suspend fun getAllCoins(): List<Coin>

    @Query("select * from Coin where uuid = :coinId")
    suspend fun getCoin(coinId: Int): Coin

    @Query("delete from Coin")
    suspend fun deleteAllCoins()

    @Query("select coinId from Coin where uuid = :coinId")
    suspend fun getCoinId(coinId: String): String

    @Query("select coinId from Coin where coinId in ('bitcoin', 'ethereum', 'binancecoin')group by coinId")
    suspend fun getAllCoinIds(): List<String>
}