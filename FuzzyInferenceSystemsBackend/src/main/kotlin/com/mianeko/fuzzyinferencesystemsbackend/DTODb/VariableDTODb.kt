package com.mianeko.fuzzyinferencesystemsbackend.DTODb

import com.mianeko.fuzzyinferencesystemsbackend.database.entities.DBInsertableVariable
import com.mianeko.fuzzyinferencesystemsbackend.database.entities.DBVariable
import com.mianeko.fuzzyinferencesystemsbackend.services.models.Variable
import com.mianeko.fuzzyinferencesystemsbackend.services.models.VariableTemplate


data class VariableDTODb(
    val id: Int,
    val name: String,
    val minValue: Double,
    val maxValue: Double,
    val value: Double?,
    val system: SystemDTODb
) {
    fun toModel() = Variable(
        id = this.id,
        name = this.name,
        minValue = this.minValue,
        maxValue = this.maxValue,
        value = this.value,
        inferenceSystem = this.system.toModel()
    )

    fun toModelDb() = DBVariable(
        id = this.id,
        name = this.name,
        minValue = this.minValue,
        maxValue = this.maxValue,
        value = this.value,
        system = system.toModelDb()
    )

    companion object {
        fun fromModel(variable: Variable) = VariableDTODb(
            id = variable.id,
            name = variable.name,
            minValue = variable.minValue,
            maxValue = variable.maxValue,
            value = variable.value,
            system = SystemDTODb.fromModel(variable.inferenceSystem)
        )

        fun fromModelDb(dbVariable: DBVariable) = VariableDTODb(
            id = dbVariable.id,
            name = dbVariable.name,
            minValue = dbVariable.minValue,
            maxValue = dbVariable.maxValue,
            value = dbVariable.value,
            system = SystemDTODb.fromModelDb(dbVariable.system)
        )
    }
}

data class VariableTemplateDTODb(
    val id: Int,
    val name: String,
    val minValue: Double,
    val maxValue: Double,
    val value: Double?,
    val systemId: Int
) {
    fun toModelDb() = DBInsertableVariable(
        id = this.id,
        name = this.name,
        minValue = this.minValue,
        maxValue = this.maxValue,
        value = this.value,
        systemId = this.systemId
    )

    companion object {
        fun fromModel(variable: VariableTemplate) = VariableTemplateDTODb(
            id = variable.id,
            name = variable.name,
            minValue = variable.minValue,
            maxValue = variable.maxValue,
            value = variable.value,
            systemId = variable.systemId
        )
    }
}
