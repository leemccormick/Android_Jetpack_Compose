package com.leemccormick.jetnote.model

import java.time.LocalDateTime
import java.util.*

data class Note(
    val id: UUID = UUID.randomUUID(),
    val title: String,
    val description: String,
    var entryDate: LocalDateTime = LocalDateTime.now()
)
