package com.github.snuffix.recruitmenttask.cache.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.github.snuffix.recruitmenttask.cache.model.DocumentCachedModel

@Dao
interface DocumentsDao {
    @Query(DocumentsDatabaseConstants.QUERY_COUNT)
    suspend fun countDocuments(): Int

    @Query(DocumentsDatabaseConstants.QUERY_DOCUMENTS)
    suspend fun queryDocuments(): List<DocumentCachedModel>

    @Query(DocumentsDatabaseConstants.QUERY_DOCUMENT)
    suspend fun queryDocument(id: String): DocumentCachedModel

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertDocuments(documents: List<DocumentCachedModel>)
}