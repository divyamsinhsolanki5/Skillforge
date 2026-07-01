package com.example.skillforge.data.network

import com.example.skillforge.data.model.SkillforgeResponse
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET

interface SkillforgeApiService {

    @GET("android-assesment/notes/refs/heads/main/data.json")
    suspend fun getCourseData(): SkillforgeResponse

    companion object {
        private const val BASE_URL = "https://raw.githubusercontent.com/"

        fun create(): SkillforgeApiService {
            return Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(SkillforgeApiService::class.java)
        }
    }
}








