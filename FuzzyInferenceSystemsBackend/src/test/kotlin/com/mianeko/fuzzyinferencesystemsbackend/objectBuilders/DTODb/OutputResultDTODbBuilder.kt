package com.mianeko.fuzzyinferencesystemsbackend.objectBuilders.DTODb

import com.mianeko.fuzzyinferencesystemsbackend.DTODb.OutputResultDTODb

class OutputResultDTODbBuilder private constructor(
    private var variableName: String = defaultName,
    private var variableValue: Double = defaultValue
) {
    fun build() = OutputResultDTODb(
        name  = variableName,
        value = variableValue
    )

    fun withVariableName(variableName: String): OutputResultDTODbBuilder {
        this.variableName = variableName
        return this
    }

    fun withValue(variableValue: Double): OutputResultDTODbBuilder {
        this.variableValue = variableValue
        return this
    }

    companion object {
        val defaultName: String = "out1"
        val defaultValue: Double = 10.1

        fun nOutputResultDTODb() = OutputResultDTODbBuilder()
    }
}