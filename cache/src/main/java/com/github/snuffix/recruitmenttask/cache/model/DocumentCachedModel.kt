package com.github.snuffix.recruitmenttask.cache.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.github.snuffix.recruitmenttask.cache.db.DocumentsDatabaseConstants
import java.util.*

@Entity(tableName = DocumentsDatabaseConstants.TABLE_NAME)
data class DocumentCachedModel(
    @PrimaryKey
    val id: String,
    val name: String,
    val url: String,
    val type: String,
    val description: String,
    val creationDate: Date
)
