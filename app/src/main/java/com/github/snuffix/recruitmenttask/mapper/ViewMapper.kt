package com.github.snuffix.recruitmenttask.mapper

interface ViewMapper<in PresentationModel, out ViewItemModel> {
    fun mapToViewItem(presentation: PresentationModel): ViewItemModel
}