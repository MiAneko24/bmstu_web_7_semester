package com.mianeko.fuzzyinferencesystemsbackend.DTO

import com.mianeko.fuzzyinferencesystemsbackend.services.models.SystemTemplate
import com.mianeko.fuzzyinferencesystemsbackend.services.models.enums.FuzzySystemType
import com.mianeko.fuzzyinferencesystemsbackend.services.models.enums.SpecializationType

data class SystemDTO(
    val id: Int,
    val name: String,
    val type: FuzzySystemType,
    val specializationType: SpecializationType
) {
    fun toModel() = SystemTemplate(
        id = this.id,
        name = this.name,
        type = this.type,
        specializationType = this.specializationType
    )
}

data class PartialSystemDTO(
    val id: Int,
    val name: String
)
