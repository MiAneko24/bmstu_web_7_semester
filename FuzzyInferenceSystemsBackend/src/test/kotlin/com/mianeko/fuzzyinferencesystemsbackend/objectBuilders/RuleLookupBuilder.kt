package com.mianeko.fuzzyinferencesystemsbackend.objectBuilders

import com.mianeko.fuzzyinferencesystemsbackend.lookupEntities.RuleLookup

class RuleLookupBuilder private constructor(
    private var systemId: Int = defaultSystemId,
    private var ruleId: Int? = defaultRuleId
){
    fun build() = RuleLookup(
        systemId = this.systemId,
        ruleId = this.ruleId
    )

    fun withSystemId(systemId: Int): RuleLookupBuilder {
        this.systemId = systemId
        return this
    }

    fun withRuleId(ruleId: Int?): RuleLookupBuilder {
        this.ruleId = ruleId
        return this
    }

    companion object {
        val defaultSystemId: Int = 1
        val defaultRuleId: Int? = null

        fun nRuleLookup() = RuleLookupBuilder()
    }
}