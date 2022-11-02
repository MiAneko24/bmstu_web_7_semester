package com.mianeko.fuzzyinferencesystemsbackend.api.models

import com.mianeko.fuzzyinferencesystemsbackend.DTO.ConsequentDTO
import com.mianeko.fuzzyinferencesystemsbackend.services.models.ConsequentTemplateDTO

data class ConsequentNet(
    val id: Int,
    val variable: PartialVariableNet?,
    val text: String,
    val membershipFunction: PartialMembershipFunctionNet
) {
    companion object {
        fun fromDTO(consequent: ConsequentDTO) = ConsequentNet(
            id = consequent.id,
            variable = consequent.variable
                ?.let { PartialVariableNet.fromPartialDTO(it) },
            text = consequent.text,
            membershipFunction = PartialMembershipFunctionNet
                .fromPartialDTO(consequent.membershipFunction)
        )
    }
}

data class ConsequentTemplateNet(
    val variableId: Int?,
    val membershipFunctionId: Int
) {
    fun toDTO(systemId: Int, ruleId: Int, consequentId: Int?) = ConsequentTemplateDTO(
        id = consequentId,
        ruleId = ruleId,
        membershipFunctionId = this.membershipFunctionId,
        variableId = this.variableId,
        systemId = systemId
    )
}