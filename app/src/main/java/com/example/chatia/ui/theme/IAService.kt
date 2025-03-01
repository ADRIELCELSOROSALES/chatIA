package com.example.chatia.ui.theme

import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface IaService {
    @GET("run")
    fun enviarMensaje(@Query("mensaje") mensaje: String): Call<ResponseBody>
}
