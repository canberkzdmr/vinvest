package com.canberkozdemir.vinvest.service.coin

import com.canberkozdemir.vinvest.model.Coin
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Query
import retrofit2.http.Url

interface CoinAPI {
    //GET, POST, UPDATE
    //BASE_URL = "https://api.coingecko.com/api/v3/"

    @GET( "https://api.coingecko.com/api/v3/coins/markets?vs_currency=usd&order=market_cap_desc&per_page=100&sparkline=false")
    fun getData(@Query("page") pageNumber: String): Single<List<Coin>>
}