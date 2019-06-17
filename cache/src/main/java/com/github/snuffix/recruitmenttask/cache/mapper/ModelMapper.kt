package com.github.snuffix.recruitmenttask.cache.mapper

interface ModelMapper<Model, Entity> {
    fun mapFromModel(model: Model): Entity
    fun mapFromEntity(model: Entity): Model
}