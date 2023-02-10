package com.mianeko.fuzzyinferencesystemsbackend.objectBuilders.DTODb

import com.mianeko.fuzzyinferencesystemsbackend.DTODb.ConsequentTemplateDTODb

class ConsequentTemplateDTODbBuilder private constructor(
    private var id: Int = defaultId,
    private var membershipFunctionId: Int = defaultMembershipFunction,
    private var ruleId: Int = defaultRuleId,
    private var variableId: Int? = defaultVariable,
    private var systemId: Int = defaultSystemId
){
    fun build() =
        ConsequentTemplateDTODb(
            id = this.id,
            membershipFunctionId = this.membershipFunctionId,
            ruleId = this.ruleId,
            variableId = this.variableId,
            systemId = this.systemId
        )

    fun withId(id: Int): ConsequentTemplateDTODbBuilder {
        this.id = id
        return this
    }

    fun withMembershipFunction(membershipFunctionId: Int): ConsequentTemplateDTODbBuilder {
        this.membershipFunctionId = membershipFunctionId
        return this
    }

    fun withRuleId(ruleId: Int): ConsequentTemplateDTODbBuilder {
        this.ruleId = ruleId
        return this
    }

    fun withVariable(variableId: Int?): ConsequentTemplateDTODbBuilder {
        this.variableId = variableId
        return this
    }

    companion object {
        val defaultId: Int = 1
        val defaultMembershipFunction: Int =
            MembershipFunctionDTODbBuilder.nMembershipFunctionDTODb()
                .build().id
        val defaultRuleId: Int = 1
        val defaultVariable: Int? = null
        val defaultSystemId = 1

        fun nConsequentTemplateDTODb() = ConsequentTemplateDTODbBuilder()
    }
}