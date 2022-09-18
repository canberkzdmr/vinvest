package com.canberkozdemir.vinvest.service.coinHistory

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.canberkozdemir.vinvest.model.CoinMarketHistory
import com.canberkozdemir.vinvest.model.CoinMarketHistoryMarketCaps
import com.canberkozdemir.vinvest.model.CoinMarketHistoryPrices
import com.canberkozdemir.vinvest.model.CoinMarketHistoryTotalVolume
import com.canberkozdemir.vinvest.service.BaseDAO

@Dao
interface CoinMarketHistoryPricesDAO : BaseDAO<CoinMarketHistoryPrices> {
    @Query("delete from coin_history_prices")
    suspend fun deleteAll()

    @Query("select * from coin_history_prices")
    suspend fun getAll(): List<CoinMarketHistoryPrices>

    @Query("select * from coin_history_prices where coinId = :coinId")
    suspend fun getHistoryById(coinId: String): List<CoinMarketHistoryPrices>

/*    @Query("select * from coin_history_prices where coinId = :coinId")
    suspend fun getCoinPricesHistory(coinId: Int): CoinMarketHistoryPrices

    @Query("delete from coin_history_prices")
    suspend fun deleteAllMarketHistory()*/
}

@Dao
interface CoinMarketHistoryMarketCapsDAO: BaseDAO<CoinMarketHistoryMarketCaps> {
    @Query("delete from coin_history_market_caps")
    suspend fun deleteAll()

    /*@Query("select * from coin_history_market_caps")
    suspend fun getAll(): List<CoinMarketHistoryMarketCaps>

    @Query("select * from coin_history_market_caps where coinId = :coinId")
    suspend fun getCoinMarketCapsHistory(coinId: Int): CoinMarketHistoryMarketCaps

    @Query("delete from coin_history_market_caps")
    suspend fun deleteAllMarketHistory()*/
}

@Dao
interface CoinMarketHistoryTotalVolumeDAO: BaseDAO<CoinMarketHistoryTotalVolume> {
    @Query("delete from coin_history_total_volume")
    suspend fun deleteAll()

    /*@Query("select * from coin_history_total_volume")
    suspend fun getAll(): List<CoinMarketHistoryTotalVolume>

    @Query("select * from coin_history_total_volume where coinId = :coinId")
    suspend fun getCoinTotalVolumeHistory(coinId: Int): CoinMarketHistoryTotalVolume

    @Query("delete from coin_history_total_volume")
    suspend fun deleteAllTotalVolumeHistory()*/
}