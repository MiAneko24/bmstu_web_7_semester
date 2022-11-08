package com.mianeko.fuzzyinferencesystemsbackend.services.models

data class Antecedent(
    val id: Int,
    val membershipFunction: MembershipFunction
) {

    fun toTemplate(systemId: Int) = AntecedentTemplate(
        id = this.id,
        membershipFunctionId = this.membershipFunction.id,
        systemId = systemId
    )

    override fun toString(): String {
        return "${this.membershipFunction.variable?.name
            ?: "variable"} is ${this.membershipFunction.term
            ?: "term"}"
    }
}

data class AntecedentTemplate(
    val id: Int,
    val membershipFunctionId: Int,
    val systemId: Int
)