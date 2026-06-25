package com.example.skillforge.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.example.skillforge.data.model.Course
import com.example.skillforge.data.model.Lesson

@Composable
fun CourseDetailScreen(
    viewModel: CourseViewModel,
    categoryIndex: Int,
    courseIndex: Int,
    onBackClick: () -> Unit,
    onLessonClick: (Int) -> Unit
) {
    val uiState by viewModel.uiState.collectAsState()

    Scaffold(
        containerColor = CreamBg
    ) { paddingValues ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            if (uiState is CourseUiState.Success) {
                val categories = (uiState as CourseUiState.Success).categories
                val course = categories.getOrNull(categoryIndex)?.courses?.getOrNull(courseIndex)

                if (course != null) {
                    LazyColumn(
                        modifier = Modifier.fillMaxSize(),
                        verticalArrangement = Arrangement.spacedBy(16.dp)
                    ) {
                        item {
                            Box(modifier = Modifier.fillMaxWidth().height(220.dp)) {
                                AsyncImage(
                                    model = course.thumbnailUrl,
                                    contentDescription = "Thumb",
                                    modifier = Modifier.fillMaxSize(),
                                    contentScale = ContentScale.Crop
                                )
                                IconButton(
                                    onClick = onBackClick,
                                    modifier = Modifier.padding(16.dp)
                                        .background(Color.White, CircleShape)
                                        .align(Alignment.TopStart)
                                ) {
                                    Icon(
                                        Icons.Default.ArrowBack,
                                        contentDescription = "Back",
                                        tint = TextDark
                                    )
                                }
                            }
                        }

                        item {
                            Column(modifier = Modifier.padding(horizontal = 20.dp)) {
                                Text(
                                    text = course.title?:"",
                                    fontSize = 24.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = TextDark
                                )
                                Text(
                                    text = "Instructor: ${course.instructor.name}",
                                    color = Color.Gray,
                                    fontSize = 14.sp
                                )
                                Spacer(modifier = Modifier.height(10.dp))
                                Text(
                                    text = "Course content",
                                    fontSize = 18.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = TextDark
                                )
                            }
                        }

                        // ... બાકીનો કોડ ઉપર મુજબ જ રાખવો ...

                        item {
                            Column(modifier = Modifier.padding(horizontal = 20.dp)) {
                                Text(
                                    text = course.title?:"",
                                    fontSize = 24.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = TextDark
                                )
                                // અહીં સેફ કોલ (?.) નો ઉપયોગ કરો
                                Text(
                                    text = "Instructor: ${course.instructor?.name ?: "Unknown"}",
                                    color = Color.Gray,
                                    fontSize = 14.sp
                                )
                                Spacer(modifier = Modifier.height(10.dp))
                                Text(
                                    text = "Course content",
                                    fontSize = 18.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = TextDark
                                )
                            }
                        }

// અહીં course.lessons?.let { ... } નો ઉપયોગ કરી લિસ્ટ સેફ કરો
                        course.lessons?.let { lessonList ->
                            itemsIndexed(lessonList) { index, lesson ->
                                Card(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(horizontal = 20.dp)
                                        .clickable { onLessonClick(index) },
                                    colors = CardDefaults.cardColors(containerColor = Color.White),
                                    shape = RoundedCornerShape(12.dp)
                                ) {
                                    Row(
                                        modifier = Modifier.padding(16.dp),
                                        verticalAlignment = Alignment.CenterVertically
                                    ) {
                                        Icon(
                                            imageVector = if (lesson.isFree) Icons.Default.PlayArrow else Icons.Default.Lock,
                                            contentDescription = "Status",
                                            tint = if (lesson.isFree) TealAccent else Color.Gray
                                        )
                                        Spacer(modifier = Modifier.width(16.dp))
                                        Column {
                                            Text(
                                                text = lesson.title?:"",
                                                fontWeight = FontWeight.Bold,
                                                color = TextDark
                                            )
                                            Text(
                                                text = lesson.duration?:"",
                                                color = Color.Gray,
                                                fontSize = 13.sp
                                            )
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}
