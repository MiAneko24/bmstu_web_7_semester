package com.mianeko.fuzzyinferencesystemsbackend.DTO

import com.mianeko.fuzzyinferencesystemsbackend.services.models.*

data class ConsequentDTO(
    val id: Int,
    val ruleId: Int,
    val text: String,
    val membershipFunction: PartialMembershipFunctionDTO,
    val variable: PartialVariableDTO?,
    val systemId: Int
) {
    fun toModel() = ConsequentTemplate(
        id = this.id,
        ruleId = this.ruleId,
        membershipFunctionId = this.membershipFunction.id,
        variableId = this.variable?.id,
        systemId = this.systemId
    )
}