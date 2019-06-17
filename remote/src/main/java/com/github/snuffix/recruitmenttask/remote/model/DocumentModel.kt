package com.github.snuffix.recruitmenttask.remote.model

import java.util.*

data class DocumentModel(
    val name: String,
    val url: String,
    val type: String,
    val description: String,
    val creationDate: Date
)