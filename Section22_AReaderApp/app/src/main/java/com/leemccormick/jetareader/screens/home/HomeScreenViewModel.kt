package com.leemccormick.jetareader.screens.home

import android.util.Log
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.leemccormick.jetareader.data.DataOrException
import com.leemccormick.jetareader.model.Book
import com.leemccormick.jetareader.model.MBook
import com.leemccormick.jetareader.repository.FireRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeScreenViewModel @Inject constructor(
    private val repository: FireRepository
) : ViewModel() {
    val data: MutableState<DataOrException<List<MBook>, Boolean, Exception>> =
        mutableStateOf(DataOrException(listOf(), true, Exception("")))

    init {
        getAllBooksFromDatabase()
    }

    private fun getAllBooksFromDatabase() {
        viewModelScope.launch {
            data.value.loading = true
            data.value = repository.getAllBooksFromDatabase()

            if (!data.value.data.isNullOrEmpty()) data.value.loading = false

            Log.d("GET", "getAllBooksFromDatabase : ${data.value.data?.toList().toString()}")
        }
    }
}