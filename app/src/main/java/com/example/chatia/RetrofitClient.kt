package com.example.chatia
import com.example.chatia.ui.theme.IaService
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


object RetrofitClient {
    private const val BASE_URL = "https://magicloops.dev/api/loop/f895a07f-c027-418a-a13c-64b49723ac62/"

    val iaService: IaService by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(IaService::class.java)
    }
}

