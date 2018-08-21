package com.example.gustavobatista.paygen.adapter

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.gustavobatista.paygen.util.StringUtils.currency
import com.example.gustavobatista.paygen.R
import com.example.gustavobatista.paygen.entity.Item
import kotlinx.android.synthetic.main.adapter_add_item.view.*

class AddItemAdapter(private val items: List<Item>, private val listener: (Item) -> Unit) :
        RecyclerView.Adapter<AddItemAdapter.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.adapter_add_item,
                parent, false)
        return ViewHolder(v)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bindView(items[position], listener)
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bindView(item: Item, listener: (Item) -> Unit) = with(itemView) {
            tvPrice.text = item.price.currency()
            tvDesc.text = item.description
            btAdd.setOnClickListener { listener(item) }
        }
    }

    override fun getItemCount(): Int = items.size
}
