package com.example.door_bell.configs

import com.example.door_bell.web.sockets.ESPWebsocketHandler
import org.springframework.context.annotation.Configuration
import org.springframework.web.socket.config.annotation.EnableWebSocket
import org.springframework.web.socket.config.annotation.WebSocketConfigurer
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry

@Configuration
@EnableWebSocket
class WebsocketConfig(
    private val espWebsocketHandler: ESPWebsocketHandler
) : WebSocketConfigurer {

    override fun registerWebSocketHandlers(registry: WebSocketHandlerRegistry) {
        registry
            .addHandler(espWebsocketHandler, "/ws/esp")
            .setAllowedOrigins("*")
    }
}
