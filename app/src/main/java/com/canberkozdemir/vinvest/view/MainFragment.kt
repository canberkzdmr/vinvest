package com.canberkozdemir.vinvest.view

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.canberkozdemir.vinvest.R
import com.canberkozdemir.vinvest.adapter.CoinAdapter
import com.canberkozdemir.vinvest.viewmodel.MainViewModel
import kotlinx.android.synthetic.main.main_fragment.*

class MainFragment : Fragment() {
    private lateinit var viewModel: MainViewModel
    private val coinAdapter = CoinAdapter(arrayListOf())

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.main_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(this).get(MainViewModel::class.java)
        viewModel.refreshData()

        coinList.layoutManager = LinearLayoutManager(context)
        coinList.adapter = coinAdapter

        swipeRefreshLayout.setOnRefreshListener {
            coinList.visibility = View.GONE
            errorMessage.visibility = View.GONE
            coinsLoading.visibility = View.VISIBLE
            viewModel.refreshDataFromAPI()
            swipeRefreshLayout.isRefreshing = false
        }

        observeLiveData()
    }

    private fun observeLiveData() {
        viewModel.coins.observe(viewLifecycleOwner) { coins ->
            coins?.let {
                llUserInfo.visibility = View.VISIBLE
                coinList.visibility = View.VISIBLE
                coinAdapter.updateCoinList(coins)
            }
        }

        viewModel.coinError.observe(viewLifecycleOwner) { error ->
            error?.let {
                if (it)
                    errorMessage.visibility = View.VISIBLE
                else
                    errorMessage.visibility = View.GONE
            }
        }

        viewModel.coinLoadingProgress.observe(viewLifecycleOwner) { loading ->
            loading?.let {
                if (it) {
                    coinsLoading.visibility = View.VISIBLE
                    llUserInfo.visibility = View.GONE
                    coinList.visibility = View.GONE
                    errorMessage.visibility = View.GONE
                } else {
                    coinsLoading.visibility = View.GONE
                }
            }
        }
    }

}