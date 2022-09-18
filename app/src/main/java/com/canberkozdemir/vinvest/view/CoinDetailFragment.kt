package com.canberkozdemir.vinvest.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.canberkozdemir.vinvest.R
import com.canberkozdemir.vinvest.databinding.CoinDetailFragmentBinding
import com.canberkozdemir.vinvest.viewmodel.CoinDetailViewModel
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.LineDataSet
import kotlinx.android.synthetic.main.coin_detail_fragment.*

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
        viewModel.chartPriceHistory.observe(viewLifecycleOwner) {
            val lineList = ArrayList<Entry>()
            val xValues = ArrayList<String>()
            for ((index, priceHistory) in it.withIndex()) {
                xValues.add(priceHistory.time)
                lineList.add(Entry(priceHistory.price.toFloat(), index))
            }

            val lineDataset = LineDataSet(lineList, "US Dollar")
            lineDataset.color = ContextCompat.getColor(requireContext(), R.color.coinColor)
            lineDataset.circleRadius = 0f
            lineDataset.setDrawFilled(true)
            lineDataset.fillColor = ContextCompat.getColor(requireContext(), R.color.saveEyesWhite)
            lineDataset.fillAlpha = 30
            lineDataset.valueTextColor = ContextCompat.getColor(requireContext(), R.color.saveEyesWhite)
            lineDataset.valueTextSize = 10F
            setLineData(LineData(xValues, lineDataset))
        }
    }

    private fun setLineData(lineData: LineData) {
        lineChart.data = lineData
        lineChart.legend.textColor = (ContextCompat.getColor(requireContext(), R.color.coinColor))
        lineChart.xAxis.textColor = (ContextCompat.getColor(requireContext(), R.color.coinColor))
        lineChart.axisLeft.textColor = (ContextCompat.getColor(requireContext(), R.color.coinColor))
        lineChart.axisRight.textColor = (ContextCompat.getColor(requireContext(), R.color.coinColor))
        lineChart.setDescriptionColor(ContextCompat.getColor(requireContext(), R.color.saveEyesWhite))
        lineChart.setDescription(tvCoinName.text.toString())
        lineChart.animateXY(1000, 1000)
    }
}