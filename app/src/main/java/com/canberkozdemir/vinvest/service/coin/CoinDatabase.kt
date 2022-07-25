package com.canberkozdemir.vinvest.service.coin

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.canberkozdemir.vinvest.model.Coin
import com.canberkozdemir.vinvest.model.CoinMarketHistoryMarketCaps
import com.canberkozdemir.vinvest.model.CoinMarketHistoryPrices
import com.canberkozdemir.vinvest.model.CoinMarketHistoryTotalVolume
import com.canberkozdemir.vinvest.service.coinHistory.CoinMarketHistoryMarketCapsDAO
import com.canberkozdemir.vinvest.service.coinHistory.CoinMarketHistoryPricesDAO
import com.canberkozdemir.vinvest.service.coinHistory.CoinMarketHistoryTotalVolumeDAO

@Database(
    entities = [Coin::class, CoinMarketHistoryPrices::class, CoinMarketHistoryMarketCaps::class, CoinMarketHistoryTotalVolume::class],
    version = 7
)
abstract class CoinDatabase : RoomDatabase() {

    abstract fun coinDao(): CoinDAO
    abstract fun coinMarketHistoryPricesDao(): CoinMarketHistoryPricesDAO
    abstract fun coinMarketHistoryMarketCapsDao(): CoinMarketHistoryMarketCapsDAO
    abstract fun coinMarketHistoryTotalVolumeDao(): CoinMarketHistoryTotalVolumeDAO

    //Singleton
    companion object {

        @Volatile
        private var instance: CoinDatabase? = null

        private val lock = Any()

        operator fun invoke(context: Context) = instance ?: synchronized(lock) {
            instance ?: makeDatabase(context).also {
                instance = it
            }
        }

        private fun makeDatabase(context: Context) = Room.databaseBuilder(
            context.applicationContext,
            CoinDatabase::class.java,
            "coindatabase"
        ).build()

    }

}