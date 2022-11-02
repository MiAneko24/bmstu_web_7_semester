package com.mianeko.fuzzyinferencesystemsbackend.api.models

import com.mianeko.fuzzyinferencesystemsbackend.DTO.SystemDTO
import com.mianeko.fuzzyinferencesystemsbackend.api.models.enums.FuzzySystemTypeNet
import com.mianeko.fuzzyinferencesystemsbackend.api.models.enums.SpecializationTypeNet
import com.mianeko.fuzzyinferencesystemsbackend.services.models.SystemTemplateDTO

data class SystemNet(
    val name: String,
    val type: FuzzySystemTypeNet,
    val specializationType: SpecializationTypeNet
) {
    fun toDTO(systemId: Int) = SystemDTO(
        id = systemId,
        name = name,
        type = type.toFuzzySystemType(),
        specializationType = specializationType.toSpecializationType()
    )

    companion object {
        fun fromDTO(system: SystemDTO) = SystemNet(
            name = system.name,
            type = FuzzySystemTypeNet.fromFuzzySystemType(system.type),
            specializationType = SpecializationTypeNet.fromSpecializationType(system.specializationType)
        )
    }
}

data class SystemTemplateNet(
    val name: String,
    val type: FuzzySystemTypeNet,
    val specializationType: SpecializationTypeNet
) {
    fun toDTO(id: Int?) = SystemTemplateDTO(
        id = id,
        name = name,
        type = type.toFuzzySystemType(),
        specializationType = specializationType.toSpecializationType()
    )
}