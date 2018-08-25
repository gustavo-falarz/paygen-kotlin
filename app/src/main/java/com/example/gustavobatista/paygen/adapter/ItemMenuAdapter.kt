package com.example.gustavobatista.paygen.adapter

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.gustavobatista.paygen.R
import com.example.gustavobatista.paygen.entity.Item
import com.example.gustavobatista.paygen.util.ImageUtil.load
import com.example.gustavobatista.paygen.util.StringUtils.currency
import kotlinx.android.synthetic.main.adapter_menu_item.view.*

class ItemMenuAdapter(private val items: List<Item>, private val listener: (Item) -> Unit, private val listenerButton: (String, String) -> Unit) :
        RecyclerView.Adapter<ItemMenuAdapter.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.adapter_menu_item,
                parent, false)
        return ViewHolder(v)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bindView(items[position], listener, listenerButton)
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        fun bindView(item: Item, listener: (Item) -> Unit, listenerButton: (String, String) -> Unit) = with(itemView) {
            tvPrice.text = item.price.currency()
            tvName.text = item.name
            tvDesc.text = item.description
            imItem.load(item.picture) { request -> request.fit() }
            setOnClickListener { listenerButton(item.name, item.description) }
            btAddItem.setOnClickListener { listener(item) }

        }
    }

    override fun getItemCount(): Int = items.size
}