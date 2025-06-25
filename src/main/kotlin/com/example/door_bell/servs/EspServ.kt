package com.example.door_bell.servs

import com.example.door_bell.infra.PushNotificationManager
import com.example.door_bell.repos.EspRepo
import org.springframework.stereotype.Service
import org.springframework.web.socket.WebSocketSession

@Service
class EspServ(
    private val espRepo: EspRepo,
    private val pushNotificationManager: PushNotificationManager
) {

    fun registerEspId(espId: String, session: WebSocketSession) {
        espRepo.registerEspConnection(espId, session)
    }

    fun notifyUserMobilesAboutDoorbellBeingRang(espId: String) {
        val user = espRepo.findUserByEspId(espId) ?: return
        espRepo.attachEspToUser(espId, user)
        user.pushTokens.forEach { token ->
            pushNotificationManager.sendPushNotification(
                token,
                title = "🔔 Ring!",
                body = "Somebody is at the door.",
                data = mapOf("read" to "false")
            )
        }
    }
}
