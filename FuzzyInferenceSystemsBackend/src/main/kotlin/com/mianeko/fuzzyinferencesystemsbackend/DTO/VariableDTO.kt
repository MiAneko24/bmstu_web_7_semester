package com.mianeko.fuzzyinferencesystemsbackend.DTO

import com.mianeko.fuzzyinferencesystemsbackend.services.models.VariableTemplate


data class VariableDTO(
    val id: Int,
    val name: String,
    val minValue: Double,
    val maxValue: Double,
    val value: Double?,
    val systemId: Int
) {
    fun toModel() = VariableTemplate(
        id = this.id,
        name = this.name,
        minValue = this.minValue,
        maxValue = this.maxValue,
        value = this.value,
        systemId = this.systemId
    )
}

data class PartialVariableDTO(
    val id: Int,
    val name: String,
    val systemId: Int
)