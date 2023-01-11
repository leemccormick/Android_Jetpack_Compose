package com.leemccormick.jetmovie.screens.screen.home

import android.util.Log
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.leemccormick.jetmovie.MovieRow
import com.leemccormick.jetmovie.navigation.MovieScreens

@Composable
fun HomeScreen(navController: NavController){
    Scaffold(topBar = {
        TopAppBar(
            backgroundColor = Color.Transparent,
            elevation = 5.dp
        ) {
            Text(text = "Movies")
        }
    }) {
        MainContent(navController = navController)
    }
}

@Composable
fun MainContent(
    navController: NavController,
    movieList: List<String> = listOf(
    "Avatar",
    "300",
    "Harry Potter",
    "Cross the line ",
    "Happy Feet",
    "Super Man",
    "Life",
    "Happiness")) {

    Column(modifier = Modifier.padding(12.dp)) {
        LazyColumn {
            items(items = movieList) {
                MovieRow(movie = it) { movie ->
                    Log.d("TAG", "MainContent | $movie")
                    navController.navigate(route = MovieScreens.DetailScreen.name+"/$movie")
                }
            }
        }
    }
}
