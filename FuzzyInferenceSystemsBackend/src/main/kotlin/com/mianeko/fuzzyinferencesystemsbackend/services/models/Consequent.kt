package com.mianeko.fuzzyinferencesystemsbackend.services.models

import com.mianeko.fuzzyinferencesystemsbackend.DTO.ConsequentDTO

data class Consequent(
    val id: Int,
    val ruleId: Int,
    val membershipFunction: MembershipFunction,
    val variable: Variable?,
    val systemId: Int
) {
    fun toDTO() = ConsequentDTO(
        id = this.id,
        ruleId = this.ruleId,
        text = this.toString(),
        membershipFunction = this.membershipFunction.toPartialDTO(systemId),
        variable = this.variable?.toPartialDTO(),
        systemId = systemId
    )

    fun toTemplate() = ConsequentTemplate(
        id = this.id,
        ruleId = this.ruleId,
        membershipFunctionId = this.membershipFunction.id,
        variableId = this.variable?.id,
        systemId = this.systemId
    )

    override fun toString(): String {
        return if (variable != null)
            "${variable.name} = ${membershipFunction.parameter1} ${membershipFunction.variable?.name}"
        else
            "${membershipFunction.variable?.name} is ${membershipFunction.term}"
    }
}

data class ConsequentTemplate(
    val id: Int,
    val ruleId: Int,
    val membershipFunctionId: Int,
    val variableId: Int?,
    val systemId: Int
)

data class ConsequentTemplateDTO(
    val id: Int?,
    val ruleId: Int,
    val membershipFunctionId: Int,
    val variableId: Int?,
    val systemId: Int
) {
    fun toModel(id: Int) = ConsequentTemplate(
        id = id,
        ruleId = this.ruleId,
        membershipFunctionId = this.membershipFunctionId,
        variableId = this.variableId,
        systemId = this.systemId
    )
}