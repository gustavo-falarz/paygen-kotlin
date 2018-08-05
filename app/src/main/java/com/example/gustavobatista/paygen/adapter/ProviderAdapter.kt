package com.example.gustavobatista.paygen.adapter

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.gustavobatista.paygen.R
import com.example.gustavobatista.paygen.entity.Provider
import com.example.gustavobatista.paygen.util.ImageUtil.load
import kotlinx.android.synthetic.main.adapter_provider.view.*

class ProviderAdapter(private val items: List<Provider>, private val listener: (Provider) -> Unit) :
        RecyclerView.Adapter<ProviderAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.adapter_provider,
                parent, false)
        return ViewHolder(v)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bindView(items[position], listener)
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        fun bindView(provider: Provider, listener: (Provider) -> Unit) = with(itemView) {
            tvProviderName.text = provider.name
            imBannerProvider.load(provider.info.banner) { request -> request.fit() }
            parentView.setOnClickListener{listener(provider)}

        }
    }

    override fun getItemCount(): Int = items.size
}
