package com.mianeko.fuzzyinferencesystemsbackend.objectBuilders

import com.mianeko.fuzzyinferencesystemsbackend.lookupEntities.VariableLookup

class VariableLookupBuilder  private constructor(
    private var systemId: Int = defaultSystemId,
    private var variableId: Int? = defaultVariableId
){
    fun build() = VariableLookup(
        systemId = this.systemId,
        variableId = this.variableId
    )

    fun withSystemId(systemId: Int): VariableLookupBuilder {
        this.systemId = systemId
        return this
    }

    fun withVariableId(variableId: Int?): VariableLookupBuilder {
        this.variableId = variableId
        return this
    }

    companion object {
        val defaultSystemId: Int = 1
        val defaultVariableId: Int? = null

        fun nVariableLookup() = VariableLookupBuilder()
    }
}