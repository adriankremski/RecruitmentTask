package com.github.snuffix.recruitmenttask.presentation.model

enum class DocumentSortOrder(val displayName: String) {
    // In production app, names should not be hardcoded
    BY_NAME_ASC("Sort by name ascending"),
    BY_NAME_DESC("Sort by name descending"),
    BY_DATE_ASC("Sort by date ascending"),
    BY_DATE_DESC("Sort by date descending")
}
