package com.leemccormick.jetweatherforcast.screens.about

import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.navigation.NavController
import com.leemccormick.jetweatherforcast.widgets.WeatherAppBar

@Composable
fun AboutScreen(navController: NavController) {
    Scaffold(
        topBar = {
            WeatherAppBar(
                title = "About",
                icon = Icons.Default.ArrowBack,
                false,
                navController = navController
            ) {
                navController.popBackStack()
            }
        }
    ) {
        Surface(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight()
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                // Text(text = stringResource(id = R.string.about_app), --> This line is not working, same result as line below
                Text(
                    text = "Weather App v.0.1",
                    style = MaterialTheme.typography.subtitle1,
                    fontWeight = FontWeight.Bold
                )

                // Text(text = stringResource(id = R.string.api_used), --> This line is not working, same result as line below
                Text(
                    text = "Weather API by: https://openweathermap.org",
                    style = MaterialTheme.typography.subtitle1,
                    fontWeight = FontWeight.Light
                )
            }
        }
    }
}