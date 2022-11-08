package com.mianeko.fuzzyinferencesystemsbackend.services.models

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
)

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
