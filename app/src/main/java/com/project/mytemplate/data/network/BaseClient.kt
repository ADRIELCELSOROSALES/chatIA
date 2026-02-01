package com.project.mytemplate.data.network

import io.ktor.client.HttpClient
import io.ktor.client.engine.cio.CIO
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logging
import io.ktor.client.request.get
import io.ktor.client.request.header
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.client.statement.HttpResponse
import io.ktor.http.ContentType
import io.ktor.http.contentType
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json

class BaseClient {
    companion object {
        private const val BASE_URL = "https://magicloops.dev/api/loop/253e449e-fd03-4a7b-a6ad-0357f8526ebb/"
        val baseClient: BaseClient = BaseClient()
    }

    private val apiClient = HttpClient(CIO) {
        install(ContentNegotiation) {
            json(Json {
                ignoreUnknownKeys = true
                encodeDefaults = true
                isLenient = true
            })
        }
        install(Logging) {
            level = LogLevel.ALL
        }
    }

    internal suspend fun post(
        endpoint: String,
        body: String,
        errorMessage: String,
        token: String? = ""
    ): HttpStatus {
        return try {
            val response = apiClient.post("$BASE_URL$endpoint") {
                contentType(ContentType.Application.Json)
                setBody(body)
                header("Authorization", "Bearer $token")
            }
            if (response.status.value in 200..299) {
                HttpStatus(httpResponse = response)
            } else {
                HttpStatus(errorMessage = errorMessage)
            }
        } catch (e: Exception) {
            HttpStatus(errorMessage = "Error desconocido: ${e.message}")
        }
    }

    internal suspend fun get(
        url: String,
        errorMessage: String,
    ): HttpStatus {
        return try {
            val response = apiClient.get("$BASE_URL$url") {
                contentType(ContentType.Application.Json)
            }
            if (response.status.value in 200..299) {
                HttpStatus(httpResponse = response)
            } else {
                HttpStatus(errorMessage = errorMessage)
            }
        } catch (e: Exception) {
            HttpStatus(errorMessage = "Ups! Atrapaste un error desconocido, vuelve a intentarlo")
        }
    }
}

internal data class HttpStatus(
    val httpResponse: HttpResponse? = null,
    val errorMessage: String = "",
    val errorType: ErrorType? = null
)

enum class ErrorType {
    NETWORK,
    SERVER
}
