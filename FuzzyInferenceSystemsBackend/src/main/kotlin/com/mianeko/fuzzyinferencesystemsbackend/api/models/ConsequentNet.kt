package com.mianeko.fuzzyinferencesystemsbackend.api.models

data class ConsequentNet(
    val id: Int,
    val variable: PartialVariableNet?,
    val text: String,
    val membershipFunction: PartialMembershipFunctionNet
)

data class ConsequentTemplateNet(
    val variableId: Int?,
    val membershipFunctionId: Int
)