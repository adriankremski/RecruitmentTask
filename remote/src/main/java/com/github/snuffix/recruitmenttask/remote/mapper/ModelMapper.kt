package com.github.snuffix.recruitmenttask.remote.mapper

interface ModelMapper<in Model, out Entity> {
    fun mapFromModel(model: Model): Entity
}