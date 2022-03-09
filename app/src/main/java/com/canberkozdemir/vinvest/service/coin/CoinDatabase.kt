package com.canberkozdemir.vinvest.service.coin

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.canberkozdemir.vinvest.model.Coin

@Database(entities = arrayOf(Coin::class), version = 2)
abstract class CoinDatabase : RoomDatabase() {

    abstract fun coinDao(): CoinDAO

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