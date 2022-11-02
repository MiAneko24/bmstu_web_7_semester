package com.mianeko.fuzzyinferencesystemsbackend.services.models

import com.mianeko.fuzzyinferencesystemsbackend.DTO.MembershipFunctionDTO
import com.mianeko.fuzzyinferencesystemsbackend.DTO.PartialMembershipFunctionDTO
import com.mianeko.fuzzyinferencesystemsbackend.services.models.enums.LinguisticHedgeType
import com.mianeko.fuzzyinferencesystemsbackend.services.models.enums.MembershipFunctionType

data class MembershipFunction(
    val id: Int,

    val term: String?,
    val functionType: MembershipFunctionType,

    val variable: Variable?,

    val parameter1: Double?,
    val parameter2: Double?,
    val parameter3: Double?,
    val parameter4: Double?,
    val value: Double?,

    val parent: MembershipFunction?,

    val hedgeType: LinguisticHedgeType
) {
    fun fromPartialModel(partialMembershipFunction: PartialMembershipFunction) =
        copy(term = partialMembershipFunction.term)

    fun toDTO(systemId: Int) = MembershipFunctionDTO(
        id = this.id,
        term = this.term,
        functionType = this.functionType,
        variable = this.variable?.toPartialDTO(),
        parameter1 = this.parameter1,
        parameter2 = this.parameter2,
        parameter3 = this.parameter3,
        parameter4 = this.parameter4,
        value = this.value,
        parent = this.parent?.toPartialDTO(systemId),
        hedgeType = this.hedgeType,
        systemId = systemId
    )

    fun toPartialDTO(systemId: Int) = PartialMembershipFunctionDTO(
        id = this.id,
        term = this.term,
        systemId = systemId,
        variableId = this.variable?.id
    )
}

data class MembershipFunctionTemplate(
    val id: Int,
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
)

data class MembershipFunctionTemplateDTO(
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
}
