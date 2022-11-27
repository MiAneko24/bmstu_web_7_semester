package com.mianeko.fuzzyinferencesystemsbackend.objectBuilders.DTODb

import com.mianeko.fuzzyinferencesystemsbackend.DTODb.SystemDTODb
import com.mianeko.fuzzyinferencesystemsbackend.DTODb.VariableDTODb

class VariableDTODbBuilder private constructor(
    private var id: Int = defaultId,
    private var name: String = defaultName,
    private var minValue: Double = defaultMinValue,
    private var maxValue: Double = defaultMaxValue,
    private var value: Double? = defaultValue,
    private var system: SystemDTODb = defaultSystem,
) {
    fun build() =
        VariableDTODb(
            id = this.id,
            name = this.name,
            minValue = this.minValue,
            maxValue = this.maxValue,
            value = this.value,
            system = this.system
        )

    fun withId(id: Int): VariableDTODbBuilder {
        this.id = id
        return this
    }

    fun withName(name: String): VariableDTODbBuilder {
        this.name = name
        return this
    }

    fun withMinValue(minValue: Double): VariableDTODbBuilder {
        this.minValue = minValue
        return this
    }

    fun withMaxValue(maxValue: Double): VariableDTODbBuilder {
        this.maxValue = maxValue
        return this
    }

    fun withValue(value: Double?): VariableDTODbBuilder {
        this.value = value
        return this
    }

    fun withSystem(system: SystemDTODb): VariableDTODbBuilder {
        this.system = system
        return this
    }

    companion object {
        val defaultId: Int = 1
        val defaultName: String = "Variable"
        val defaultMinValue: Double = -10.1
        val defaultMaxValue: Double = 100.0
        val defaultValue: Double? = null
        val defaultSystem: SystemDTODb = SystemDTODbBuilder.nSystemDTODb().build()

        fun nVariableDTODb() =
            VariableDTODbBuilder()
    }
}