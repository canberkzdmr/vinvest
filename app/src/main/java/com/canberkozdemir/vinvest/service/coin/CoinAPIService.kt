package com.canberkozdemir.vinvest.service.coin

import com.canberkozdemir.vinvest.model.Coin
import com.canberkozdemir.vinvest.model.CoinMarketHistory
import io.reactivex.Single
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

class CoinAPIService {
    private val BASE_URL = "https://api.coingecko.com/api/v3/"
    private val api = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
        .build()
        .create(CoinAPI::class.java)

    fun getCoinInfo(): Single<List<Coin>> {
        return api.getCoinInfo("1")
    }

    fun getCoinMarketHistoryByCoin(coinId: String): Single<CoinMarketHistory> {
        return api.getMarketHistoryByCoin(coinId)
    }
}