package com.canberkozdemir.vinvest.viewmodel

import android.app.Application
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.canberkozdemir.vinvest.model.*
import com.canberkozdemir.vinvest.service.coin.CoinAPIService
import com.canberkozdemir.vinvest.service.coin.CoinDatabase
import com.canberkozdemir.vinvest.service.coin.storePriceHistoryInSQLite
import com.canberkozdemir.vinvest.util.*
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.observers.DisposableSingleObserver
import io.reactivex.schedulers.Schedulers
import kotlinx.coroutines.launch
import java.math.BigDecimal

class MainViewModel(application: Application) : BaseViewModel(application) {
    private val coinAPIService = CoinAPIService()
    private val disposable = CompositeDisposable()
    private var customPreference = CustomSharedPreferences(getApplication())
    private var refreshTime = Minutes.TEN_MINUTES.millisecondValue

    private val getCoinMsg = MutableLiveData<Resource<Coin>>()
    val getCoinMessage: LiveData<Resource<Coin>>
        get() = getCoinMsg
    val coins = MutableLiveData<List<Coin>>()
    val coinError = MutableLiveData<Boolean>()
    val coinLoadingProgress = MutableLiveData<Boolean>()

    fun refreshData() {
        val updateTime = customPreference.getTime()

        if (updateTime != 0L && System.nanoTime() - updateTime!! < refreshTime!!.toLong())
            getDataFromSQLite()
        else {
            getCoinInfoFromAPI()
            getCoinMarketHistory()
        }

    }

    private fun getDataFromSQLite() {
        coinLoadingProgress.value = true
        launch {
            val coins = CoinDatabase(getApplication()).coinDao().getAllCoins()
            showCoins(coins)
            getCoinMsg.postValue(Resource.success("Data retrieved from local!", null))
            Log.i("LOCAL", "getDataFromSQLite: Data retrieved from local")
        }
    }

    fun refreshDataFromAPI() {
        getCoinInfoFromAPI()
        getCoinMarketHistory()
        getCoinMsg.postValue(Resource.success("Coins updated online!", null))
    }

    private fun getCoinMarketHistory() {
        launch {
            val coinIds = CoinDatabase(getApplication()).coinDao().getAllCoinIds()
            for (coinId in coinIds) {
                disposable.add(
                    coinAPIService.getCoinMarketHistoryByCoin(coinId)
                        .subscribeOn(Schedulers.newThread())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribeWith(object : DisposableSingleObserver<CoinMarketHistory>() {
                            override fun onSuccess(t: CoinMarketHistory) {
                                storeCoinMarketHistoryInSQLite(t, coinId)
                                Log.i(
                                    "HISTORY",
                                    "onSuccess: Coin Market History Retrieved Successfully"
                                )
                            }

                            override fun onError(e: Throwable) {
                                coinLoadingProgress.value = false
                                coinError.value = true
                                e.printStackTrace()
                                Log.e("HISTORY", "onError: Could not retrieve coin market history!")
                            }
                        })
                )
            }
        }
    }

    private fun getCoinInfoFromAPI() {
        coinLoadingProgress.value = true
        disposable.add(
            coinAPIService.getCoinInfo()
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(object : DisposableSingleObserver<List<Coin>>() {
                    override fun onSuccess(t: List<Coin>) {
                        storeCoinInfoInSQLite(convertPricesTo5Decimal(t))
                    }

                    override fun onError(e: Throwable) {
                        coinLoadingProgress.value = false
                        coinError.value = true
                        e.printStackTrace()
                    }
                })
        )
    }

    private fun storeCoinMarketHistoryInSQLite(
        coinHistory: CoinMarketHistory,
        coinId: String
    ) {

        launch {
            storePriceHistoryInSQLite(coinHistory.prices, coinId, getApplication())
        }
        storeMarketCapsHistoryInSQLite(coinHistory.market_caps, coinId)
        storeTotalVolumeHistoryInSQLite(coinHistory.total_volumes, coinId)
    }

    private fun storeTotalVolumeHistoryInSQLite(totalVolumes: List<List<Double>>, coinId: String) {
        val coinMarketTotalVolumesList: ArrayList<CoinMarketHistoryTotalVolume> = ArrayList()
        for (totalVolume in totalVolumes) {
            val totalVolumeDecimal = BigDecimal(totalVolume[1]).setScale(3, BigDecimal.ROUND_HALF_EVEN).toPlainString().toDouble()
            val date = unixTimeStampToDateString(BigDecimal(totalVolume[0]).toPlainString())
            val coinTotalVolumeHistory = CoinMarketHistoryTotalVolume(coinId, date, totalVolumeDecimal)
            coinMarketTotalVolumesList.add(coinTotalVolumeHistory)
        }
        launch {
            val dao = CoinDatabase(getApplication()).coinMarketHistoryTotalVolumeDao()
            dao.insertAll(coinMarketTotalVolumesList)
        }
    }

    private fun storeMarketCapsHistoryInSQLite(marketCaps: List<List<Double>>, coinId: String) {
        val coinMarketCapsHistoryList: ArrayList<CoinMarketHistoryMarketCaps> = ArrayList()
        for (marketCap in marketCaps) {
//            val marketCapDecimal = formatTo5Decimals(BigDecimal(marketCap[1]).toPlainString()).toDouble()
            val marketCapDecimal = BigDecimal(marketCap[1]).setScale(3, BigDecimal.ROUND_HALF_EVEN).toPlainString().toDouble()
            val date = unixTimeStampToDateString(BigDecimal(marketCap[0]).toPlainString())
            val coinMarketCapHistory = CoinMarketHistoryMarketCaps(coinId, date, marketCapDecimal)
            coinMarketCapsHistoryList.add(coinMarketCapHistory)
        }
        launch {
            val dao = CoinDatabase(getApplication()).coinMarketHistoryMarketCapsDao()
            dao.insertAll(coinMarketCapsHistoryList)
        }
    }

/*    private fun storePriceHistoryInSQLite(prices: List<List<Double>>, coinId: String) {
        val coinPriceHistoryList: ArrayList<CoinMarketHistoryPrices> = ArrayList()
        for (price in prices) {
            val priceDecimal = formatTo5Decimals(BigDecimal(price[1]).toPlainString()).toDouble()
            val date = unixTimeStampToDateString(BigDecimal(price[0].toString()).toPlainString())
            val coinPriceHistory = CoinMarketHistoryPrices(coinId, date, priceDecimal)
            coinPriceHistoryList.add(coinPriceHistory)
        }
        launch {
            val dao = CoinDatabase(getApplication()).coinMarketHistoryPricesDao()
            dao.deleteAll()
            dao.insertAll(coinPriceHistoryList)
        }
    }*/

    private fun storeCoinInfoInSQLite(coinList: List<Coin>) {
        launch {
            val dao = CoinDatabase(getApplication()).coinDao()
            dao.deleteAllCoins()
            val coinListLong = dao.insertAll(coinList)
            var i = 0
            while (i < coinList.size) {
                coinList[i].uuid = coinListLong[i].toInt()
                i += 1
            }
            showCoins(coinList)
        }
        customPreference.saveTime(System.nanoTime())
    }

    private fun convertPricesTo5Decimal(coins: List<Coin>): List<Coin> {
        for (i in coins.indices) {
            if (coins[i].currentPrice.isNotEmpty())
                coins[i].currentPrice = formatTo5Decimals(coins[i].currentPrice)
        }
        return coins
    }

    private fun showCoins(coinList: List<Coin>) {
        coinLoadingProgress.value = false
        coinError.value = false
        coins.value = coinList
    }

    /**
     * hafizayi verimli kullanmak icin temizlemek gerekiyor
     */
    override fun onCleared() {
        super.onCleared()
        disposable.clear()
    }
}