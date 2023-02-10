package com.mianeko.fuzzyinferencesystemsbackend.DTONet

import com.mianeko.fuzzyinferencesystemsbackend.api.models.ConsequentNet
import com.mianeko.fuzzyinferencesystemsbackend.api.models.ConsequentTemplateNet
import com.mianeko.fuzzyinferencesystemsbackend.services.models.Consequent
import com.mianeko.fuzzyinferencesystemsbackend.services.models.ConsequentTemplate

data class ConsequentDTONet(
    val id: Int,
    val ruleId: Int,
    val text: String,
    val membershipFunction: PartialMembershipFunctionDTONet,
    val variable: PartialVariableDTONet?,
    val systemId: Int
) {
    fun toModelNet() = ConsequentNet(
        id = this.id,
        variable = this.variable
            ?.toModelNet(),
        text = this.text,
        membershipFunction = this.membershipFunction.toModelNet()
    )

    companion object {
        fun fromModel(consequent: Consequent) = ConsequentDTONet(
            id = consequent.id,
            ruleId = consequent.ruleId,
            text = consequent.toString(),
            membershipFunction = PartialMembershipFunctionDTONet.fromModel(consequent.membershipFunction, consequent.systemId),
            variable = consequent.variable?.let { PartialVariableDTONet.fromModel(it) },
            systemId = consequent.systemId
        )
    }
}


data class ConsequentTemplateDTONet(
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

    companion object {
        fun fromModelNet(
            consequentNet: ConsequentTemplateNet,
            systemId: Int,
            ruleId: Int,
            consequentId: Int?
        ) = ConsequentTemplateDTONet(
            id = consequentId,
            ruleId = ruleId,
            membershipFunctionId = consequentNet.membershipFunctionId,
            variableId = consequentNet.variableId,
            systemId = systemId
        )
    }
}