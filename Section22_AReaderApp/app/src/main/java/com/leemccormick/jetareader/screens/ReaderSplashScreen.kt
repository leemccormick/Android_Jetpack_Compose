package com.leemccormick.jetareader.screens

import android.view.animation.OvershootInterpolator
import androidx.compose.animation.Animatable
import androidx.compose.animation.core.tween
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.google.firebase.auth.FirebaseAuth
import com.leemccormick.jetareader.components.ReaderLogo
import com.leemccormick.jetareader.navigation.ReaderScreens
import kotlinx.coroutines.delay

// To add @Preview, we need to use default value of NavHostController(context = LocalContext.current)
// @Preview
// @Composable
// fun ReaderSplashScreen(navController: NavHostController = NavHostController(context = LocalContext.current)) {

@Composable
fun ReaderSplashScreen(navController: NavHostController) {
    // Create the scale and use it later on the surface to scale it.
    val scale = remember {
        androidx.compose.animation.core.Animatable(0f)
    }

    // Animation the screen using LaunchedEffect.
    LaunchedEffect(key1 = true) {
        scale.animateTo(
            targetValue = 0.9f,
            animationSpec = tween(
                durationMillis = 800,
                easing = {
                    OvershootInterpolator(8f).getInterpolation(it)
                }
            )
        )

        delay(2000L)

        if (FirebaseAuth.getInstance().currentUser?.email.isNullOrEmpty()) {
            navController.navigate(ReaderScreens.LoginScreen.name)
        } else {
            navController.navigate(ReaderScreens.ReaderHomeScreen.name)
        }
    }

    // View
    Surface(
        modifier = Modifier
            .padding(15.dp)
            .size(330.dp)
            .scale(scale.value), // Added the scale here for animation
        shape = CircleShape,
        color = Color.White,
        border = BorderStroke(width = 2.dp, color = Color.LightGray)
    ) {
        Column(
            modifier = Modifier.padding(1.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            ReaderLogo()

            Spacer(modifier = Modifier.height(15.dp))

            Text(
                text = "\"Read. Change. Yourself\"",
                style = MaterialTheme.typography.h5,
                color = Color.LightGray
            )
        }
    }
}

