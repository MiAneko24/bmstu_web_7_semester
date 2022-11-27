package com.mianeko.fuzzyinferencesystemsbackend.database.repositories.objectBuilders.db

import com.mianeko.fuzzyinferencesystemsbackend.database.entities.DBSystem
import com.mianeko.fuzzyinferencesystemsbackend.database.entities.DBVariable

class DBVariableBuilder private constructor(
    private var id: Int = defaultId,
    private var name: String = defaultName,
    private var minValue: Double = defaultMinValue,
    private var maxValue: Double = defaultMaxValue,
    private var value: Double? = defaultValue,
    private var system: DBSystem = defaultSystem,
) {
    fun build() =
        DBVariable(
            id = this.id,
            name = this.name,
            minValue = this.minValue,
            maxValue = this.maxValue,
            value = this.value,
            system = this.system
        )

    fun withId(id: Int): DBVariableBuilder {
        this.id = id
        return this
    }

    fun withName(name: String): DBVariableBuilder {
        this.name = name
        return this
    }

    fun withMinValue(minValue: Double): DBVariableBuilder {
        this.minValue = minValue
        return this
    }

    fun withMaxValue(maxValue: Double): DBVariableBuilder {
        this.maxValue = maxValue
        return this
    }

    fun withValue(value: Double?): DBVariableBuilder {
        this.value = value
        return this
    }

    fun withSystem(dbSystem: DBSystem): DBVariableBuilder {
        this.system = dbSystem
        return this
    }

    companion object {
        val defaultId: Int = 1
        val defaultName: String = "Variable"
        val defaultMinValue: Double = -10.1
        val defaultMaxValue: Double = 100.0
        val defaultValue: Double? = null
        val defaultSystem: DBSystem = DBSystemBuilder.nDBSystem().build()

        fun nDBVariable() =
            DBVariableBuilder()
    }
}