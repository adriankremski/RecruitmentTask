package com.github.snuffix.recruitmenttask.model

import org.joda.time.DateTime

data class DocumentViewItem(
    val id: String,
    val name: String,
    val type: String,
    val description: String,
    val creationDate: DateTime
) : ViewItem {
    fun isPdf(): Boolean = type == "pdf"
}
