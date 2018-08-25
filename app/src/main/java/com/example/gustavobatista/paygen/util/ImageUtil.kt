package com.example.gustavobatista.paygen.util

import android.content.Context
import android.widget.ImageView
import com.example.gustavobatista.paygen.R
import com.squareup.picasso.Picasso
import com.squareup.picasso.RequestCreator

/**
 * Created by Gustavo on 12/27/2017.
 */
object ImageUtil {

    fun ImageView.load(path: String?, request: (RequestCreator) -> RequestCreator) {
        if (!path.isNullOrEmpty()) {
            request(context.picasso.load(path)).error(R.drawable.image_place_holder).placeholder(R.drawable.image_place_holder).into(this)
        } else {
            request(context.picasso.load(R.drawable.image_place_holder)).error(R.drawable.image_place_holder).placeholder(R.drawable.image_place_holder).into(this)
        }
    }

    private val Context.picasso: Picasso
        get() = Picasso.with(this)
}