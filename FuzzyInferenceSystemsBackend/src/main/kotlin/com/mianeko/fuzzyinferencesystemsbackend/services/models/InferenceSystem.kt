package com.mianeko.fuzzyinferencesystemsbackend.services.models

import com.mianeko.fuzzyinferencesystemsbackend.services.models.enums.FuzzySystemType
import com.mianeko.fuzzyinferencesystemsbackend.services.models.enums.SpecializationType

data class InferenceSystem(
    val id: Int,
    val name: String,
    val type: FuzzySystemType,
    val specializationType: SpecializationType
)

data class SystemTemplate(
    val id: Int,
    val name: String,
    val type: FuzzySystemType,
    val specializationType: SpecializationType
)
