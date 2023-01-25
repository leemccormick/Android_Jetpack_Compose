package com.leemccormick.jetareader.screens.stats

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.ThumbUp
import androidx.compose.material.icons.sharp.Person
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.text.toUpperCase
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import coil.compose.rememberImagePainter
import com.google.firebase.auth.FirebaseAuth
import com.leemccormick.jetareader.components.ReaderAppBar
import com.leemccormick.jetareader.model.Item
import com.leemccormick.jetareader.model.MBook
import com.leemccormick.jetareader.navigation.ReaderScreens
import com.leemccormick.jetareader.screens.home.HomeScreenViewModel
import com.leemccormick.jetareader.screens.search.BookRow
import com.leemccormick.jetareader.utils.formatDate
import java.util.*

@Composable
fun ReaderStatsScreen(
    navController: NavHostController,
    viewModel: HomeScreenViewModel = hiltViewModel()
) {
    var books: List<MBook>
    val currentUser = FirebaseAuth.getInstance().currentUser

    Scaffold(
        topBar = {
            ReaderAppBar(
                title = "Book Stat",
                icon = Icons.Default.ArrowBack,
                showProfile = false,
                navController = navController
            ) {
                navController.popBackStack()
            }
        }
    ) {
        Surface() {
            // Only show books by this user that have been read
            books = if (!viewModel.data.value.data.isNullOrEmpty()) {
                viewModel.data.value.data!!.filter { mBook ->
                    (mBook.userId == currentUser?.uid)
                }
            } else {
                emptyList()
            }

            Column {
                Row() {
                    Box(
                        modifier = Modifier
                            .size(45.dp)
                            .padding(2.dp)
                    ) {
                        Icon(imageVector = Icons.Sharp.Person, contentDescription = "icon")
                    }

                    Text(
                        text = "H1, ${
                            currentUser?.email.toString().split("@")[0].uppercase(
                                Locale.getDefault()
                            )
                        }"
                    )
                }

                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(4.dp), shape = CircleShape, elevation = 5.dp
                ) {
                    val readBooksList: List<MBook> =
                        if (viewModel.data.value.data.isNullOrEmpty()) {
                            books.filter { mBook ->
                                (mBook.userId == currentUser?.uid) && (mBook.finishedReading != null)
                            }
                        } else {
                            emptyList()
                        }

                    val readingBooks = books.filter { mBook ->
                        (mBook.startedReading != null && mBook.finishedReading == null)
                    }

                    Column(
                        modifier = Modifier.padding(start = 25.dp, top = 4.dp, bottom = 4.dp),
                        horizontalAlignment = Alignment.Start
                    ) {
                        Text(text = "Your Stats", style = MaterialTheme.typography.h5)

                        Divider()

                        Text(text = "You' re reading : ${readingBooks.size} books.")

                        Text(text = "You' ve read : ${readBooksList.size} books.")
                    }
                }



                if (viewModel.data.value.loading == true) {
                    LinearProgressIndicator()
                } else {
                    Divider()

                    LazyColumn(
                        modifier = Modifier
                            .fillMaxWidth()
                            .fillMaxHeight(),
                        contentPadding = PaddingValues(16.dp)
                    ) {
                        // Filter books by finished one
                        val readBooks: List<MBook> =
                            if (!viewModel.data.value.data.isNullOrEmpty()) {
                                viewModel.data.value.data!!.filter { mBook ->
                                    (mBook.userId == currentUser?.uid) && (mBook.finishedReading != null)
                                }
                            } else {
                                emptyList()
                            }


                        items(items = readBooks) { book ->
                            Log.d("Book", "ReaderStatsScreen - Book is $book")
                            // Text(text = book.title.toString())
                            BookRowStats(book = book)
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun BookRowStats(book: MBook) {
    Card(
        modifier = Modifier
//            .clickable {
//                navController.navigate(ReaderScreens.DetailScreen.name + "/${book.id}")
//            }
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
                if (book.photoUrl.toString().isEmpty()!!) {
                    "https://g.christianbook.com/g/slideshow/5/599006/main/599006_1_ftc.jpg"
                } else {
                    book.photoUrl.toString()
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
                Row(horizontalArrangement = Arrangement.SpaceBetween) {
                    Text(text = book.title.toString(), overflow = TextOverflow.Ellipsis)

                    if (book.rating!! >= 4) {
                        Spacer(modifier = Modifier.fillMaxWidth(0.8f))

                        Icon(
                            imageVector = Icons.Default.ThumbUp,
                            contentDescription = "thumb up icon",
                            tint = Color.Green.copy(alpha = 0.5f)
                        )
                    } else {
                        Box {}
                    }
                }

                Text(
                    text = "Authors : ${book.authors}",
                    overflow = TextOverflow.Clip,
                    fontStyle = FontStyle.Italic,
                    style = MaterialTheme.typography.caption
                )

                val startedReading =
                    if (book.startedReading == null) "N/A" else formatDate(book.startedReading!!)
                Text(
                    text = "Started : $startedReading",
                    softWrap = true,
                    overflow = TextOverflow.Clip,
                    fontStyle = FontStyle.Italic,
                    style = MaterialTheme.typography.caption
                )

                val finishedReading =
                    if (book.finishedReading == null) "N/A" else formatDate(book.finishedReading!!)
                Text(
                    text = "Finished : $finishedReading}",
                    overflow = TextOverflow.Clip,
                    fontStyle = FontStyle.Italic,
                    style = MaterialTheme.typography.caption
                )
            }
        }
    }
}
