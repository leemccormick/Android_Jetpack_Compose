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
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import coil.compose.rememberImagePainter
import com.leemccormick.jetareader.components.InputFiled
import com.leemccormick.jetareader.components.ReaderAppBar
import com.leemccormick.jetareader.model.Item
import com.leemccormick.jetareader.navigation.ReaderScreens

@Composable
fun SearchScreen(
    navController: NavController,
    viewModel: BooksSearchViewModel = hiltViewModel()
) {
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
                ) { query ->
                    Log.d("SearchScreen", "Search Term is $query")
                    viewModel.searchBooks(query)
                }

                Spacer(modifier = Modifier.height(13.dp))

                BookList(navController)
            }
        }
    }
}

/* This code before using Resource
@Composable
fun BookList(navController: NavController, viewModel: BooksSearchViewModel) {

    if (viewModel.listOfBooks.value.loading == true) {
        Log.d("Book", "BookList : loading...")
        CircularProgressIndicator()
    } else {
        Log.d("Book", "BookList : ${viewModel.listOfBooks.value.data}")
    }

     val listOfBooks = listOf(
        MBook("adas", "Running1", "Me and you", "Hello world"),
        MBook("adas", "Running2", "Me and you", "Hello world"),
        MBook("adas", "Running3", "Me and you", "Hello world"),
        MBook("adas", "Running4", "Me and you", "Hello world"),
        MBook("adas", "Running5", "Me and you", "Hello world")
    )
 */

@Composable
fun BookList(navController: NavController, viewModel: BooksSearchViewModel = hiltViewModel()) {
    val listOfBooks = viewModel.list
    Log.d("Book", "BookList : $listOfBooks")

    if (viewModel.isLoading) {
        LinearProgressIndicator()
    } else {
        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            contentPadding = PaddingValues(16.dp)
        ) {
            items(items = listOfBooks) { book ->
                BookRow(book, navController)
            }
        }
    }
}

@Composable
fun BookRow(book: Item, navController: NavController) {
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
            // This is an example image
            // val imageURL = "https://g.christianbook.com/g/slideshow/5/599006/main/599006_1_ftc.jpg"

            val imageURL: String =
                if (book.volumeInfo.imageLinks.smallThumbnail.isEmpty() == true) {
                    "https://g.christianbook.com/g/slideshow/5/599006/main/599006_1_ftc.jpg"
                } else {
                    book.volumeInfo.imageLinks.smallThumbnail
                }

            Image(
                painter = rememberImagePainter(data = imageURL),
                contentDescription = "book image",
                modifier = Modifier
                    .width(80.dp)
                    .fillMaxHeight()
                    .padding(4.dp)
            )

            Column() {
                Text(text = book.volumeInfo.title, overflow = TextOverflow.Ellipsis)

                Text(
                    text = "Authors : ${book.volumeInfo.authors}",
                    overflow = TextOverflow.Clip,
                    fontStyle = FontStyle.Italic,
                    style = MaterialTheme.typography.caption
                )

                Text(
                    text = "Date : ${book.volumeInfo.publishedDate}",
                    overflow = TextOverflow.Clip,
                    fontStyle = FontStyle.Italic,
                    style = MaterialTheme.typography.caption
                )

                Text(
                    text = "${book.volumeInfo.categories}",
                    overflow = TextOverflow.Clip,
                    fontStyle = FontStyle.Italic,
                    style = MaterialTheme.typography.caption
                )
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