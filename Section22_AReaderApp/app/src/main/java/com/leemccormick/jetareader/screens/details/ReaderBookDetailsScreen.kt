package com.leemccormick.jetareader.screens.details

import android.util.Log
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.runtime.produceState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.core.text.HtmlCompat
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import coil.compose.rememberImagePainter
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.leemccormick.jetareader.components.ReaderAppBar
import com.leemccormick.jetareader.components.RoundedButton
import com.leemccormick.jetareader.data.Resource
import com.leemccormick.jetareader.model.Item
import com.leemccormick.jetareader.model.MBook
import com.leemccormick.jetareader.navigation.ReaderScreens

@Composable
fun BookDetailScreen(
    navController: NavHostController,
    bookId: String,
    viewModel: DetailsViewModel = hiltViewModel()
) {
    Scaffold(
        topBar = {
            ReaderAppBar(
                title = "Book Details",
                icon = Icons.Default.ArrowBack,
                showProfile = false,
                navController = navController
            ) {
                navController.navigate(ReaderScreens.SearchScreen.name)
            }
        }
    ) {
        Surface(
            modifier = Modifier
                .padding(3.dp)
                .fillMaxWidth()
        ) {
            Column(
                modifier = Modifier.padding(top = 12.dp),
                verticalArrangement = Arrangement.Top,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                val bookInfo = produceState<Resource<Item>>(initialValue = Resource.Loading()) {
                    value = viewModel.getBookInfo(bookId)
                }.value

                Log.d("Details", "Book Detail Screen : ${bookInfo.data.toString()}")

                if (bookInfo.data == null) {
                    Row(
                        modifier = Modifier.padding(start = 20.dp, end = 20.dp),
                        horizontalArrangement = Arrangement.SpaceAround,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        LinearProgressIndicator()
                        Text(text = "Loading...")
                    }
                } else {
                    ShowBookDetails(bookInfo, navController)
                }
            }
        }
    }
}

@Composable
fun ShowBookDetails(bookInfo: Resource<Item>, navController: NavHostController) {
    val bookData = bookInfo.data?.volumeInfo
    val googleBookId = bookInfo.data?.id

    // Book Image
    Card(
        modifier = Modifier.padding(34.dp),
        shape = CircleShape,
        elevation = 4.dp
    ) {
        Image(
            painter = rememberImagePainter(data = bookData!!.imageLinks.thumbnail),
            contentDescription = "book image",
            modifier = Modifier
                .width(90.dp)
                .height(90.dp)
                .padding(1.dp)
        )
    }

    // Book Details with Text
    Text(
        text = bookData?.title.toString(),
        style = MaterialTheme.typography.h6,
        overflow = TextOverflow.Ellipsis,
        maxLines = 19
    )

    Text(text = "Authors : ${bookData?.authors.toString()}")

    Text(text = "Page Count : ${bookData?.pageCount.toString()}")

    Text(
        text = "Categories : ${bookData?.categories.toString()}",
        style = MaterialTheme.typography.subtitle1,
        overflow = TextOverflow.Ellipsis,
        maxLines = 3
    )

    Text(
        text = "Published : ${bookData?.publishedDate.toString()}",
        style = MaterialTheme.typography.subtitle1
    )

    Spacer(modifier = Modifier.height(5.dp))

    val cleanDescription =
        HtmlCompat.fromHtml(bookData!!.description, HtmlCompat.FROM_HTML_MODE_LEGACY).toString()
    val localDims = LocalContext.current.resources.displayMetrics

    // Description
    Surface(
        modifier = Modifier
            .height(localDims.heightPixels.dp.times(0.09f))
            .padding(4.dp),
        shape = RectangleShape,
        border = BorderStroke(1.dp, Color.DarkGray)
    ) {
        LazyColumn(modifier = Modifier.padding(3.dp)) {
            item {
                Text(text = cleanDescription)
            }
        }
    }

    // Save and Cancel Buttons
    Row(
        modifier = Modifier.padding(top = 6.dp),
        horizontalArrangement = Arrangement.SpaceAround
    ) {
        RoundedButton(label = "Save") {
            val book = MBook(
                title = bookData.title,
                authors = bookData.authors.toString(),
                description = bookData.description,
                categories = bookData.categories.toString(),
                notes = "",
                photoUrl = bookData.imageLinks.thumbnail,
                publishedDate = bookData.publishedDate,
                pageCount = bookData.pageCount.toString(),
                rating = 0.0,
                googleBookId = googleBookId,
                userId = FirebaseAuth.getInstance().currentUser?.uid.toString(),
            )

            saveToFirebase(book, navController)
        }

        Spacer(modifier = Modifier.width(25.dp))

        RoundedButton(label = "Cancel") {
            navController.popBackStack()
        }
    }
}

fun saveToFirebase(book: MBook, navController: NavController) {
    val db = FirebaseFirestore.getInstance()
    val dbCollection = db.collection("books")

    if (book.toString().isNotEmpty()) {
        dbCollection.add(book)
            .addOnSuccessListener { documentRef -> // Everytime that save in firebase, we get document ref and use this to save in user collection
                val docId = documentRef.id
                dbCollection.document(docId)
                    .update(hashMapOf("id" to docId) as Map<String, Any>)
                    .addOnCompleteListener { task ->
                        if (task.isSuccessful) {
                            navController.popBackStack()
                        }
                    }
                    .addOnFailureListener {
                        Log.w("Error", "SaveToFirebase --> Error updating doc", it)
                    }
            }
    } else {
        Log.w("Error", "SaveToFirebase --> book.toString() is empty.")
    }
}



