package com.canberkozdemir.vinvest.viewmodel

import android.app.Application
import androidx.lifecycle.MutableLiveData
import com.canberkozdemir.vinvest.model.Coin
import com.canberkozdemir.vinvest.model.CoinMarketHistoryPrices
import com.canberkozdemir.vinvest.service.coin.CoinDatabase
import com.github.mikephil.charting.data.LineData
import kotlinx.coroutines.launch

class CoinDetailViewModel(application: Application) : BaseViewModel(application) {

    val coinId = MutableLiveData<String>()
    val coinLiveData = MutableLiveData<Coin>()
    val coinPriceHistory = MutableLiveData<List<CoinMarketHistoryPrices>>()
    val chartPriceHistory = MutableLiveData<List<CoinMarketHistoryPrices>>()

    fun getDataFromRoom(uuid: Int) {
        launch {
            val dao = CoinDatabase(getApplication()).coinDao()
            val coin = dao.getCoin(uuid)
            coinId.value = dao.getCoinId(uuid)
            coinLiveData.value = coin
            initChart()
        }
    }

    private fun initChart() {
        launch {
            val dao = CoinDatabase(getApplication()).coinMarketHistoryPricesDao()
            val priceHistories = dao.getHistoryById(coinId.value.toString())
            chartPriceHistory.postValue(priceHistories)
        }
    }

    private fun setPriceHistoryByCoin(coinId: String) {
        launch {
            val dao = CoinDatabase(getApplication()).coinMarketHistoryPricesDao()
            coinPriceHistory.value = dao.getAll()
        }
    }
}