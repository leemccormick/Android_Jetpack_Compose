package com.leemccormick.jetareader.screens.home

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.rememberScrollableState
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material.icons.rounded.Favorite
import androidx.compose.material.icons.rounded.FavoriteBorder
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import coil.compose.rememberImagePainter
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.ktx.Firebase
import com.leemccormick.jetareader.components.FABContent
import com.leemccormick.jetareader.components.ListCard
import com.leemccormick.jetareader.components.ReaderAppBar
import com.leemccormick.jetareader.components.TitleSection
import com.leemccormick.jetareader.model.MBook
import com.leemccormick.jetareader.navigation.ReaderScreens

@Preview
@Composable
fun Home(navController: NavController = NavController(LocalContext.current)) {
    Scaffold(
        topBar = {
            ReaderAppBar(title = "A.Reader", navController = navController)
        },
        floatingActionButton = {
            FABContent {
                Log.d("FABContent", "FABContent Clicked ! Should go to search screen...")
                navController.navigate(ReaderScreens.SearchScreen.name)
            }
        }
    ) {
        Surface(
            modifier = Modifier.fillMaxSize()
        ) {
            HomeContent(navController)
        }
    }
}

@Composable
fun HomeContent(navController: NavController) {
    val email = FirebaseAuth.getInstance().currentUser?.email
    val currentUserName = if (!email.isNullOrEmpty()) email?.split("@").get(0) else "N/A"
    val listOfBooks = listOf(
        MBook("adas", "Running1", "Me and you", "Hello world"),
        MBook("adas", "Running2", "Me and you", "Hello world"),
        MBook("adas", "Running3", "Me and you", "Hello world"),
        MBook("adas", "Running4", "Me and you", "Hello world"),
        MBook("adas", "Running5", "Me and you", "Hello world")
    )

    Column(
        Modifier.padding(2.dp),
        verticalArrangement = Arrangement.Top
    ) {
        Row(modifier = Modifier.align(alignment = Alignment.Start)) {
            TitleSection(label = "Your reading \n" + " activity right now...")

            Spacer(modifier = Modifier.fillMaxWidth(0.7f))

            Column {
                Icon(
                    imageVector = Icons.Filled.AccountCircle,
                    contentDescription = "profile icon",
                    modifier = Modifier
                        .clickable {
                            navController.navigate(ReaderScreens.ReaderStatesScreen.name)
                        }
                        .size(45.dp),
                    tint = MaterialTheme.colors.secondaryVariant
                )

                Text(
                    text = currentUserName,
                    modifier = Modifier.padding(2.dp),
                    style = MaterialTheme.typography.overline,
                    color = Color.Red,
                    fontSize = 15.sp,
                    maxLines = 1,
                    overflow = TextOverflow.Clip
                )

                Divider()
            }
        }

        ReadingRightNowArea(books = listOf(), navController = navController)

        TitleSection(label = "Reading List")

        BookListArea(listOfBook = listOfBooks, navController = navController)
    }
}

@Composable
fun BookListArea(listOfBook: List<MBook>, navController: NavController) {
    HorizontalScrollableComponent(listOfBook) {
        Log.d("Book", "BookListArea : $it")
        // TODO : onCardPressed -> Go to detail
    }
}

@Composable
fun HorizontalScrollableComponent(
    listOfBook: List<MBook>,
    onCardPressed: (String) -> Unit = {}
) {
    val scrollState = rememberScrollState()

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .heightIn(280.dp)
            .horizontalScroll(scrollState)
    ) {

        for (book in listOfBook) {
            ListCard(book) {
                onCardPressed(it)
            }
        }
    }
}

@Composable
fun ReadingRightNowArea(books: List<MBook>, navController: NavController) {
    ListCard()
}


