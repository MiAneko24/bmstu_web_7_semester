package com.mianeko.fuzzyinferencesystemsbackend.objectBuilders.DTONet

import com.mianeko.fuzzyinferencesystemsbackend.DTONet.VariableTemplateDTONet

class VariableTemplateDTONetBuilder private constructor(
    private var id: Int = defaultId,
    private var name: String = defaultName,
    private var minValue: Double = defaultMinValue,
    private var maxValue: Double = defaultMaxValue,
    private var value: Double? = defaultValue,
    private var systemId: Int = defaultSystemId,
) {
    fun build() =
        VariableTemplateDTONet(
            id = this.id,
            name = this.name,
            minValue = this.minValue,
            maxValue = this.maxValue,
            value = this.value,
            systemId = this.systemId
        )

    fun withId(id: Int): VariableTemplateDTONetBuilder {
        this.id = id
        return this
    }

    fun withName(name: String): VariableTemplateDTONetBuilder {
        this.name = name
        return this
    }

    fun withMinValue(minValue: Double): VariableTemplateDTONetBuilder {
        this.minValue = minValue
        return this
    }

    fun withMaxValue(maxValue: Double): VariableTemplateDTONetBuilder {
        this.maxValue = maxValue
        return this
    }

    fun withValue(value: Double?): VariableTemplateDTONetBuilder {
        this.value = value
        return this
    }

    fun withSystemId(systemId: Int): VariableTemplateDTONetBuilder {
        this.systemId = systemId
        return this
    }

    companion object {
        val defaultId: Int = 1
        val defaultName: String = "Variable"
        val defaultMinValue: Double = -10.1
        val defaultMaxValue: Double = 100.0
        val defaultValue: Double? = null
        val defaultSystemId: Int = 1

        fun nVariableTemplateDTONet() =
            VariableTemplateDTONetBuilder()
    }
}