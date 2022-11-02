package com.mianeko.fuzzyinferencesystemsbackend.services.models

import com.mianeko.fuzzyinferencesystemsbackend.DTO.PartialSystemDTO
import com.mianeko.fuzzyinferencesystemsbackend.DTO.SystemDTO
import com.mianeko.fuzzyinferencesystemsbackend.services.models.enums.FuzzySystemType
import com.mianeko.fuzzyinferencesystemsbackend.services.models.enums.SpecializationType

data class InferenceSystem(
    val id: Int,
    val name: String,
    val type: FuzzySystemType,
    val specializationType: SpecializationType
) {
    fun toDTO() = SystemDTO(
        id = this.id,
        name = this.name,
        type = this.type,
        specializationType = this.specializationType
    )

    fun toPartialDTO() = PartialSystemDTO(
        id = this.id,
        name = this.name
    )
}

data class SystemTemplate(
    val id: Int,
    val name: String,
    val type: FuzzySystemType,
    val specializationType: SpecializationType
)

data class SystemTemplateDTO(
    val id: Int?,
    val name: String,
    val type: FuzzySystemType,
    val specializationType: SpecializationType
) {
    fun toModel(id: Int) = SystemTemplate(
        id = id,
        name = this.name,
        type = this.type,
        specializationType = this.specializationType
    )
}
