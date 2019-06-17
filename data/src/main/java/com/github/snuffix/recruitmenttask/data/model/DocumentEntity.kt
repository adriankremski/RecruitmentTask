package com.github.snuffix.recruitmenttask.data.model

import java.util.*

data class DocumentEntity(val id: String? = null,
                          val name: String,
                          val url: String,
                          val type: String,
                          val description: String,
                          val creationDate: Date
)
