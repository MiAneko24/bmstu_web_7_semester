package com.mianeko.fuzzyinferencesystemsbackend.DTONet

import com.mianeko.fuzzyinferencesystemsbackend.api.models.PartialVariableNet
import com.mianeko.fuzzyinferencesystemsbackend.api.models.VariableNet
import com.mianeko.fuzzyinferencesystemsbackend.api.models.VariableTemplateNet
import com.mianeko.fuzzyinferencesystemsbackend.services.models.Variable
import com.mianeko.fuzzyinferencesystemsbackend.services.models.VariableTemplate

data class VariableDTONet(
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

    fun toModelNet() = VariableNet(
        name = this.name,
        minValue = this.minValue,
        maxValue = this.maxValue,
        value = this.value,
    )

    fun toPartialModelNet() = PartialVariableNet(
        id = this.id,
        name = this.name
    )

    companion object {
        fun fromModel(variable: Variable) = VariableDTONet(
            id = variable.id,
            name = variable.name,
            minValue = variable.minValue,
            maxValue = variable.maxValue,
            value = variable.value,
            systemId = variable.inferenceSystem.id
        )
    }
}

data class PartialVariableDTONet(
    val id: Int,
    val name: String,
    val systemId: Int
) {
    fun toModelNet() = PartialVariableNet(
        id = this.id,
        name = this.name
    )
    companion object {
        fun fromModel(variable: Variable) = PartialVariableDTONet(
            id = variable.id,
            name = variable.name,
            systemId = variable.inferenceSystem.id
        )
    }
}


data class VariableTemplateDTONet(
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

    companion object {
        fun fromModelNet(
            variableTemplateNet: VariableTemplateNet,
            systemId: Int,
            variableId: Int?
        ) = VariableTemplateDTONet(
            id = variableId,
            name = variableTemplateNet.name,
            minValue = variableTemplateNet.minValue,
            maxValue = variableTemplateNet.maxValue,
            value = variableTemplateNet.value,
            systemId = systemId
        )
    }
}
