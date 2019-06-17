package com.github.snuffix.recruitmenttask.cache.db

object DocumentsDatabaseConstants {
    const val TABLE_NAME = "documents"
    const val ID_COLUMN_NAME = "id"
    const val QUERY_DOCUMENTS = "SELECT * FROM $TABLE_NAME"
    const val QUERY_DOCUMENT = "SELECT * FROM $TABLE_NAME WHERE $ID_COLUMN_NAME = :$ID_COLUMN_NAME"
    const val QUERY_COUNT = "SELECT count(*) FROM $TABLE_NAME"
}