package com.mianeko.fuzzyinferencesystemsbackend.services.models

data class Consequent(
    val id: Int,
    val ruleId: Int,
    val membershipFunction: MembershipFunction,
    val variable: Variable?,
    val systemId: Int
) {
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
