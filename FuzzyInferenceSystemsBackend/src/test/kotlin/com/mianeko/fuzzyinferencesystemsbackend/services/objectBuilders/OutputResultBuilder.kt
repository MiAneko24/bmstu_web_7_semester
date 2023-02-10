package com.mianeko.fuzzyinferencesystemsbackend.services.objectBuilders

import com.mianeko.fuzzyinferencesystemsbackend.services.models.OutputResult

class OutputResultBuilder private constructor(
    private var variableName: String = defaultName,
    private var variableValue: Double = defaultValue
) {
    fun build() = OutputResult(
        name  = variableName,
        value = variableValue
    )

    fun withVariableName(variableName: String): OutputResultBuilder {
        this.variableName = variableName
        return this
    }

    fun withValue(variableValue: Double): OutputResultBuilder {
        this.variableValue = variableValue
        return this
    }

    companion object {
        val defaultName: String = "out1"
        val defaultValue: Double = 10.1

        fun nOutputResult() = OutputResultBuilder()
    }
}