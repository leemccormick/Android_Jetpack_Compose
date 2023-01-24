package com.leemccormick.jetareader.utils


import android.icu.text.DateFormat
import android.os.Build
import com.google.firebase.Timestamp

fun formatDate(timestamp: Timestamp): String {
    return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
        DateFormat.getDateInstance().format(timestamp.toDate()).toString().split(",")[0] // March 12
    } else {
        ""
    }
}

/* This is not working, need version check...
* fun formatDate(timestamp: Timestamp): String {
    return DateFormat.getDateInstance().format(timestamp.toDate()).toString().split(",")[0] // March 12
}*/