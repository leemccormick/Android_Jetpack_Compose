package com.leemccormick.jetnote

import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import com.leemccormick.jetnote.data.NotesDataSource
import com.leemccormick.jetnote.model.Note
import com.leemccormick.jetnote.screen.NoteScreen
import com.leemccormick.jetnote.screen.NoteViewModel
import com.leemccormick.jetnote.ui.theme.JetNoteTheme
import dagger.hilt.android.AndroidEntryPoint

// This is a dependency container using @AndroidEntryPoint
@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            JetNoteTheme {
                Surface(color = MaterialTheme.colors.background) {

                    // Using ViewModel and Source of Truth here...
                    // val noteViewModel: NoteViewModel by viewModels() --> Same as the line below
                    val noteViewModel = viewModel<NoteViewModel>()

                    NotesApp(noteViewModel)
                }
            }
        }
    }
}

@Composable
fun NotesApp(noteViewModel: NoteViewModel) {
    val notesList = noteViewModel.noteList.collectAsState().value

    NoteScreen(
        notes = notesList,
        onAddNote = { noteViewModel.addNote(it) },
        onRemoveNote = { noteViewModel.removeNote(it) }
    )
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    JetNoteTheme {
        NoteScreen(notes = NotesDataSource().loadNote(),
            onAddNote = {},
            onRemoveNote = {}
        )
    }
}