package com.example.door_bell.web.sockets

import com.example.door_bell.repos.EspRepo
import com.example.door_bell.servs.EspServ
import com.example.door_bell.web.dtos.EspMessage
import com.fasterxml.jackson.databind.ObjectMapper
import org.springframework.stereotype.Component
import org.springframework.web.socket.CloseStatus
import org.springframework.web.socket.TextMessage
import org.springframework.web.socket.WebSocketSession
import org.springframework.web.socket.handler.TextWebSocketHandler

@Component
class ESPWebsocketHandler(
    private val espServ: EspServ,
    private val objectMapper: ObjectMapper
) : TextWebSocketHandler() {

    private val sessions = mutableMapOf<String, WebSocketSession>()

    override fun handleTextMessage(session: WebSocketSession, message: TextMessage) {
        val espMessage = objectMapper.readValue(message.payload, EspMessage::class.java)
        espServ.registerEspId(espMessage.id, session)
        println("📩 Message From Esp Server ${espMessage.id}: ${espMessage.message}")
        espServ.notifyUserMobilesAboutDoorbellBeingRang(espMessage.id)
    }


    override fun afterConnectionEstablished(session: WebSocketSession){
        println("<UNK> <UNK> <UNK> <UNK>: $session")
        sessions.put(session.id, session)
    }

    override fun afterConnectionClosed(session: WebSocketSession, status: CloseStatus) {
        sessions.remove(session.id)
    }

}