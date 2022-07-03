package com.canberkozdemir.vinvest.viewmodel

import android.app.Application
import androidx.lifecycle.MutableLiveData
import com.canberkozdemir.vinvest.model.Coin
import com.canberkozdemir.vinvest.service.coin.CoinDatabase
import kotlinx.coroutines.launch

class CoinDetailViewModel(application: Application) : BaseViewModel(application) {
    val coinLiveData = MutableLiveData<Coin>()

    fun getDataFromRoom(uuid: Int) {
        launch {
            val dao = CoinDatabase(getApplication()).coinDao()
            val coin = dao.getCoin(uuid)
            coinLiveData.value = coin
        }
    }
}