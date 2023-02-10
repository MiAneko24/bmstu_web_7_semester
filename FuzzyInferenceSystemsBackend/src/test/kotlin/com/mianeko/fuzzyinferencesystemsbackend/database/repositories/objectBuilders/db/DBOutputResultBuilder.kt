package com.mianeko.fuzzyinferencesystemsbackend.database.repositories.objectBuilders.db

import com.mianeko.fuzzyinferencesystemsbackend.database.entities.DBOutputResult

class DBOutputResultBuilder private constructor(
    private var variableName: String = defaultName,
    private var variableValue: Double = defaultValue
) {
    fun build() = DBOutputResult(
        variableName = variableName,
        value = variableValue
    )

    fun withVariableName(variableName: String): DBOutputResultBuilder {
        this.variableName = variableName
        return this
    }

    fun withValue(variableValue: Double): DBOutputResultBuilder {
        this.variableValue = variableValue
        return this
    }

    companion object {
        val defaultName: String = "out1"
        val defaultValue: Double = 10.1

        fun nDBOutputResult() = DBOutputResultBuilder()
    }
}