package com.example.gustavobatista.paygen.entity.dto

import com.example.gustavobatista.paygen.entity.User

class LoginDTO {
    var providerId: String = ""
    var userId: String = ""
    var token: String = ""
    var userName: String = ""
    var picture: String = ""
    var status: User.Status? = null

}