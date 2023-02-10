package com.mianeko.fuzzyinferencesystemsbackend.objectBuilders.DTONet

import com.mianeko.fuzzyinferencesystemsbackend.DTONet.ConsequentTemplateDTONet

class ConsequentTemplateDTONetBuilder private constructor(
    private var id: Int = defaultId,
    private var membershipFunctionId: Int = defaultMembershipFunction,
    private var ruleId: Int = defaultRuleId,
    private var variableId: Int? = defaultVariable,
    private var systemId: Int = defaultSystemId
){
    fun build() =
        ConsequentTemplateDTONet(
            id = this.id,
            membershipFunctionId = this.membershipFunctionId,
            ruleId = this.ruleId,
            variableId = this.variableId,
            systemId = this.systemId
        )

    fun withId(id: Int): ConsequentTemplateDTONetBuilder {
        this.id = id
        return this
    }

    fun withMembershipFunction(membershipFunctionId: Int): ConsequentTemplateDTONetBuilder {
        this.membershipFunctionId = membershipFunctionId
        return this
    }

    fun withRuleId(ruleId: Int): ConsequentTemplateDTONetBuilder {
        this.ruleId = ruleId
        return this
    }

    fun withVariable(variableId: Int?): ConsequentTemplateDTONetBuilder {
        this.variableId = variableId
        return this
    }

    companion object {
        val defaultId: Int = 1
        val defaultMembershipFunction: Int =
            MembershipFunctionDTONetBuilder.nMembershipFunctionDTONet()
                .build().id
        val defaultRuleId: Int = 1
        val defaultVariable: Int? = null
        val defaultSystemId = 1

        fun nConsequentTemplateDTONet() = ConsequentTemplateDTONetBuilder()
    }
}