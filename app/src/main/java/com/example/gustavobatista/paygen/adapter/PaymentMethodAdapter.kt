package com.example.gustavobatista.paygen.adapter

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.gustavobatista.paygen.R
import com.example.gustavobatista.paygen.entity.CreditCard
import com.example.gustavobatista.paygen.util.StringUtils.maskNumber
import kotlinx.android.synthetic.main.adapter_payment_method.view.*

class PaymentMethodAdapter(private val items: List<CreditCard>, private val listener: (CreditCard) -> Unit) :
        RecyclerView.Adapter<PaymentMethodAdapter.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val v = LayoutInflater.from(parent.context)
                .inflate(R.layout.adapter_payment_method, parent, false)
        return ViewHolder(v)
    }

    override fun getItemCount(): Int = items.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bindView(items[position], listener)
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bindView(creditCard: CreditCard, listener: (CreditCard) -> Unit) = with(itemView) {
            tvCardNumber.text = creditCard.cardNumber.maskNumber()
        }

    }
}

