package com.mianeko.fuzzyinferencesystemsbackend.api.models

import com.mianeko.fuzzyinferencesystemsbackend.api.models.enums.LinguisticHedgeTypeNet
import com.mianeko.fuzzyinferencesystemsbackend.api.models.enums.MembershipFunctionTypeNet

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
)


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
)
