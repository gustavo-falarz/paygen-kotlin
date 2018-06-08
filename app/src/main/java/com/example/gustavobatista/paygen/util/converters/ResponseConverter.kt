package com.gfb.watchlist.util.converters

import com.example.gustavobatista.paygen.util.ServerException


import java.io.IOException

import okhttp3.ResponseBody
import retrofit2.Converter
import com.example.gustavobatista.paygen.entity.Response
/**
 * Created by Gustavo on 10/19/2017.
 */

internal class ResponseConverter<T>(private val converter: Converter<ResponseBody, Response<T>>) : Converter<ResponseBody, T> {

    @Throws(IOException::class)
    override fun convert(value: ResponseBody): T? {
        val (status, data, message) = converter.convert(value)
        if (status) {
            return data
        }
        throw ServerException(message!!)
    }
}
