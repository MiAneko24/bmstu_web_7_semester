package com.mianeko.fuzzyinferencesystemsbackend.objectBuilders.DTONet

import com.mianeko.fuzzyinferencesystemsbackend.DTONet.PartialVariableDTONet
import com.mianeko.fuzzyinferencesystemsbackend.DTONet.VariableDTONet

class VariableDTONetBuilder private constructor(
    private var id: Int = defaultId,
    private var name: String = defaultName,
    private var minValue: Double = defaultMinValue,
    private var maxValue: Double = defaultMaxValue,
    private var value: Double? = defaultValue,
    private var systemId: Int = defaultSystemId,
) {
    fun build() =
        VariableDTONet(
            id = this.id,
            name = this.name,
            minValue = this.minValue,
            maxValue = this.maxValue,
            value = this.value,
            systemId = this.systemId
        )

    fun buildPartial() =
        PartialVariableDTONet(
            id = id,
            name = name,
            systemId = systemId
        )

    fun withId(id: Int): VariableDTONetBuilder {
        this.id = id
        return this
    }

    fun withName(name: String): VariableDTONetBuilder {
        this.name = name
        return this
    }

    fun withMinValue(minValue: Double): VariableDTONetBuilder {
        this.minValue = minValue
        return this
    }

    fun withMaxValue(maxValue: Double): VariableDTONetBuilder {
        this.maxValue = maxValue
        return this
    }

    fun withValue(value: Double?): VariableDTONetBuilder {
        this.value = value
        return this
    }

    fun withSystemId(system: Int): VariableDTONetBuilder {
        this.systemId = system
        return this
    }

    companion object {
        val defaultId: Int = 1
        val defaultName: String = "Variable"
        val defaultMinValue: Double = -10.1
        val defaultMaxValue: Double = 100.0
        val defaultValue: Double? = null
        val defaultSystemId: Int = 1

        fun nVariableDTONet() =
            VariableDTONetBuilder()
    }
}