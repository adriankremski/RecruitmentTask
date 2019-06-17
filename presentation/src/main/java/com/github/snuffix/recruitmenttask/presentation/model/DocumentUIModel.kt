package com.github.snuffix.recruitmenttask.presentation.model

import java.util.*

data class DocumentUIModel(
    val id: String,
    val name: String,
    val type: String,
    val description: String,
    val creationDate: Date
)
