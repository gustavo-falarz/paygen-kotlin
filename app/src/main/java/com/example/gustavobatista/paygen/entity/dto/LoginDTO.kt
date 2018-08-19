package com.example.gustavobatista.paygen.entity.dto

import com.example.gustavobatista.paygen.entity.User

class LoginDTO(val providerId: String, val userId: String, val token: String) {

    var status: User.Status? = null

}