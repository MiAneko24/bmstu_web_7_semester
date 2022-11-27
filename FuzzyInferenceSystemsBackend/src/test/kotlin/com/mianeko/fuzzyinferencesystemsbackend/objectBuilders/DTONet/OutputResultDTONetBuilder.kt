package com.mianeko.fuzzyinferencesystemsbackend.objectBuilders.DTONet

import com.mianeko.fuzzyinferencesystemsbackend.DTONet.OutputResultDTONet

class OutputResultDTONetBuilder private constructor(
    private var variableName: String = defaultName,
    private var variableValue: Double = defaultValue
) {
    fun build() = OutputResultDTONet(
        name  = variableName,
        value = variableValue
    )

    fun withVariableName(variableName: String): OutputResultDTONetBuilder {
        this.variableName = variableName
        return this
    }

    fun withValue(variableValue: Double): OutputResultDTONetBuilder {
        this.variableValue = variableValue
        return this
    }

    companion object {
        val defaultName: String = "out1"
        val defaultValue: Double = 10.1

        fun nOutputResultDTONet() = OutputResultDTONetBuilder()
    }
}