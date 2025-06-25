package com.example.door_bell.web.dtos

data class RequestUserDto (
    val id: String? = null,
    val pushToken: String? = null,
    val espId: String? = null,
    val mobileId: String? = null
)
