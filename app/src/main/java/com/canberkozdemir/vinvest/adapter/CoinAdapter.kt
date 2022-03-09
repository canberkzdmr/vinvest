package com.canberkozdemir.vinvest.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.canberkozdemir.vinvest.R
import com.canberkozdemir.vinvest.databinding.ItemCoinRowBinding
import com.canberkozdemir.vinvest.model.Coin

class CoinAdapter(val coinList: ArrayList<Coin>) :
    RecyclerView.Adapter<CoinAdapter.CoinViewHolder>(), ICoinClickListener {

    class CoinViewHolder(var view: ItemCoinRowBinding): RecyclerView.ViewHolder(view.root) {

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CoinViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view = DataBindingUtil.inflate<ItemCoinRowBinding>(inflater, R.layout.item_coin_row, parent, false)
        return CoinViewHolder(view)
    }

    override fun onBindViewHolder(holder: CoinViewHolder, position: Int) {
        holder.view.coin = coinList[position]
        holder.view.listener = this
    }

    override fun getItemCount(): Int {
        return coinList.size
    }

    fun updateCoinList(newCoinList: List<Coin>) {
        coinList.clear().also {
            coinList.addAll(newCoinList)
            notifyDataSetChanged()
        }
    }

    override fun onCoinClicked(v: View) {
        TODO("Not yet implemented")
    }

}