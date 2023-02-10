package com.mianeko.fuzzyinferencesystemsbackend.objectBuilders.DTONet

import com.mianeko.fuzzyinferencesystemsbackend.DTONet.ConsequentDTONet
import com.mianeko.fuzzyinferencesystemsbackend.DTONet.PartialMembershipFunctionDTONet
import com.mianeko.fuzzyinferencesystemsbackend.DTONet.PartialVariableDTONet

class ConsequentDTONetBuilder private constructor(
    private var id: Int = defaultId,
    private var ruleId: Int = defaultRuleId,
    private var text: String = defaultText,
    private var membershipFunction: PartialMembershipFunctionDTONet = defaultMembershipFunction,
    private var variable: PartialVariableDTONet? = defaultVariable,
    private var systemId: Int = defaultSystemId
){
    fun build() =
        ConsequentDTONet(
            id = this.id,
            ruleId = this.ruleId,
            text = text,
            membershipFunction = this.membershipFunction,
            variable = this.variable,
            systemId = this.systemId
        )

    fun withId(id: Int): ConsequentDTONetBuilder {
        this.id = id
        return this
    }

    fun withMembershipFunction(membershipFunction: PartialMembershipFunctionDTONet): ConsequentDTONetBuilder {
        this.membershipFunction = membershipFunction
        return this
    }

    fun withRuleId(ruleId: Int): ConsequentDTONetBuilder {
        this.ruleId = ruleId
        return this
    }

    fun withVariable(variable: PartialVariableDTONet?): ConsequentDTONetBuilder {
        this.variable = variable
        return this
    }

    companion object {
        val defaultId: Int = 1
        val defaultText: String = "Variable is Term"
        val defaultMembershipFunction: PartialMembershipFunctionDTONet =
            MembershipFunctionDTONetBuilder.nMembershipFunctionDTONet()
                .buildPartial()
        val defaultRuleId: Int = 1
        val defaultVariable: PartialVariableDTONet? = null
        val defaultSystemId = 1

        fun nConsequentDTONet() = ConsequentDTONetBuilder()
    }
}