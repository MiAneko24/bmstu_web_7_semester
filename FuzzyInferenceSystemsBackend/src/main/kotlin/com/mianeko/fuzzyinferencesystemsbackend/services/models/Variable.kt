package com.mianeko.fuzzyinferencesystemsbackend.services.models

data class Variable(
    val id: Int,
    val name: String,
    val minValue: Double,
    val maxValue: Double,
    val value: Double?,
    val inferenceSystem: InferenceSystem
) {
    fun toTemplate() = VariableTemplate(
        id = this.id,
        name = this.name,
        minValue = this.minValue,
        maxValue = this.maxValue,
        value = this.value,
        systemId = inferenceSystem.id
    )
}

data class VariableTemplate(
    val id: Int,
    val name: String,
    val minValue: Double,
    val maxValue: Double,
    val value: Double?,
    val systemId: Int
)
