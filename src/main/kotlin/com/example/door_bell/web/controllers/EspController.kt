package com.example.door_bell.web.controllers

import com.example.door_bell.servs.EspServ
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping

@Controller
@RequestMapping("/esp")
class EspController(private val espServ: EspServ) {

    @PostMapping("/ring")
    suspend fun handleEsp() {
//        espServ.ring()
    }
}