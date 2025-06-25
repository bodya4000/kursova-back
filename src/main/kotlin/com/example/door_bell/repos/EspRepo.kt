package com.example.door_bell.repos

import com.example.door_bell.domain.User
import org.springframework.stereotype.Repository
import org.springframework.web.socket.WebSocketSession
import java.util.concurrent.atomic.AtomicInteger

@Repository
class EspRepo {

    private val espAndUsers = mutableMapOf<String, User>()
     val espAndSessions = mutableMapOf<String, WebSocketSession>()

    fun registerEspConnection(espId: String, session: WebSocketSession) {
        espAndSessions[espId] = session
    }


    fun attachEspToUser(espId: String, user: User) {
        espAndUsers[espId] = user
    }

    fun findUserByEspId(espId: String) = espAndUsers[espId]


}
