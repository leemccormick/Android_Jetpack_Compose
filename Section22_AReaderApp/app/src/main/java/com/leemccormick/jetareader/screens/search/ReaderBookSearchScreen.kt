package com.leemccormick.jetareader.screens.search

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import coil.compose.rememberImagePainter
import com.leemccormick.jetareader.components.InputFiled
import com.leemccormick.jetareader.components.ReaderAppBar
import com.leemccormick.jetareader.model.MBook
import com.leemccormick.jetareader.navigation.ReaderScreens

@Preview
@Composable
fun SearchScreen(navController: NavHostController = NavHostController(LocalContext.current)) {
    Scaffold(
        topBar = {
            ReaderAppBar(
                title = "Search Books",
                icon = Icons.Default.ArrowBack,
                navController = navController,
                showProfile = false
            ) {
                // navController.popBackStack() --> We can use this or the line below
                navController.navigate(ReaderScreens.ReaderHomeScreen.name)
            }
        }
    ) {
        Surface() {
            Column {
                SearchForm(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp)
                ) {
                    Log.d("SearchScreen", "Search Term is $it")
                }

                Spacer(modifier = Modifier.height(13.dp))

                BookList(navController)
            }
        }
    }
}

@Composable
fun BookList(navController: NavHostController) {
    val listOfBooks = listOf(
        MBook("adas", "Running1", "Me and you", "Hello world"),
        MBook("adas", "Running2", "Me and you", "Hello world"),
        MBook("adas", "Running3", "Me and you", "Hello world"),
        MBook("adas", "Running4", "Me and you", "Hello world"),
        MBook("adas", "Running5", "Me and you", "Hello world")
    )

    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        contentPadding = PaddingValues(16.dp)
    ) {
        items(items = listOfBooks) { book ->
            BookRow(book, navController)
        }
    }
}

@Composable
fun BookRow(book: MBook, navController: NavHostController) {
    Card(
        modifier = Modifier
            .clickable {
                //  navController.navigate(ReaderScreens.DetailScreen.name + "/${book.id}")
            }
            .fillMaxWidth()
            .height(100.dp)
            .padding(3.dp),
        shape = RectangleShape,
        elevation = 7.dp
    ) {
        Row(
            modifier = Modifier.padding(5.dp),
            verticalAlignment = Alignment.Top
        ) {
            val imageURL = "https://g.christianbook.com/g/slideshow/5/599006/main/599006_1_ftc.jpg"

            Image(
                painter = rememberImagePainter(data = imageURL),
                contentDescription = "book image",
                modifier = Modifier.width(80.dp).fillMaxHeight().padding(4.dp)
            )

            Column() {
                Text(text = book.title.toString(), overflow = TextOverflow.Ellipsis)

                Text(
                    text = "Authors : ${book.authors.toString()}",
                    overflow = TextOverflow.Ellipsis,
                    style = MaterialTheme.typography.caption
                )

                //TODO : Add more filed later
            }
        }
    }
}

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun SearchForm(
    modifier: Modifier = Modifier,
    loading: Boolean = false,
    hint: String = "Search",
    onSearch: (String) -> Unit = {}
) {
    Column() {
        val searchQueryState = rememberSaveable() { mutableStateOf("") }
        val keyboardController = LocalSoftwareKeyboardController.current
        val valid = remember(searchQueryState.value) {
            searchQueryState.value.trim().isNotEmpty()
        }

        InputFiled(
            valueState = searchQueryState,
            labelId = "Search",
            enabled = true,
            onAction = KeyboardActions {
                if (!valid) return@KeyboardActions // Do nothing

                onSearch(searchQueryState.value.trim())
                searchQueryState.value = ""
                keyboardController?.hide()
            }
        )
    }
}