package com.canberkozdemir.vinvest.service.coin

import com.canberkozdemir.vinvest.model.Coin
import com.canberkozdemir.vinvest.model.CoinMarketHistory
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface CoinAPI
{
    //GET, POST, UPDATE
    //BASE_URL = "https://api.coingecko.com/api/v3/"

    @GET( "https://api.coingecko.com/api/v3/coins/markets?vs_currency=usd&order=market_cap_desc&per_page=100&sparkline=false")
    fun getCoinInfo(@Query("page") pageNumber: String): Single<List<Coin>>

    @GET("https://api.coingecko.com/api/v3/coins/{coin}/market_chart?vs_currency=usd&days=2")
    fun getMarketHistoryByCoin(@Path("coin") coinId: String): Single<CoinMarketHistory>
}