package com.mianeko.fuzzyinferencesystemsbackend.api.models

data class AntecedentNet(
    val id: Int,
    val text: String,
    val membershipFunctionId: Int
)

data class AntecedentTemplateNet(
    val membershipFunctionId: Int
)