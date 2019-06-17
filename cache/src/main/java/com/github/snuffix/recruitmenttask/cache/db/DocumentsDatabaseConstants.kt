package com.github.snuffix.recruitmenttask.cache.db

object DocumentsDatabaseConstants {
    const val TABLE_NAME = "documents"
    const val QUERY_DOCUMENTS = "SELECT * FROM $TABLE_NAME"
    const val QUERY_COUNT = "SELECT count(*) FROM $TABLE_NAME"
}