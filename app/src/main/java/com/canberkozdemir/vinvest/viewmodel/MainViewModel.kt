package com.canberkozdemir.vinvest.viewmodel

import android.app.Application
import android.widget.Toast
import androidx.lifecycle.MutableLiveData
import com.canberkozdemir.vinvest.model.Coin
import com.canberkozdemir.vinvest.service.coin.CoinAPIService
import com.canberkozdemir.vinvest.service.coin.CoinDatabase
import com.canberkozdemir.vinvest.util.CustomSharedPreferences
import com.canberkozdemir.vinvest.util.Minutes
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.observers.DisposableSingleObserver
import io.reactivex.schedulers.Schedulers
import kotlinx.coroutines.launch

class MainViewModel(application: Application) : BaseViewModel(application) {
    private val coinAPIService = CoinAPIService()
    private val disposable = CompositeDisposable()
    private var customPreference = CustomSharedPreferences(getApplication())
    private var refreshTime = Minutes.TEN_MINUTES.millisecondValue

    val coins = MutableLiveData<List<Coin>>()
    val coinError = MutableLiveData<Boolean>()
    val coinLoadingProgress = MutableLiveData<Boolean>()

    fun refreshData() {
        val updateTime = customPreference.getTime()

        if (updateTime != 0L && System.nanoTime() - updateTime!! < refreshTime!!.toLong())
            getDataFromSQLite()
        else
            getDataFromAPI()

    }

    private fun getDataFromSQLite() {
        coinLoadingProgress.value = true
        launch {
            val coins = CoinDatabase(getApplication()).coinDao().getAllCoins()
            showCoins(coins)
            Toast.makeText(getApplication(), "Coin data retrieved from local!", Toast.LENGTH_LONG).show()
        }
    }

    fun refreshDataFromAPI() {
        getDataFromAPI()
    }

    private fun getDataFromAPI() {
        coinLoadingProgress.value = true
        disposable.add(
            coinAPIService.getData()
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(object : DisposableSingleObserver<List<Coin>>() {
                    override fun onSuccess(t: List<Coin>) {
                        storeInSQLite(t)
                        Toast.makeText(getApplication(), "Coins updated online!", Toast.LENGTH_LONG)
                            .show()
                    }

                    override fun onError(e: Throwable) {
                        coinLoadingProgress.value = false
                        coinError.value = true
                        e.printStackTrace()
                    }
                })
        )
    }

    private fun storeInSQLite(coinList: List<Coin>) {
        launch {
            val dao = CoinDatabase(getApplication()).coinDao()
            dao.deleteAllCoins()
            val coinListLong = dao.insertAll(*coinList.toTypedArray())
            var i = 0
            while (i < coinList.size) {
                coinList[i].uuid = coinListLong[i].toInt()
                i += 1
            }
            showCoins(coinList)
        }
        customPreference.saveTime(System.nanoTime())
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