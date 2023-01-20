package com.leemccormick.jetareader.repository

import com.leemccormick.jetareader.data.DataOrException
import com.leemccormick.jetareader.model.Item
import com.leemccormick.jetareader.network.BooksApi
import retrofit2.http.Query
import javax.inject.Inject

class BookRepository @Inject constructor(private val api: BooksApi) {
    private val dataOrException = DataOrException<List<Item>, Boolean, Exception>()
    private val bookInfoDataOrException = DataOrException<Item, Boolean, Exception>()

    // suspend fun getBooks(searchQueryTerm: String): List<Item> {} --> It could be like this but better to wrap in another data class
    suspend fun getBooks(searchQueryTerm: String): DataOrException<List<Item>, Boolean, Exception> {
        try {
            dataOrException.loading = true
            dataOrException.data = api.getAllBooks(searchQueryTerm).items
            if (dataOrException.data!!.isNotEmpty()) dataOrException.loading = false

        } catch (e: Exception) {
            dataOrException.e = e
        }

        return dataOrException
    }

    suspend fun getBookInfo(bookId: String): DataOrException<Item, Boolean, Exception> {
        val response = try {
            bookInfoDataOrException.loading = true
            bookInfoDataOrException.data = api.getBookInfo(bookId = bookId)
            if (bookInfoDataOrException.data.toString()
                    .isNotEmpty()
            ) bookInfoDataOrException.loading = false else {
            }

        } catch (e: Exception) {
            bookInfoDataOrException.e = e
        }

        return bookInfoDataOrException
    }
}