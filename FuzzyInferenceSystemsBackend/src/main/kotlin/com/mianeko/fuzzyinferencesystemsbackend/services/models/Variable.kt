package com.mianeko.fuzzyinferencesystemsbackend.services.models

import com.mianeko.fuzzyinferencesystemsbackend.DTO.PartialVariableDTO
import com.mianeko.fuzzyinferencesystemsbackend.DTO.VariableDTO

data class Variable(
    val id: Int,
    val name: String,
    val minValue: Double,
    val maxValue: Double,
    val value: Double?,
    val inferenceSystem: InferenceSystem
) {
    fun toDTO() = VariableDTO(
        id = this.id,
        name = this.name,
        minValue = this.minValue,
        maxValue = this.maxValue,
        value = this.value,
        systemId = this.inferenceSystem.id
    )

    fun toPartialDTO() = PartialVariableDTO(
        id = this.id,
        name = this.name,
        systemId = this.inferenceSystem.id
    )
}

data class VariableTemplate(
    val id: Int,
    val name: String,
    val minValue: Double,
    val maxValue: Double,
    val value: Double?,
    val systemId: Int
)

data class VariableTemplateDTO(
    val id: Int?,
    val name: String,
    val minValue: Double,
    val maxValue: Double,
    val value: Double?,
    val systemId: Int
) {
    fun toModel(id: Int) = VariableTemplate(
        id = id,
        name = this.name,
        minValue = this.minValue,
        maxValue = this.maxValue,
        value = this.value,
        systemId = this.systemId
    )
}
