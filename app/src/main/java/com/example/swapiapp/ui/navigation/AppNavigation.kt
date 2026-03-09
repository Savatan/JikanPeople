package com.example.swapiapp.ui.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.swapiapp.ui.detail.PersonDetailScreen
import com.example.swapiapp.ui.list.PeopleListScreen

object Routes {
    const val PEOPLE_LIST = "people_list"
    const val PERSON_DETAIL = "person_detail/{personId}"

    fun personDetail(personId: Int) = "person_detail/$personId"
}

@Composable
fun AppNavigation() {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = Routes.PEOPLE_LIST
    ) {
        composable(Routes.PEOPLE_LIST) {
            PeopleListScreen(
                onPersonClick = { personId ->
                    navController.navigate(Routes.personDetail(personId))
                }
            )
        }

        composable(
            route = Routes.PERSON_DETAIL,
            arguments = listOf(
                navArgument("personId") { type = NavType.IntType }
            )
        ) {
            PersonDetailScreen(
                onBack = { navController.popBackStack() }
            )
        }
    }
}
