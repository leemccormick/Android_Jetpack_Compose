package com.leemccormick.jetareader.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.leemccormick.jetareader.screens.ReaderSplashScreen
import com.leemccormick.jetareader.screens.details.BookDetailScreen
import com.leemccormick.jetareader.screens.home.Home
import com.leemccormick.jetareader.screens.login.ReaderLoginScreen
import com.leemccormick.jetareader.screens.search.SearchScreen
import com.leemccormick.jetareader.screens.stats.ReaderStatsScreen
import com.leemccormick.jetareader.screens.update.BookUpdateScreen

/* To Set Up Navigation
* 1) Create all empty screens in each own folder in Screens folder.
* 2) Create Screens Enum --> using companion object to create fromRoute function
* 3) Create Navigation Composable for navigate between screen in the app --> ReaderNavigation
* 4) Call Navigation in MainActivity
* */

@Composable
fun ReaderNavigation() {
    // 1) Create rememberNavController()
    val navController = rememberNavController()

    // 2) Create NavHost()
    NavHost(
        navController = navController,
        startDestination = ReaderScreens.SplashScreen.name) {

        // 3) Create composable
        composable(ReaderScreens.SplashScreen.name) {
            // 4) Pass navController to the screen for navigation later
            ReaderSplashScreen(navController = navController)
        }

        composable(ReaderScreens.ReaderHomeScreen.name) {
            Home(navController = navController)
        }

        composable(ReaderScreens.SearchScreen.name) {
            SearchScreen(navController = navController)
        }

        composable(ReaderScreens.DetailsScreen.name) {
            BookDetailScreen(navController = navController)
        }

        composable(ReaderScreens.LoginScreen.name) {
            ReaderLoginScreen(navController = navController)
        }

        composable(ReaderScreens.ReaderStatesScreen.name) {
            ReaderStatsScreen(navController = navController)
        }

        composable(ReaderScreens.UpdateScreen.name) {
            BookUpdateScreen(navController = navController)
        }
    }
}