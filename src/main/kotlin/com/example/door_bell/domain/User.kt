package com.example.door_bell.domain

import java.io.Serializable

class User : Serializable {
    var id: Long? = null
    var mobileIds: MutableList<String> = mutableListOf()
    var pushTokens: MutableList<String> = mutableListOf()
    var espId: String = ""
}
