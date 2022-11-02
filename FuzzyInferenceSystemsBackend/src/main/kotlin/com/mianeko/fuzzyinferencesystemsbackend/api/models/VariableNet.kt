package com.mianeko.fuzzyinferencesystemsbackend.api.models

import com.mianeko.fuzzyinferencesystemsbackend.DTO.VariableDTO
import com.mianeko.fuzzyinferencesystemsbackend.services.models.VariableTemplateDTO

data class VariableNet(
    val name: String,
    val minValue: Double,
    val maxValue: Double,
    val value: Double?
) {
    fun toDTO(systemId: Int, variableId: Int) = VariableDTO(
        id = variableId,
        name = this.name,
        minValue = this.minValue,
        maxValue = this.maxValue,
        value = this.value,
        systemId = systemId
    )

    companion object {
        fun fromDTO(variable: VariableDTO) = VariableNet(
            name = variable.name,
            minValue = variable.minValue,
            maxValue = variable.maxValue,
            value = variable.value,
        )
    }
}

data class VariableTemplateNet(
    val name: String,
    val minValue: Double,
    val maxValue: Double,
    val value: Double?
) {
    fun toDTO(systemId: Int, variableId: Int?) = VariableTemplateDTO(
        id = variableId,
        name = this.name,
        minValue = this.minValue,
        maxValue = this.maxValue,
        value = this.value,
        systemId = systemId
    )
}