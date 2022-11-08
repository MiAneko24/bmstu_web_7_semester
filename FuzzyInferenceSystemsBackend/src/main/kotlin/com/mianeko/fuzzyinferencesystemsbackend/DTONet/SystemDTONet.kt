package com.mianeko.fuzzyinferencesystemsbackend.DTONet

import com.mianeko.fuzzyinferencesystemsbackend.api.models.PartialSystemNet
import com.mianeko.fuzzyinferencesystemsbackend.api.models.SystemNet
import com.mianeko.fuzzyinferencesystemsbackend.api.models.SystemTemplateNet
import com.mianeko.fuzzyinferencesystemsbackend.api.models.enums.FuzzySystemTypeNet
import com.mianeko.fuzzyinferencesystemsbackend.api.models.enums.SpecializationTypeNet
import com.mianeko.fuzzyinferencesystemsbackend.services.models.InferenceSystem
import com.mianeko.fuzzyinferencesystemsbackend.services.models.SystemTemplate
import com.mianeko.fuzzyinferencesystemsbackend.services.models.enums.FuzzySystemType
import com.mianeko.fuzzyinferencesystemsbackend.services.models.enums.SpecializationType

data class SystemDTONet(
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

    fun toPartialModelNet() = PartialSystemNet(
        id = this.id,
        name = this.name
    )

    fun toModelNet() = SystemNet(
        name = this.name,
        type = FuzzySystemTypeNet.fromFuzzySystemType(this.type),
        specializationType = SpecializationTypeNet.fromSpecializationType(this.specializationType)
    )

    companion object {
        fun fromModel(system: InferenceSystem) = SystemDTONet(
            id = system.id,
            name = system.name,
            type = system.type,
            specializationType = system.specializationType
        )
    }
}

data class SystemTemplateDTONet(
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

    companion object {
        fun fromModelNet(systemTemplateNet: SystemTemplateNet, id: Int?) = SystemTemplateDTONet(
            id = id,
            name = systemTemplateNet.name,
            type = systemTemplateNet.type.toFuzzySystemType(),
            specializationType = systemTemplateNet.specializationType.toSpecializationType()
        )
    }
}


