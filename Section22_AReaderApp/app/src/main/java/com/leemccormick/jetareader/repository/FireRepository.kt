package com.leemccormick.jetareader.repository

import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.FirebaseFirestoreException
import com.google.firebase.firestore.Query
import com.google.firebase.firestore.ktx.toObject
import com.leemccormick.jetareader.data.DataOrException
import com.leemccormick.jetareader.model.MBook
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

// After create this need to add Provide on AppModule
class FireRepository @Inject constructor(private val queryBook: Query) {
    suspend fun getAllBooksFromDatabase(): DataOrException<List<MBook>, Boolean, Exception> {
        val dataOrException = DataOrException<List<MBook>, Boolean, Exception>()

        try {
            dataOrException.loading = true

            // await() --> is from kotlin Coroutines in build gradle
            dataOrException.data = queryBook.get().await().documents.map { documentSnapshot ->
                documentSnapshot.toObject(MBook::class.java)!!
            }

            if (!dataOrException.data.isNullOrEmpty()) dataOrException.loading = false

        } catch (exception: FirebaseFirestoreException) {
            dataOrException.e = exception
        }

        return dataOrException
    }
}