package com.mianeko.fuzzyinferencesystemsbackend.DTONet

import com.mianeko.fuzzyinferencesystemsbackend.api.models.AntecedentNet
import com.mianeko.fuzzyinferencesystemsbackend.api.models.AntecedentTemplateNet
import com.mianeko.fuzzyinferencesystemsbackend.exceptions.AntecedentWithNoVariableException
import com.mianeko.fuzzyinferencesystemsbackend.services.models.Antecedent
import com.mianeko.fuzzyinferencesystemsbackend.services.models.AntecedentTemplate


data class AntecedentDTONet(
    val id: Int,
    val membershipFunctionId: Int,
    val text: String,
    val systemId: Int
) {
    fun toModelNet() = AntecedentNet(
        id = this.id,
        text = this.text,
        membershipFunctionId = this.membershipFunctionId
    )

    companion object {
        fun fromModel(antecedent: Antecedent) = AntecedentDTONet(
            id = antecedent.id,
            text = antecedent.toString(),
            membershipFunctionId = antecedent.membershipFunction.id,
            systemId = antecedent.membershipFunction.variable?.id
                ?: throw AntecedentWithNoVariableException()
        )
    }
}

data class AntecedentTemplateDTONet(
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

    companion object {
        fun fromModelNet(
            antecedentTemplateNet: AntecedentTemplateNet,
            systemId: Int,
            antecedentId: Int?
        ) = AntecedentTemplateDTONet(
            id = antecedentId,
            membershipFunctionId = antecedentTemplateNet.membershipFunctionId,
            systemId = systemId
        )
    }
}