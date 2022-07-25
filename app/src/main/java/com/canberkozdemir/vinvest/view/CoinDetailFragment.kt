package com.canberkozdemir.vinvest.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.canberkozdemir.vinvest.R
import com.canberkozdemir.vinvest.databinding.CoinDetailFragmentBinding
import com.canberkozdemir.vinvest.databinding.MainFragmentBinding
import com.canberkozdemir.vinvest.viewmodel.CoinDetailViewModel

/**
 * A simple [Fragment] subclass.
 * Use the [CoinDetailFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class CoinDetailFragment : Fragment() {

    private lateinit var viewModel: CoinDetailViewModel
    private var coinUuid = 0
    private lateinit var dataBinding: CoinDetailFragmentBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        dataBinding =
            DataBindingUtil.inflate(inflater, R.layout.coin_detail_fragment, container, false)
        return dataBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        arguments?.let {
            coinUuid = CoinDetailFragmentArgs.fromBundle(it).coinUuid
        }
        viewModel = ViewModelProvider(this).get(CoinDetailViewModel::class.java)
        viewModel.getDataFromRoom(coinUuid)

        observeLiveData()
    }

    private fun observeLiveData() {
        viewModel.coinLiveData.observe(viewLifecycleOwner, Observer { coin ->
            coin?.let {
                dataBinding.selectedCoin = it
            }
        })
    }
}