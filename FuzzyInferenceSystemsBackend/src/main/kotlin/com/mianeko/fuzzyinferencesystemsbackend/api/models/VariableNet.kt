package com.mianeko.fuzzyinferencesystemsbackend.api.models

data class VariableNet(
    val name: String,
    val minValue: Double,
    val maxValue: Double,
    val value: Double?
)

data class VariableTemplateNet(
    val name: String,
    val minValue: Double,
    val maxValue: Double,
    val value: Double?
)