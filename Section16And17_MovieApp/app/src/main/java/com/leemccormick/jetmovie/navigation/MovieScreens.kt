package com.leemccormick.jetmovie.navigation

import java.lang.IllegalArgumentException
import kotlin.math.round

enum class MovieScreens {
    HomeScreen,
    DetailScreen;
        companion object {
            fun fromRoute(route: String?): MovieScreens
            = when (route?.substringBefore("/")) {
                HomeScreen.name -> HomeScreen
                DetailScreen.name -> DetailScreen
                null -> HomeScreen
                else -> throw IllegalArgumentException("Route $route is not recognized.")
            }
        }
}