package com.leemccormick.jetareader.screens.update

import android.content.Context
import android.os.Message
import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import coil.compose.rememberImagePainter
import com.google.firebase.Timestamp
import com.google.firebase.firestore.FirebaseFirestore
import com.leemccormick.jetareader.R
import com.leemccormick.jetareader.components.InputFiled
import com.leemccormick.jetareader.components.RatingBar
import com.leemccormick.jetareader.components.ReaderAppBar
import com.leemccormick.jetareader.components.RoundedButton
import com.leemccormick.jetareader.data.DataOrException
import com.leemccormick.jetareader.model.MBook
import com.leemccormick.jetareader.navigation.ReaderScreens
import com.leemccormick.jetareader.screens.home.HomeScreenViewModel
import com.leemccormick.jetareader.utils.formatDate
import com.leemccormick.jetareader.screens.update.ShowBookUpdate as ShowBookUpdate1

@Composable
fun BookUpdateScreen(
    navController: NavHostController,
    bookItemId: String,
    viewModel: HomeScreenViewModel = hiltViewModel()
) {
    Scaffold(
        topBar = {
            ReaderAppBar(
                title = "Update Book",
                icon = Icons.Default.ArrowBack,
                showProfile = false,
                navController = navController
            ) {
                navController.popBackStack()
            }
        }
    ) {
        val bookInfo = produceState<DataOrException<List<MBook>, Boolean, Exception>>(
            initialValue = DataOrException(data = emptyList(), true, Exception(""))
        ) {
            value = viewModel.data.value
        }.value

        Surface(
            modifier = Modifier
                .fillMaxSize()
                .padding(3.dp)
        ) {
            Column(
                modifier = Modifier.padding(top = 3.dp),
                verticalArrangement = Arrangement.Top,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Log.d("INFO", "BookUpdateScreen : ${viewModel.data.value.data.toString()}")

                if (bookInfo.loading == true) {
                    LinearProgressIndicator()
                    bookInfo.loading = false
                } else {
                    Surface(
                        modifier = Modifier
                            .padding(2.dp)
                            .fillMaxWidth(),
                        shape = CircleShape,
                        elevation = 4.dp,
                    ) {
                        ShowBookUpdate1(bookInfo = viewModel.data.value, bookItemId = bookItemId)
                    }

                    ShowSimpleForm(
                        book = viewModel.data.value.data?.first { mBook ->
                            mBook.googleBookId == bookItemId
                        }!!,
                        navController
                    )
                }
            }
        }
    }
}

@Composable
fun ShowSimpleForm(book: MBook, navController: NavHostController) {
    val notesText = remember { mutableStateOf("") }
    val isStartedReading = remember { mutableStateOf(false) }
    val isFinishedReading = remember { mutableStateOf(false) }
    val ratingVal = remember { mutableStateOf(0) }
    val context = LocalContext.current

    SimpleForm(
        defaultValue = if (book.notes.toString()
                .isNotEmpty()
        ) book.notes.toString() else "No thoughts available. "
    ) { note ->
        notesText.value = note
    }

    Row(
        modifier = Modifier.padding(4.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Start
    ) {
        TextButton(
            onClick = { isStartedReading.value = true },
            enabled = book.startedReading == null
        ) {
            if (book.startedReading == null) {
                if (!isStartedReading.value) {
                    Text(text = "Start Reading")
                } else {
                    Text(
                        text = "Started Reading!",
                        modifier = Modifier.alpha(0.6f),
                        color = Color.Red.copy(alpha = 0.5f)
                    )
                }
            } else {
                Text("Started on: ${formatDate(book.startedReading!!)}")
            }
        }

        Spacer(modifier = Modifier.height(4.dp))

        TextButton(
            onClick = { isFinishedReading.value = true },
            enabled = book.finishedReading == null
        ) {
            if (book.finishedReading == null) {
                if (!isFinishedReading.value) {
                    Text(text = "Mark as Read")
                } else {
                    Text(text = "Finished Reading!")
                }
            } else {
                Text(text = "Finished on: ${formatDate(book.finishedReading!!)}")
            }
        }
    }

    Text(text = "Rating", modifier = Modifier.padding(bottom = 3.dp))

    book.rating?.toInt().let {
        RatingBar(rating = it!!) { rating ->
            ratingVal.value = rating
            Log.d("TAG", "ShowSimpleForm: ${ratingVal.value}")
        }
    }

    Spacer(modifier = Modifier.padding(bottom = 15.dp))

    val changedNote = book.notes != notesText.value
    val changedRating = book.rating?.toInt() != ratingVal.value
    val isFinishedTimeStamp = if (isFinishedReading.value) Timestamp.now() else book.finishedReading
    val isStartedTimeStamp = if (isStartedReading.value) Timestamp.now() else book.startedReading
    val bookUpdate = changedNote || changedNote || isStartedReading.value || isFinishedReading.value
    val bookToUpdate = hashMapOf(
        "finished_reading_at" to isFinishedTimeStamp,
        "started_reading_at" to isStartedTimeStamp,
        "rating" to ratingVal.value,
        "notes" to notesText.value
    ).toMap()

    Row {
        RoundedButton("Update") {
            if (bookUpdate) {
                FirebaseFirestore.getInstance()
                    .collection("books")
                    .document(book.id!!)
                    .update(bookToUpdate)
                    .addOnCompleteListener { task ->
                        Log.d("Update", "ShowSimpleForm: ${task.result.toString()}")
                        showToast(context = context, "Book updated successfully !")
                        navController.navigate(ReaderScreens.ReaderHomeScreen.name)
                    }
                    .addOnFailureListener {
                        Log.w("Error", "Error updating document", it)
                    }
            }
        }

        Spacer(modifier = Modifier.width(100.dp))

        val openDialog = remember { mutableStateOf(false) }
        if (openDialog.value) {
            ShowAlertDialog(
                message = stringResource(id = R.string.sure) + "\n" + stringResource(id = R.string.action),
                openDialog = openDialog
            ) {
                FirebaseFirestore.getInstance()
                    .collection("books")
                    .document(book.id!!)
                    .delete()
                    .addOnCompleteListener {
                        if (it.isSuccessful) {
                            openDialog.value = false

                            // navController.popBackStack() --> also work but not recompose
                            navController.navigate(ReaderScreens.ReaderHomeScreen.name)
                        }
                    }
            }
        }

        RoundedButton("Delete") {
            openDialog.value = true
        }
    }
}

@Composable
fun ShowAlertDialog(
    message: String,
    openDialog: MutableState<Boolean>,
    onYesPressed: () -> Unit
) {
    if (openDialog.value) {
        AlertDialog(
            onDismissRequest = { openDialog.value = false },
            title = { Text(text = "Delete Book") },
            text = { Text(text = message) },
            buttons = {
                Row(
                    modifier = Modifier.padding(8.dp),
                    horizontalArrangement = Arrangement.Center
                ) {
                    TextButton(onClick = { onYesPressed.invoke() }) {
                        Text(text = "Yes")
                    }

                    TextButton(onClick = { openDialog.value = false }) {
                        Text(text = "No")
                    }
                }
            }
        )
    }
}

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun SimpleForm(
    modifier: Modifier = Modifier,
    loading: Boolean = false,
    defaultValue: String = "Great Book!",
    onSearch: (String) -> Unit
) {
    Column() {
        val textFiledValue = rememberSaveable { mutableStateOf(defaultValue) }
        val keyboardController = LocalSoftwareKeyboardController.current
        val valid = remember(textFiledValue.value) { textFiledValue.value.trim().isNotEmpty() }

        InputFiled(
            modifier
                .fillMaxWidth()
                .height(140.dp)
                .padding(3.dp)
                .background(Color.White, CircleShape)
                .padding(horizontal = 20.dp, vertical = 12.dp),
            valueState = textFiledValue,
            labelId = "Enter Your thoughts",
            enabled = true,
            onAction = KeyboardActions {
                if (!valid) return@KeyboardActions
                onSearch(textFiledValue.value.trim())
                keyboardController?.hide()
            }
        )
    }
}

@Composable
fun ShowBookUpdate(
    bookInfo: DataOrException<List<MBook>, Boolean, Exception>,
    bookItemId: String
) {
    Row() {
        Spacer(modifier = Modifier.width(43.dp))

        if (bookInfo.data != null) {
            Column(
                modifier = Modifier.padding(4.dp),
                verticalArrangement = Arrangement.Center
            ) {
                CardListItem(
                    book = bookInfo.data!!.first { mBook ->
                        mBook.googleBookId == bookItemId
                    },
                    onPressDetails = {}
                )
            }
        }
    }
}

@Composable
fun CardListItem(book: MBook, onPressDetails: () -> Unit) {
    Card(
        modifier = Modifier
            .padding(start = 4.dp, end = 4.dp, top = 4.dp, bottom = 8.dp)
            .clip(RoundedCornerShape(20.dp))
            .clickable {},
        elevation = 8.dp
    ) {
        Row(horizontalArrangement = Arrangement.Start) {
            Image(
                painter = rememberImagePainter(data = book.photoUrl.toString()),
                contentDescription = null,
                modifier = Modifier
                    .height(100.dp)
                    .width(120.dp)
                    .padding(4.dp)
                    .clip(
                        RoundedCornerShape(
                            topStart = 120.dp, topEnd = 20.dp, bottomEnd = 0.dp, bottomStart = 0.dp
                        )
                    )
            )

            Column {
                Text(
                    text = book.title.toString(),
                    style = MaterialTheme.typography.h6,
                    modifier = Modifier
                        .padding(start = 8.dp, end = 8.dp)
                        .width(120.dp),
                    fontWeight = FontWeight.SemiBold,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis
                )

                Text(
                    text = book.authors.toString(),
                    style = MaterialTheme.typography.body2,
                    modifier = Modifier.padding(
                        start = 8.dp,
                        end = 8.dp,
                        top = 2.dp,
                        bottom = 0.dp
                    )
                )

                Text(
                    text = book.publishedDate.toString(),
                    style = MaterialTheme.typography.body2,
                    modifier = Modifier.padding(
                        start = 8.dp,
                        end = 8.dp,
                        top = 0.dp,
                        bottom = 8.dp
                    )
                )
            }
        }
    }
}

fun showToast(context: Context, message: String) {
    Toast.makeText(context, message, Toast.LENGTH_LONG).show()
}