package com.example.gustavobatista.paygen.util

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.util.Base64
import android.widget.ImageView
import com.example.gustavobatista.paygen.R
import com.squareup.picasso.Picasso
import com.squareup.picasso.RequestCreator
import io.reactivex.Observable
import java.io.ByteArrayOutputStream
import java.io.File

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

    fun compressImage(file: File): Observable<String> {
        return Observable.create { subscriber ->
            try {
                val bitmap = BitmapFactory.decodeFile(file.path)
                val bao = ByteArrayOutputStream()
                bitmap.compress(Bitmap.CompressFormat.JPEG, 60, bao)
                val ba = bao.toByteArray()
                val string = Base64.encodeToString(ba, Base64.DEFAULT)
                subscriber.onNext("data:image/jpeg;base64,$string")
            } catch (e: Throwable) {
                subscriber.onError(e)
            }
        }
    }

    private val Context.picasso: Picasso
        get() = Picasso.with(this)
}