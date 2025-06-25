package com.example.door_bell.infra

import com.fasterxml.jackson.databind.ObjectMapper
import org.springframework.http.*
import org.springframework.stereotype.Component
import org.springframework.web.client.RestTemplate

@Component
class PushNotificationManager(
    private val restTemplate: RestTemplate = RestTemplate(),
) {
    private val expoApiUrl = "https://exp.host/--/api/v2/push/send"

    fun sendPushNotification(expoToken: String, title: String, body: String, data: Map<String, String> = emptyMap()) {
        if (!expoToken.startsWith("ExponentPushToken")) {
            println("❌ Invalid Expo push token: $expoToken")
            return
        }

        val payload = mapOf(
            "to" to expoToken,
            "title" to title,
            "body" to body,
            "data" to data
        )

        val headers = HttpHeaders().apply {
            contentType = MediaType.APPLICATION_JSON
            set("Accept", "application/json")
        }

        val request = HttpEntity(payload, headers)

        try {
            val response = restTemplate.postForEntity(expoApiUrl, request, String::class.java)
            println("📤 Expo Push Notification sent: ${response.body}")
        } catch (ex: Exception) {
            println("❗ Error sending Expo notification: ${ex.message}")
        }
    }
}
