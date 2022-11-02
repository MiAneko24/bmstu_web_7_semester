package com.mianeko.fuzzyinferencesystemsbackend.api.models

import com.mianeko.fuzzyinferencesystemsbackend.DTO.MembershipFunctionDTO
import com.mianeko.fuzzyinferencesystemsbackend.api.models.enums.LinguisticHedgeTypeNet
import com.mianeko.fuzzyinferencesystemsbackend.api.models.enums.MembershipFunctionTypeNet
import com.mianeko.fuzzyinferencesystemsbackend.services.models.MembershipFunctionTemplateDTO

data class MembershipFunctionNet(
    val id: Int,
    val term: String?,
    val membershipFunctionType: MembershipFunctionTypeNet,
    val variable: PartialVariableNet?,
    val parameter1: Double?,
    val parameter2: Double?,
    val parameter3: Double?,
    val parameter4: Double?,
    val value: Double?,
    val parent: PartialMembershipFunctionNet?,
    val linguisticHedgeType: LinguisticHedgeTypeNet
) {
    fun toDTO(systemId: Int) =
        MembershipFunctionDTO(
            id = this.id,
            term = this.term,
            functionType = this.membershipFunctionType.toMembershipFunctionType(),
            variable = this.variable?.toDTO(systemId),
            parameter1 = this.parameter1,
            parameter2 = this.parameter2,
            parameter3 = this.parameter3,
            parameter4 = this.parameter4,
            value = this.value,
            parent = this.parent?.toDTO(systemId),
            hedgeType = this.linguisticHedgeType.toLinguisticHedgeType(),
            systemId = systemId
        )

    companion object {
        fun fromDTO(membershipFunction: MembershipFunctionDTO) =
            MembershipFunctionNet(
                id = membershipFunction.id,
                term = membershipFunction.term,
                membershipFunctionType = MembershipFunctionTypeNet.fromMembershipFunctionType(
                    membershipFunction.functionType
                ),
                variable = membershipFunction.variable
                    ?.let { PartialVariableNet.fromPartialDTO(it) },
                parameter1 = membershipFunction.parameter1,
                parameter2 = membershipFunction.parameter2,
                parameter3 = membershipFunction.parameter3,
                parameter4 = membershipFunction.parameter4,
                value = membershipFunction.value,
                parent = membershipFunction.parent
                    ?.let { PartialMembershipFunctionNet.fromPartialDTO(it) },
                linguisticHedgeType = LinguisticHedgeTypeNet.fromLinguisticHedgeType(membershipFunction.hedgeType)
            )
    }
}


data class MembershipFunctionTemplateNet(
    val term: String?,
    val membershipFunctionType: MembershipFunctionTypeNet,
    val variableId: Int?,
    val parameter1: Double?,
    val parameter2: Double?,
    val parameter3: Double?,
    val parameter4: Double?,
    val value: Double?,
    val parentId: Int?,
    val linguisticHedgeType: LinguisticHedgeTypeNet
) {
    fun toDTO(systemId: Int, membershipFunctionId: Int?) =
        MembershipFunctionTemplateDTO(
            id = membershipFunctionId,
            term = this.term,
            functionType = this.membershipFunctionType.toMembershipFunctionType(),
            variableId = this.variableId,
            parameter1 = this.parameter1,
            parameter2 = this.parameter2,
            parameter3 = this.parameter3,
            parameter4 = this.parameter4,
            value = this.value,
            parentId = this.parentId,
            hedgeType = this.linguisticHedgeType.toLinguisticHedgeType(),
            systemId = systemId
        )
}
