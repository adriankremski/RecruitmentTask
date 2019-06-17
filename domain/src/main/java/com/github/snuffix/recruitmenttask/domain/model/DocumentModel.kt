package com.github.snuffix.recruitmenttask.domain.model

import java.util.*

data class DocumentModel(
    val id: String,
    val name: String,
    val type: String,
    val description: String,
    val creationDate: Date
)