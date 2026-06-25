package com.example.skillforge

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.lifecycle.ViewModelProvider
import com.example.skillforge.data.network.SkillforgeApiService
import com.example.skillforge.data.repository.CourseRepository
import com.example.skillforge.ui.screens.CourseViewModel
import com.example.skillforge.ui.screens.CourseViewModelFactory
import com.example.skillforge.ui.screens.SkillforgeNavigation

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val apiService = SkillforgeApiService.create()
        val repository = CourseRepository(apiService)


        val viewModelFactory = CourseViewModelFactory(repository)
        val viewModel = ViewModelProvider(this, viewModelFactory)[CourseViewModel::class.java]

        setContent {

            SkillforgeNavigation(viewModel = viewModel)
        }
    }
}














