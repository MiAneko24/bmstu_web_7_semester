package com.mianeko.fuzzyinferencesystemsbackend.database.repositories.objectBuilders

import com.mianeko.fuzzyinferencesystemsbackend.services.models.InferenceSystem
import com.mianeko.fuzzyinferencesystemsbackend.services.models.Variable

class VariableBuilder private constructor(
    private var id: Int = defaultId,
    private var name: String = defaultName,
    private var minValue: Double = defaultMinValue,
    private var maxValue: Double = defaultMaxValue,
    private var value: Double? = defaultValue,
    private var system: InferenceSystem = defaultSystem,
) {
    fun build() =
        Variable(
            id = this.id,
            name = this.name,
            minValue = this.minValue,
            maxValue = this.maxValue,
            value = this.value,
            inferenceSystem = this.system
        )

    fun withId(id: Int): VariableBuilder {
        this.id = id
        return this
    }

    fun withName(name: String): VariableBuilder {
        this.name = name
        return this
    }

    fun withMinValue(minValue: Double): VariableBuilder {
        this.minValue = minValue
        return this
    }

    fun withMaxValue(maxValue: Double): VariableBuilder {
        this.maxValue = maxValue
        return this
    }

    fun withValue(value: Double?): VariableBuilder {
        this.value = value
        return this
    }

    fun withSystem(system: InferenceSystem): VariableBuilder {
        this.system = system
        return this
    }

    companion object {
        val defaultId: Int = 1
        val defaultName: String = "Variable"
        val defaultMinValue: Double = -10.1
        val defaultMaxValue: Double = 100.0
        val defaultValue: Double? = null
        val defaultSystem: InferenceSystem = SystemBuilder.nSystem().build()

        fun nVariable() =
            VariableBuilder()
    }
}