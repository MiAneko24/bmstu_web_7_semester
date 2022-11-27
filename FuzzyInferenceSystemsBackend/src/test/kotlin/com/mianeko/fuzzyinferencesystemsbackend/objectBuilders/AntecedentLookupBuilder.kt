package com.mianeko.fuzzyinferencesystemsbackend.objectBuilders

import com.mianeko.fuzzyinferencesystemsbackend.lookupEntities.AntecedentLookup

class AntecedentLookupBuilder private constructor(
    private var systemId: Int = defaultSystemId,
    private var ruleId: Int? = defaultRuleId,
    private var antecedentId: Int? = defaultAntecedentId
){
    fun build() = AntecedentLookup(
        systemId = this.systemId,
        ruleId = this.ruleId,
        antecedentId = this.antecedentId
    )

    fun withSystemId(systemId: Int): AntecedentLookupBuilder {
        this.systemId = systemId
        return this
    }

    fun withRuleId(ruleId: Int?): AntecedentLookupBuilder {
        this.ruleId = ruleId
        return this
    }

    fun withAntecedentId(antecedentId: Int?): AntecedentLookupBuilder {
        this.antecedentId = antecedentId
        return this
    }

    companion object {
        val defaultSystemId: Int = 1
        val defaultRuleId: Int? = null
        val defaultAntecedentId: Int? = null

        fun nAntecedentLookup() = AntecedentLookupBuilder()
    }
}