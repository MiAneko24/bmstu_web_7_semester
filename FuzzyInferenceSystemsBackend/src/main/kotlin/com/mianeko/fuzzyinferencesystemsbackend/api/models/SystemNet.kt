package com.mianeko.fuzzyinferencesystemsbackend.api.models

import com.mianeko.fuzzyinferencesystemsbackend.api.models.enums.FuzzySystemTypeNet
import com.mianeko.fuzzyinferencesystemsbackend.api.models.enums.SpecializationTypeNet

data class SystemNet(
    val name: String,
    val type: FuzzySystemTypeNet,
    val specializationType: SpecializationTypeNet
)

data class SystemTemplateNet(
    val name: String,
    val type: FuzzySystemTypeNet,
    val specializationType: SpecializationTypeNet
)