package com.leemccormick.jetareader

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.google.firebase.firestore.FirebaseFirestore
import com.leemccormick.jetareader.navigation.ReaderNavigation
import com.leemccormick.jetareader.ui.theme.JetAReaderTheme
import dagger.hilt.android.AndroidEntryPoint

// Add @AndroidEntryPoint on MainActivity, to initial where the app start
@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            JetAReaderTheme {
                ReaderApp()
            }
        }
    }
}

@Composable
fun ReaderApp() {
    Surface(
        modifier = Modifier
            .fillMaxSize()
            .padding(top = 46.dp),
        color = MaterialTheme.colors.background
    ) {
        Column(
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            ReaderNavigation() // Create the Navigation here..
        }
    }
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    JetAReaderTheme {
        ReaderApp()
    }
}

/* Example of Using Firebase FireStore
    // 1) To create database
            val db = FirebaseFirestore.getInstance()
            val user: MutableMap<String, Any> = HashMap()
            user["firstName"] = "Jeo"
            user["lastName"] = "James"

    // 2) To create collection and add data --> collection == table
            db.collection("users")
              .add(user)
              .addOnSuccessListener { Log.d("FB", "onCreate addOnSuccessListener : ${it.id}") }
              .addOnFailureListener { Log.d("FB", "onCreate addOnFailureListener : $it") }
* */