package com.mianeko.fuzzyinferencesystemsbackend.objectBuilders

import com.mianeko.fuzzyinferencesystemsbackend.lookupEntities.ConsequentLookup

class ConsequentLookupBuilder private constructor(
    private var systemId: Int = defaultSystemId,
    private var ruleId: Int = defaultRuleId,
    private var consequentId: Int? = defaultConsequentId
){
    fun build() = ConsequentLookup(
        systemId = this.systemId,
        ruleId = this.ruleId,
        consequentId = this.consequentId
    )

    fun withSystemId(systemId: Int): ConsequentLookupBuilder {
        this.systemId = systemId
        return this
    }

    fun withRuleId(ruleId: Int): ConsequentLookupBuilder {
        this.ruleId = ruleId
        return this
    }

    fun withConsequentId(consequentId: Int?): ConsequentLookupBuilder {
        this.consequentId = consequentId
        return this
    }

    companion object {
        val defaultSystemId: Int = 1
        val defaultRuleId: Int = 1
        val defaultConsequentId: Int? = null

        fun nConsequentLookup() = ConsequentLookupBuilder()
    }
}