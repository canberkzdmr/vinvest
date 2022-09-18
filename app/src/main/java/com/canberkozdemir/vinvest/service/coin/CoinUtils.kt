package com.canberkozdemir.vinvest.service.coin

import android.content.Context
import com.canberkozdemir.vinvest.model.CoinMarketHistoryPrices
import com.canberkozdemir.vinvest.util.formatTo5Decimals
import com.canberkozdemir.vinvest.util.unixTimeStampToDateString
import java.math.BigDecimal
import java.util.ArrayList

suspend fun storePriceHistoryInSQLite(prices: List<List<Double>>, coinId: String, application: Context) {
    val coinPriceHistoryList: ArrayList<CoinMarketHistoryPrices> = ArrayList()
    for (price in prices) {
        val priceDecimal = formatTo5Decimals(BigDecimal(price[1]).toPlainString()).toDouble()
        val date = unixTimeStampToDateString(BigDecimal(price[0].toString()).toPlainString())
        val coinPriceHistory = CoinMarketHistoryPrices(coinId, date, priceDecimal)
        coinPriceHistoryList.add(coinPriceHistory)
    }

    val dao = CoinDatabase(application).coinMarketHistoryPricesDao()
    dao.deleteAll()
    dao.insertAll(coinPriceHistoryList)
}