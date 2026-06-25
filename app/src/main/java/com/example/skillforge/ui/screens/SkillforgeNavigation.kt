package com.example.skillforge.ui.screens

import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument

@Composable
fun SkillforgeNavigation(viewModel: CourseViewModel) {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = "home") {
        composable("home") {
            HomeScreen(
                viewModel = viewModel,
                onCourseClick = { catIndex, courseIndex ->
                    // સેફ નેવિગેશન
                    navController.navigate("detail/$catIndex/$courseIndex")
                }
            )
        }

        composable(
            route = "detail/{categoryIndex}/{courseIndex}",
            arguments = listOf(
                navArgument("categoryIndex") { type = NavType.IntType },
                navArgument("courseIndex") { type = NavType.IntType }
            )
        ) { backStackEntry ->
            val catIndex = backStackEntry.arguments?.getInt("categoryIndex") ?: 0
            val courseIndex = backStackEntry.arguments?.getInt("courseIndex") ?: 0

            CourseDetailScreen(
                viewModel = viewModel,
                categoryIndex = catIndex,
                courseIndex = courseIndex,
                onBackClick = { navController.popBackStack() },
                onLessonClick = { lessonIndex ->
                    navController.navigate("player/$catIndex/$courseIndex/$lessonIndex")
                }
            )
        }

        composable(
            route = "player/{categoryIndex}/{courseIndex}/{lessonIndex}",
            arguments = listOf(
                navArgument("categoryIndex") { type = NavType.IntType },
                navArgument("courseIndex") { type = NavType.IntType },
                navArgument("lessonIndex") { type = NavType.IntType }
            )
        ) { backStackEntry ->
            val catIndex = backStackEntry.arguments?.getInt("categoryIndex") ?: 0
            val courseIndex = backStackEntry.arguments?.getInt("courseIndex") ?: 0
            val lessonIndex = backStackEntry.arguments?.getInt("lessonIndex") ?: 0

            LessonPlayerScreen(
                viewModel = viewModel,
                categoryIndex = catIndex,
                courseIndex = courseIndex,
                lessonIndex = lessonIndex,
                onBackClick = { navController.popBackStack() },
                onLessonClick = { newLessonIndex ->
                    navController.navigate("player/$catIndex/$courseIndex/$newLessonIndex") {
                        popUpTo("home")
                    }
                }
            )
        }
    }
}