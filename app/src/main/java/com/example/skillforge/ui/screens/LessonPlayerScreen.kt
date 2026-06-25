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
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.example.skillforge.data.model.Course
import com.example.skillforge.data.model.Lesson

@Composable
fun LessonPlayerScreen(
    viewModel: CourseViewModel,
    categoryIndex: Int,
    courseIndex: Int,
    lessonIndex: Int,
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
                val currentLesson = course?.lessons?.getOrNull(lessonIndex)

                if (course != null && currentLesson != null) {
                    PlayerContent(
                        course = course,
                        currentLesson = currentLesson,
                        currentLessonIndex = lessonIndex,
                        onBackClick = onBackClick,
                        onLessonClick = onLessonClick
                    )
                } else {
                    Text(text = "Video lesson not found", modifier = Modifier.align(Alignment.Center))
                }
            }
        }
    }
}

@Composable
fun PlayerContent(
    course: Course,
    currentLesson: Lesson,
    currentLessonIndex: Int,
    onBackClick: () -> Unit,
    onLessonClick: (Int) -> Unit
) {
    Column(modifier = Modifier.fillMaxSize()) {


        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(220.dp)
                .background(Color.Black)
        ) {

            AsyncImage(
                model = course.thumbnailUrl,
                contentDescription = "Video Thumbnail Background",
                modifier = Modifier.fillMaxSize(),
                alpha = 0.4f
            )


            IconButton(
                onClick = {},
                modifier = Modifier
                    .size(64.dp)
                    .background(TealAccent, CircleShape)
                    .align(Alignment.Center)
            ) {
                Icon(
                    Icons.Default.PlayArrow,
                    contentDescription = "Play",
                    tint = Color.White,
                    modifier = Modifier.size(36.dp)
                )
            }

            // Back Navigation Button
            IconButton(
                onClick = onBackClick,
                modifier = Modifier
                    .padding(16.dp)
                    .background(Color.White.copy(alpha = 0.8f), CircleShape)
                    .align(Alignment.TopStart)
            ) {
                Icon(Icons.Default.ArrowBack, contentDescription = "Back", tint = TextDark)
            }
        }


        LazyColumn(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f)
                .padding(horizontal = 20.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            item {
                Spacer(modifier = Modifier.height(16.dp))
                Text(
                    text = "NOW PLAYING",
                    color = TealAccent,
                    fontSize = 11.sp,
                    fontWeight = FontWeight.Bold,
                    letterSpacing = 1.sp
                )
                Text(
                    text = currentLesson.title?:"Default Title",
                    fontSize = 22.sp,
                    fontWeight = FontWeight.Bold,
                    color = TextDark
                )
                Text(
                    text = "Course: ${course.title}  •  ${currentLesson.duration}",
                    color = Color.Gray,
                    fontSize = 13.sp
                )
                Divider(
                    modifier = Modifier.padding(vertical = 8.dp),
                    color = Color.LightGray.copy(alpha = 0.5f)
                )
            }


            item {
                Text(
                    text = "Up next in this course",
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold,
                    color = TextDark
                )
            }


            itemsIndexed(course.lessons) { index, lesson ->
                val isSelected = index == currentLessonIndex

                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable { onLessonClick(index) },
                    colors = CardDefaults.cardColors(
                        containerColor = if (isSelected) TealAccent.copy(alpha = 0.08f) else Color.White
                    ),
                    shape = RoundedCornerShape(12.dp),
                    border = if (isSelected) androidx.compose.foundation.BorderStroke(
                        1.dp,
                        TealAccent
                    ) else null
                ) {
                    Row(
                        modifier = Modifier.padding(14.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Box(
                            modifier = Modifier
                                .size(32.dp)
                                .background(
                                    if (isSelected) TealAccent else Color.LightGray.copy(
                                        alpha = 0.3f
                                    ), CircleShape
                                ),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(
                                text = (index + 1).toString(),
                                color = if (isSelected) Color.White else Color.DarkGray,
                                fontWeight = FontWeight.Bold,
                                fontSize = 13.sp
                            )
                        }
                        Spacer(modifier = Modifier.width(16.dp))
                        Column(modifier = Modifier.weight(1f)) {
                            Text(
                                text = lesson.title?:"Lesson Title",
                                fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Medium,
                                color = if (isSelected) TealAccent else TextDark,
                                fontSize = 14.sp
                            )
                            Text(text = lesson.duration?:"", color = Color.Gray, fontSize = 12.sp)
                        }
                    }
                }
            }
        }
    }
}