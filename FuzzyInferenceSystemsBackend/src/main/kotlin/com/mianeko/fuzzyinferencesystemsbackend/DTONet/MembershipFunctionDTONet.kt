package com.mianeko.fuzzyinferencesystemsbackend.DTONet

import com.mianeko.fuzzyinferencesystemsbackend.api.models.MembershipFunctionNet
import com.mianeko.fuzzyinferencesystemsbackend.api.models.MembershipFunctionTemplateNet
import com.mianeko.fuzzyinferencesystemsbackend.api.models.PartialMembershipFunctionNet
import com.mianeko.fuzzyinferencesystemsbackend.api.models.enums.LinguisticHedgeTypeNet
import com.mianeko.fuzzyinferencesystemsbackend.api.models.enums.MembershipFunctionTypeNet
import com.mianeko.fuzzyinferencesystemsbackend.services.models.MembershipFunction
import com.mianeko.fuzzyinferencesystemsbackend.services.models.MembershipFunctionTemplate
import com.mianeko.fuzzyinferencesystemsbackend.services.models.PartialMembershipFunction
import com.mianeko.fuzzyinferencesystemsbackend.services.models.enums.LinguisticHedgeType
import com.mianeko.fuzzyinferencesystemsbackend.services.models.enums.MembershipFunctionType


data class MembershipFunctionDTONet(
    val id: Int,
    val term: String?,
    val functionType: MembershipFunctionType,
    val variable: PartialVariableDTONet?,
    val parameter1: Double?,
    val parameter2: Double?,
    val parameter3: Double?,
    val parameter4: Double?,
    val value: Double?,
    val parent: PartialMembershipFunctionDTONet?,
    val hedgeType: LinguisticHedgeType,
    val systemId: Int
) {
    fun toModel() = MembershipFunctionTemplate(
        id = this.id,
        term = this.term,
        functionType = this.functionType,
        variableId = this.variable?.id,
        parameter1 = this.parameter1,
        parameter2 = this.parameter2,
        parameter3 = this.parameter3,
        parameter4 = this.parameter4,
        value = this.value,
        parentId = this.parent?.id,
        hedgeType = this.hedgeType,
        systemId = this.systemId
    )

    fun toModelNet() = MembershipFunctionNet(
        id = this.id,
        term = this.term,
        membershipFunctionType = MembershipFunctionTypeNet.fromMembershipFunctionType(
            this.functionType
        ),
        variable = this.variable
            ?.toModelNet(),
        parameter1 = this.parameter1,
        parameter2 = this.parameter2,
        parameter3 = this.parameter3,
        parameter4 = this.parameter4,
        value = this.value,
        parent = this.parent
            ?.toModelNet(),
        linguisticHedgeType = LinguisticHedgeTypeNet.fromLinguisticHedgeType(this.hedgeType)
    )

    fun toPartialModelNet() = PartialMembershipFunctionNet(
        id = this.id,
        term = this.term,
        variableId = this.variable?.id
    )

    companion object {
        fun fromModel(membershipFunction: MembershipFunction, systemId: Int) = MembershipFunctionDTONet(
            id = membershipFunction.id,
            term = membershipFunction.term,
            functionType = membershipFunction.functionType,
            variable = membershipFunction.variable?.let { PartialVariableDTONet.fromModel(it) },
            parameter1 = membershipFunction.parameter1,
            parameter2 = membershipFunction.parameter2,
            parameter3 = membershipFunction.parameter3,
            parameter4 = membershipFunction.parameter4,
            value = membershipFunction.value,
            parent = membershipFunction.parent?.let{ PartialMembershipFunctionDTONet.fromModel(it, systemId) },
            hedgeType = membershipFunction.hedgeType,
            systemId = systemId
        )
    }
}

data class PartialMembershipFunctionDTONet(
    val id: Int,
    val term: String?,
    val systemId: Int,
    val variableId: Int?
) {
    fun toModel() =
        PartialMembershipFunction(
            id = id,
            term = term,
            variableId = variableId
        )

    fun toModelNet() = PartialMembershipFunctionNet(
        id = this.id,
        term = this.term,
        variableId = this.variableId
    )

    companion object {
        fun fromModel(membershipFunction: MembershipFunction, systemId: Int) = PartialMembershipFunctionDTONet(
            id = membershipFunction.id,
            term = membershipFunction.term,
            variableId = membershipFunction.variable?.id,
            systemId = systemId
        )
    }
}


data class MembershipFunctionTemplateDTONet(
    val id: Int?,
    val term: String?,
    val functionType: MembershipFunctionType,
    val variableId: Int?,
    val parameter1: Double?,
    val parameter2: Double?,
    val parameter3: Double?,
    val parameter4: Double?,
    val value: Double?,
    val parentId: Int?,
    val hedgeType: LinguisticHedgeType,
    val systemId: Int
) {
    fun toModel(id: Int) = MembershipFunctionTemplate(
        id = id,
        term = this.term,
        functionType = this.functionType,
        variableId = this.variableId,
        parameter1 = this.parameter1,
        parameter2 = this.parameter2,
        parameter3 = this.parameter3,
        parameter4 = this.parameter4,
        value = this.value,
        parentId = this.parentId,
        hedgeType = this.hedgeType,
        systemId = this.systemId
    )

    companion object {
        fun fromModelNet(membershipFunctionNet: MembershipFunctionTemplateNet, systemId: Int, membershipFunctionId: Int?) =
            MembershipFunctionTemplateDTONet(
                id = membershipFunctionId,
                term = membershipFunctionNet.term,
                functionType = membershipFunctionNet.membershipFunctionType.toMembershipFunctionType(),
                variableId = membershipFunctionNet.variableId,
                parameter1 = membershipFunctionNet.parameter1,
                parameter2 = membershipFunctionNet.parameter2,
                parameter3 = membershipFunctionNet.parameter3,
                parameter4 = membershipFunctionNet.parameter4,
                value = membershipFunctionNet.value,
                parentId = membershipFunctionNet.parentId,
                hedgeType = membershipFunctionNet.linguisticHedgeType.toLinguisticHedgeType(),
                systemId = systemId
            )
    }
}
