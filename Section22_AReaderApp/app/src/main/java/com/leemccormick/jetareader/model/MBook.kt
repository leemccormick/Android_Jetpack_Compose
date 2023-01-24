package com.leemccormick.jetareader.model

import com.google.firebase.firestore.Exclude
import com.google.firebase.firestore.PropertyName
import java.security.Timestamp

data class MBook(
    // @Exclude --> Do not need to pass this id to firebase fire store.
    @Exclude var id: String? = null,

    var title: String? = null,
    var authors: String? = null,
    var notes: String? = null,

    // To rename and match the name in firebase fire store using PropertyName @get / @set
    @get:PropertyName("book_photo_url")
    @set:PropertyName("book_photo_url")
    var photoUrl: String? = null,
    var categories: String? = null,

    @get:PropertyName("published_date")
    @set:PropertyName("published_date")
    var publishedDate: String? = null,

    var rating: Double? = null,
    var description: String? = null,

    @get:PropertyName("page_count")
    @set:PropertyName("page_count")
    var pageCount: String? = null,

    @get:PropertyName("started_reading_at")
    @set:PropertyName("started_reading_at")
    var startedReading: Timestamp? = null,

    @get:PropertyName("finished_reading_at")
    @set:PropertyName("finished_reading_at")
    var finishedReading: Timestamp? = null,

    @get:PropertyName("user_id")
    @set:PropertyName("user_id")
    var userId: String? = null,

    @get:PropertyName("google_book_id")
    @set:PropertyName("google_book_id")
    var googleBookId: String? = null
)

