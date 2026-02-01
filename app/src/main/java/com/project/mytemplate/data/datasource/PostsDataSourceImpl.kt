package com.project.mytemplate.data.datasource

import android.util.Log
import com.project.mytemplate.data.interfaces.PostsDataSource
import com.project.mytemplate.data.models.IAResponseDTO
import com.project.mytemplate.data.network.BaseClient
import com.project.mytemplate.utils.StatusResult
import io.ktor.client.call.body
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.json.JSONObject
import java.net.URLEncoder

class PostsDataSourceImpl : PostsDataSource {

    override suspend fun sendMessage(message: String): StatusResult<IAResponseDTO> {
        val encodedMessage = withContext(Dispatchers.IO) {
            URLEncoder.encode(message.trim(), "UTF-8")
        }
        val endpoint = "run"
        val body = "{\"message\": \"$encodedMessage\"}"

        Log.d("PostsDataSourceImpl", "Invocado con mensaje: $body")

        val response = BaseClient.baseClient.post(endpoint, body, "Error al obtener la respuesta")

        return try {
            response.httpResponse?.let {
                val responseBody = it.body<String>()
                if (responseBody.isNotEmpty()) {
                    val jsonObject = JSONObject(responseBody)
                    val respuestaLimpia = jsonObject.optString("response", "Error en la respuesta")
                    Log.d("PostsDataSourceImpl", "Respuesta recibida: $responseBody")

                    StatusResult.Success(IAResponseDTO(respuestaLimpia))
                } else {
                    StatusResult.Error("Respuesta vacía")
                }
            } ?: StatusResult.Error("Respuesta nula")
        } catch (e: Exception) {
            StatusResult.Error("Error procesando la respuesta: ${e.message}")
        }
    }
}
