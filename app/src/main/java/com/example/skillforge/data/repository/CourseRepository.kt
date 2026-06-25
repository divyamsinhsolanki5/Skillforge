package com.example.skillforge.data.repository

import com.example.skillforge.data.model.SkillforgeResponse
import com.example.skillforge.data.network.SkillforgeApiService

class CourseRepository(private val apiService: SkillforgeApiService) {
    suspend fun getCourses(): SkillforgeResponse {
        return apiService.getCourseData()
    }
}