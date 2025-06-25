package com.example.door_bell.servs

import com.example.door_bell.domain.User
import com.example.door_bell.repos.EspRepo
import com.example.door_bell.repos.UserRepo
import com.example.door_bell.web.dtos.RequestSwitchDto
import com.example.door_bell.web.dtos.RequestUserDto
import com.example.door_bell.web.dtos.ResponseUserDto
import com.fasterxml.jackson.databind.ObjectMapper
import org.springframework.stereotype.Service
import org.springframework.web.socket.TextMessage

@Service
class MobileServ(
    private val userRepo: UserRepo,
    private val espRepo: EspRepo,
    private val objectMapper: ObjectMapper
) {
    fun registerMobile(incomingUser: RequestUserDto): ResponseUserDto {
        val espId = incomingUser.espId ?: throw IllegalArgumentException("espId cannot be null")
        val mobileId = incomingUser.mobileId ?: throw IllegalArgumentException("mobileId cannot be null")
        val pushTokenRaw = incomingUser.pushToken ?: throw IllegalArgumentException("pushToken cannot be null")


        val existingUser = espRepo.findUserByEspId(espId)
        val user = existingUser ?: User().apply { this.espId = espId }

        if (!user.mobileIds.contains(mobileId)) {
            user.mobileIds.add(mobileId)
        }

        if (!user.pushTokens.contains(pushTokenRaw)) {
            user.pushTokens.add(pushTokenRaw)
        }

        userRepo.save(user)

        val espSession = espRepo.espAndSessions[espId]
        if (espSession != null && existingUser == null) {
            espRepo.attachEspToUser(espId, user)
            return ResponseUserDto(id = incomingUser.id, established = true)
        }

        return ResponseUserDto(id = incomingUser.id, established = espSession != null)
    }

    fun switchMode(modeInfp: RequestSwitchDto) {
        val espId = modeInfp.espId ?: throw IllegalArgumentException("espId cannot be null")
        val securityEnabled = modeInfp.value ?: throw IllegalArgumentException("value cannot be null")
        val espSession = espRepo.espAndSessions[espId]

        val message = mapOf(
            "type" to "switchMode",
            "value" to securityEnabled
        )
        val json = objectMapper.writeValueAsString(message)
        val textMessage = TextMessage(json)
        espSession?.sendMessage(textMessage)
    }


    fun tryEstablish(incomingUser: RequestUserDto): ResponseUserDto {
        val espId = incomingUser.espId ?: throw IllegalArgumentException("espId cannot be null")
        espRepo.espAndSessions[espId] ?: return ResponseUserDto(id = incomingUser.id, established = false)
        val user = espRepo.findUserByEspId(espId) ?: return ResponseUserDto(id = incomingUser.id, established = false)
        espRepo.attachEspToUser(espId, user)
        return ResponseUserDto(id = incomingUser.id, established = true)
    }


}
