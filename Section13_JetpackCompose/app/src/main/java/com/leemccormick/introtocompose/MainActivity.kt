package com.leemccormick.introtocompose

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.leemccormick.introtocompose.ui.theme.IntroToComposeTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            IntroToComposeTheme {
                MyApp()
            }
        }
    }
}

//@Composable
//fun Greeting(name: String) {
//    Text(text = "Hello $name!")
//}

//@Preview
//@Composable
//fun showAge(age: Int = 12){
//    Text(text = age.toString())
//}

@Composable
fun MyApp() {
    var moneyCounter = remember {
        mutableStateOf(0)
    }

    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight(),
        color = Color(0xFF546E7A) //MaterialTheme.colors.primary
    ) {
        Column(verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally) {
            Text(text = "$${moneyCounter.value}",
                 style = TextStyle(
                     color = Color.White,
                     fontSize = 39.sp,
                     fontWeight = FontWeight.ExtraBold
                 )
            )
            Spacer(modifier = Modifier.height(130.dp))
            CreateCircle(moneyCounter = moneyCounter.value) { newValue ->
                moneyCounter.value = newValue
            }
            Spacer(modifier = Modifier.height(30.dp))

            if (moneyCounter.value > 25) {
                Text(text = "Lot of money!",
                    style = TextStyle(
                        color = Color.White,
                        fontSize = 20.sp,
                        fontWeight = FontWeight.ExtraBold
                    )
                )
            }
        }
    }
}

// @Preview
@Composable
fun CreateCircle(moneyCounter: Int = 0,
                 updateMoneyCounter: (Int) -> Unit) {

    Card(modifier = Modifier
        .padding(3.dp)
        .size(105.dp)
        .clickable {
            // moneyCounter += 1
            updateMoneyCounter(moneyCounter + 1)
            Log.d("Tap", "Circle Tapped | moneyCounter is $moneyCounter")
        },
        shape = CircleShape,
        elevation = 4.dp) {

        Box(contentAlignment = Alignment.Center) {
            Text(text = "Tap", modifier = Modifier)
        }
    }
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    IntroToComposeTheme {
        MyApp()
//        Column() {
//            Greeting("Lee McCormick")
//            showAge(32)
//        }
    }
}