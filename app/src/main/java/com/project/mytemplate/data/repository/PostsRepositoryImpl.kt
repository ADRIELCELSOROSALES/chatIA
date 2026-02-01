package com.project.mytemplate.data.repository


import android.util.Log
import com.project.mytemplate.data.interfaces.PostsDataSource
import com.project.mytemplate.data.mappers.toPost
import com.project.mytemplate.domine.interfaces.PostsRepository
import com.project.mytemplate.domine.models.IAResponse
import com.project.mytemplate.utils.StatusResult

class PostsRepositoryImpl(private val postsDataSource: PostsDataSource) : PostsRepository {
    override suspend fun sendMessage(message: String): StatusResult<IAResponse> {
        return try {
            Log.d("PostsRepositoryImpl", "Enviando mensaje: $message")

            val response = postsDataSource.sendMessage(message)

            when (response) {
                is StatusResult.Success -> {
                    val mappedResponse = StatusResult.Success(response.value.toPost())
                    Log.d("PostsRepositoryImpl", "Respuesta exitosa: ${mappedResponse.value}")
                    mappedResponse
                }
                is StatusResult.Error -> {
                    Log.e("PostsRepositoryImpl", "Error en la respuesta: ${response.message}")
                    StatusResult.Error(response.message)
                }
            }
        } catch (e: Exception) {
            Log.e("PostsRepositoryImpl", "Error inesperado: ${e.message}")
            StatusResult.Error("Error inesperado: ${e.message}")
        }
    }
}
