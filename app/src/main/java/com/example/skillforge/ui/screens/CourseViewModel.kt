package com.example.skillforge.ui.screens

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.skillforge.data.model.Category
import com.example.skillforge.data.repository.CourseRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch


sealed interface CourseUiState {
    object Loading : CourseUiState
    data class Success(val categories: List<Category>) : CourseUiState
    data class Error(val message: String) : CourseUiState
}


class CourseViewModel(private val repository: CourseRepository) : ViewModel() {

    private val _uiState = MutableStateFlow<CourseUiState>(CourseUiState.Loading)
    val uiState: StateFlow<CourseUiState> = _uiState.asStateFlow()

    init {
        fetchCourseData()
    }

    fun fetchCourseData() {
        viewModelScope.launch {
            _uiState.value = CourseUiState.Loading
            try {
                val response = repository.getCourses()
                _uiState.value = CourseUiState.Success(response.categories)
            } catch (e: Exception) {
                _uiState.value = CourseUiState.Error(e.localizedMessage ?: "Network connection error")
            }
        }
    }
}


class CourseViewModelFactory(private val repository: CourseRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(CourseViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return CourseViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}