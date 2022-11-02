package com.mianeko.fuzzyinferencesystemsbackend.services.models

import com.mianeko.fuzzyinferencesystemsbackend.DTO.AntecedentDTO
import com.mianeko.fuzzyinferencesystemsbackend.exceptions.AntecedentWithNoVariableException

data class Antecedent(
    val id: Int,
    val membershipFunction: MembershipFunction
) {
    fun toDTO() = AntecedentDTO(
        id = this.id,
        text = this.toString(),
        membershipFunctionId = this.membershipFunction.id,
        systemId = this.membershipFunction.variable?.id
            ?: throw AntecedentWithNoVariableException()
    )

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

data class AntecedentTemplateDTO(
    val id: Int?,
    val membershipFunctionId: Int,
    val systemId: Int
) {
    fun toModel(id: Int) =
        AntecedentTemplate(
            id = id,
            membershipFunctionId = membershipFunctionId,
            systemId = this.systemId
        )
}