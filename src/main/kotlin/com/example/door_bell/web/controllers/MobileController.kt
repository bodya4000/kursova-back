package com.example.door_bell.web.controllers

import com.example.door_bell.servs.MobileServ
import com.example.door_bell.web.dtos.RequestSwitchDto
import com.example.door_bell.web.dtos.RequestUserDto
import com.example.door_bell.web.dtos.ResponseUserDto
import com.fasterxml.jackson.databind.ObjectMapper
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/mobile")
class MobileController(
    val mobileServ: MobileServ,
    val objectMapper: ObjectMapper
) {
    @GetMapping
    fun test(): ResponseEntity<String> {
        return ResponseEntity.ok().body(
            objectMapper.writeValueAsString(
                mapOf<String, String>().plus("message" to "hello")

            )
        )
    }

    @PostMapping
    fun registerDevice(@RequestBody userDto: RequestUserDto): ResponseEntity<ResponseUserDto> {
        println("registering device...")
        val res = mobileServ.registerMobile(userDto)
        return ResponseEntity.ok(res)
    }

    @PostMapping("/switch")
    fun switchModa(@RequestBody userDto: RequestSwitchDto): ResponseEntity<ResponseUserDto> {
        println("mode switch...")
        mobileServ.switchMode(userDto)
        return ResponseEntity.ok().build()
    }

    @PostMapping("/try-again")
    fun tryEstablishConnectionWithEspAgain(@RequestBody userDto: RequestUserDto): ResponseEntity<ResponseUserDto> {
        val res = mobileServ.tryEstablish(userDto)
        return ResponseEntity.ok(res)
    }
}