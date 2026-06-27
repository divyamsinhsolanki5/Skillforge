package com.example.skillforge.data.model

import com.google.gson.annotations.SerializedName

data class SkillforgeResponse(
    @SerializedName("categories") val categories: List<Category>
)

data class Category(
    @SerializedName("name") val name: String?,
    @SerializedName("courses") val courses: List<Course>
)

data class Course(
    @SerializedName("title") val title: String? = "",
    @SerializedName("thumbnailUrl") val thumbnailUrl: String? = "",
    @SerializedName("level") val level: String? = "",
    @SerializedName("instructor") val instructor: Instructor,
    @SerializedName("rating") val rating: Double,
    @SerializedName("duration") val duration: String? = "",
    @SerializedName("lessons") val lessons: List<Lesson>
)

data class Instructor(
    @SerializedName("name") val name: String? = "",
    @SerializedName("role") val role: String? = "",
    @SerializedName("bio") val bio: String? = ""
)

data class Lesson(
    @SerializedName("title") val title: String? = "",
    @SerializedName("duration") val duration: String? = "",
    @SerializedName("isFree") val isFree: Boolean
)

