package com.example.gustavobatista.paygen.adapter

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.gustavobatista.paygen.R
import com.example.gustavobatista.paygen.entity.Item
import com.example.gustavobatista.paygen.util.StringUtils.currency
import kotlinx.android.synthetic.main.adapter_item.view.*

class ItemAdapter(private val items: List<Item>) :
        RecyclerView.Adapter<ItemAdapter.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.adapter_item,
                parent, false)
        return ViewHolder(v)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bindView(items[position])
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        fun bindView(item: Item) = with(itemView) {
            tvPrice.text = item.price.currency()
            tvDesc.text = item.description
        }
    }

    override fun getItemCount(): Int = items.size
}
