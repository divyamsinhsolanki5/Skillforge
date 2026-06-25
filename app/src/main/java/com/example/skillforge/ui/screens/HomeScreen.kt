package com.example.skillforge.ui.screens

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage

val CreamBg = Color(0xFFFAF8F5)
val TealAccent = Color(0xFF00A896)
val TextDark = Color(0xFF1C1C1E)
@Composable
fun HomeScreen(
    viewModel: CourseViewModel,
    onCourseClick: (Int, Int) -> Unit
) {
    val uiState by viewModel.uiState.collectAsState()
    var searchQuery by remember { mutableStateOf("") }

    Scaffold(containerColor = CreamBg) { paddingValues ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            when (val state = uiState) {
                is CourseUiState.Loading -> {
                    CircularProgressIndicator(
                        modifier = Modifier.align(Alignment.Center),
                        color = TealAccent
                    )
                }
                is CourseUiState.Error -> {
                    Text(text = state.message, color = Color.Red, modifier = Modifier.align(Alignment.Center))
                }
                is CourseUiState.Success -> {
                    LazyColumn(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(horizontal = 20.dp),
                        verticalArrangement = Arrangement.spacedBy(20.dp)
                    ) {
                        item {
                            Spacer(modifier = Modifier.height(16.dp))
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Column {
                                    Text(text = "Welcome back", color = Color.Gray, fontSize = 14.sp)
                                    Text(text = "Find your next\nskill", color = TextDark, fontSize = 28.sp, fontWeight = FontWeight.Bold)
                                }
                                Box(modifier = Modifier.size(48.dp).background(TealAccent, CircleShape), contentAlignment = Alignment.Center) {
                                    Text(text = "AS", color = Color.White, fontWeight = FontWeight.Bold)
                                }
                            }
                        }

                        item {
                            OutlinedTextField(
                                value = searchQuery,
                                onValueChange = { searchQuery = it },
                                placeholder = { Text("Search courses...", color = Color.Gray) },
                                leadingIcon = { Icon(Icons.Default.Search, contentDescription = "Search", tint = Color.Gray) },
                                modifier = Modifier.fillMaxWidth().background(Color.White, RoundedCornerShape(12.dp)),
                                shape = RoundedCornerShape(12.dp),
                                colors = OutlinedTextFieldDefaults.colors(
                                    focusedBorderColor = TealAccent,
                                    unfocusedBorderColor = Color.LightGray
                                )
                            )
                        }

                        item {
                            Text(text = "Categories", color = TextDark, fontSize = 20.sp, fontWeight = FontWeight.Bold)
                            Spacer(modifier = Modifier.height(8.dp))
                            LazyRow(horizontalArrangement = Arrangement.spacedBy(16.dp)) {
                                val categories = state.categories ?: emptyList()
                                itemsIndexed(categories) { index, item ->
                                    Card(
                                        modifier = Modifier.width(150.dp).height(100.dp),
                                        colors = CardDefaults.cardColors(containerColor = Color.White)
                                    ) {
                                        Column(modifier = Modifier.padding(12.dp), verticalArrangement = Arrangement.Center) {
                                            Text(text = item.name?:"", fontWeight = FontWeight.Bold, color = TextDark)
                                            Text(text = "${item.courses?.size ?: 0} courses", color = Color.Gray, fontSize = 12.sp)
                                        }
                                    }
                                }
                            }
                        }

                        item {
                            Text(text = "Popular courses", color = TextDark, fontSize = 20.sp, fontWeight = FontWeight.Bold)
                        }

                        val categories = state.categories ?: emptyList()
                        categories.forEachIndexed { catIndex, category ->
                            val courses = category.courses ?: emptyList()
                            itemsIndexed(courses) { courseIndex, course ->
                                if (searchQuery.isEmpty() || course.title?.contains(searchQuery, ignoreCase = true)?:false) {
                                    Card(
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .padding(vertical = 4.dp)
                                            .clickable {
                                                Log.e("ERROR", "catIndex-$catIndex,COURSEINDEX-$courseIndex")

                                                onCourseClick(catIndex, courseIndex)
                                            },
                                        colors = CardDefaults.cardColors(containerColor = Color.White),
                                        shape = RoundedCornerShape(16.dp)
                                    ) {
                                        Row(modifier = Modifier.padding(12.dp), verticalAlignment = Alignment.CenterVertically) {
                                            AsyncImage(
                                                model = course.thumbnailUrl,
                                                contentDescription = "Thumb",
                                                modifier = Modifier.size(70.dp).clip(RoundedCornerShape(12.dp)),
                                                contentScale = ContentScale.Crop
                                            )
                                            Spacer(modifier = Modifier.width(16.dp))
                                            Column {
                                                Text(text = course.title?:"Default Title", fontWeight = FontWeight.Bold, color = TextDark)
                                                Text(text = course.instructor?.name ?: "Unknown", color = Color.Gray, fontSize = 13.sp)
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
}