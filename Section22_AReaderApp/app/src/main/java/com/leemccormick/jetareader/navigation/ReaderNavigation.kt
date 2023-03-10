package com.leemccormick.jetareader.navigation

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.navArgument
import androidx.navigation.compose.rememberNavController
import com.leemccormick.jetareader.screens.ReaderSplashScreen
import com.leemccormick.jetareader.screens.details.BookDetailScreen
import com.leemccormick.jetareader.screens.home.Home
import com.leemccormick.jetareader.screens.home.HomeScreenViewModel
import com.leemccormick.jetareader.screens.login.ReaderLoginScreen
import com.leemccormick.jetareader.screens.search.BooksSearchViewModel
import com.leemccormick.jetareader.screens.search.SearchScreen
import com.leemccormick.jetareader.screens.stats.ReaderStatsScreen
import com.leemccormick.jetareader.screens.update.BookUpdateScreen

/* To Set Up Navigation
* 1) Create all empty screens in each own folder in Screens folder.
* 2) Create Screens Enum --> using companion object to create fromRoute function
* 3) Create Navigation Composable for navigate between screen in the app --> ReaderNavigation
* 4) Call Navigation in MainActivity, so when we run the app, it will start at startDestination as we set on this Navigation Composable.
* */

@Composable
fun ReaderNavigation() {
    // 1) Create rememberNavController()
    val navController = rememberNavController()

    // 2) Create NavHost()
    NavHost(
        navController = navController,
        startDestination = ReaderScreens.SplashScreen.name
    ) {

        // 3) Create composable
        composable(ReaderScreens.SplashScreen.name) {
            // 4) Pass navController to the screen for navigation later
            ReaderSplashScreen(navController = navController)
        }

        composable(ReaderScreens.ReaderHomeScreen.name) {
            val viewModel = hiltViewModel<HomeScreenViewModel>()
            Home(navController = navController, viewModel = viewModel)
        }

        composable(ReaderScreens.SearchScreen.name) {
            val viewModel = hiltViewModel<BooksSearchViewModel>()
            SearchScreen(navController = navController, viewModel)
        }

        // We can pass arguments --> using book id
        val detailName = ReaderScreens.DetailScreen.name
        composable("$detailName/{bookId}",
            arguments = listOf(
                navArgument("bookId") {
                    type = NavType.StringType
                }
            )
        ) { backStackEntry ->
            backStackEntry.arguments?.getString("bookId").let {
                BookDetailScreen(navController = navController, bookId = it.toString())
            }
        }

        composable(ReaderScreens.LoginScreen.name) {
            ReaderLoginScreen(navController = navController)
        }

        composable(ReaderScreens.ReaderStatesScreen.name) {
            val homeViewModel = hiltViewModel<HomeScreenViewModel>()
            ReaderStatsScreen(navController = navController, viewModel = homeViewModel)
        }

        val updateName = ReaderScreens.UpdateScreen.name
        composable("$updateName/{bookItemId}",
            arguments = listOf(
                navArgument("bookItemId") {
                    type = NavType.StringType
                }
            )
        ) { backStackEntry ->
            backStackEntry.arguments?.getString("bookItemId").let {
                BookUpdateScreen(navController = navController, bookItemId = it.toString())
            }
        }
    }
}