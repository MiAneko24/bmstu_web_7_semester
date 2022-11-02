package com.mianeko.fuzzyinferencesystemsbackend.DTO

import com.mianeko.fuzzyinferencesystemsbackend.services.models.MembershipFunctionTemplate
import com.mianeko.fuzzyinferencesystemsbackend.services.models.PartialMembershipFunction
import com.mianeko.fuzzyinferencesystemsbackend.services.models.enums.LinguisticHedgeType
import com.mianeko.fuzzyinferencesystemsbackend.services.models.enums.MembershipFunctionType


data class MembershipFunctionDTO(
    val id: Int,
    val term: String?,
    val functionType: MembershipFunctionType,
    val variable: PartialVariableDTO?,
    val parameter1: Double?,
    val parameter2: Double?,
    val parameter3: Double?,
    val parameter4: Double?,
    val value: Double?,
    val parent: PartialMembershipFunctionDTO?,
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
}

data class PartialMembershipFunctionDTO(
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
}