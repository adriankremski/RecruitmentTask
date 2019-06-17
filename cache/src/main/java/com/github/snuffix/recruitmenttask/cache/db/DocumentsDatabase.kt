package com.github.snuffix.recruitmenttask.cache.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.github.snuffix.recruitmenttask.cache.model.DocumentCachedModel

@Database(entities = [DocumentCachedModel::class], version = 1)
@TypeConverters(DateTypeConverter::class)
abstract class DocumentsDatabase : RoomDatabase() {
    abstract fun documentsDao(): DocumentsDao

    companion object {
        private var INSTANCE: DocumentsDatabase? = null
        private val lock = Any()
        fun getInstance(context: Context): DocumentsDatabase {
            if (INSTANCE == null) {
                synchronized(lock) {
                    if (INSTANCE == null) {
                        INSTANCE = Room.databaseBuilder(
                            context.applicationContext, DocumentsDatabase::class.java, "documents.db"
                        ).build()
                    }
                    return INSTANCE!!
                }
            }
            return INSTANCE!!
        }
    }
}