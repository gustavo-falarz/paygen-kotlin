package com.example.gustavobatista.paygen.util

import android.widget.TextView

object FieldUtils {

    fun TextView.isEmpty(): Boolean {
        if (this == null) {
            return true
        }
        if (this.text.toString().isNullOrEmpty()) {
            return true
        }
        if (this.text.toString().isEmpty()) {
            return true
        }
        if (this.text.toString().isBlank()) {
            return true
        }
        return false
    }

}
