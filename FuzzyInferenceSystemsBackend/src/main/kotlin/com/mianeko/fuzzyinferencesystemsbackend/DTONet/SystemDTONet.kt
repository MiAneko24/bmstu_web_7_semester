package com.mianeko.fuzzyinferencesystemsbackend.DTONet

import com.mianeko.fuzzyinferencesystemsbackend.DTODb.enums.FuzzySystemTypeDTONet
import com.mianeko.fuzzyinferencesystemsbackend.DTODb.enums.SpecializationTypeDTONet
import com.mianeko.fuzzyinferencesystemsbackend.api.models.PartialSystemNet
import com.mianeko.fuzzyinferencesystemsbackend.api.models.SystemNet
import com.mianeko.fuzzyinferencesystemsbackend.api.models.SystemTemplateNet
import com.mianeko.fuzzyinferencesystemsbackend.services.models.InferenceSystem
import com.mianeko.fuzzyinferencesystemsbackend.services.models.SystemTemplate

data class SystemDTONet(
    val id: Int,
    val name: String,
    val type: FuzzySystemTypeDTONet,
    val specializationType: SpecializationTypeDTONet
) {
    fun toModel() = SystemTemplate(
        id = this.id,
        name = this.name,
        type = this.type.toFuzzySystemType(),
        specializationType = this.specializationType.toSpecializationType()
    )

    fun toPartialModelNet() = PartialSystemNet(
        id = this.id,
        name = this.name
    )

    fun toModelNet() = SystemNet(
        id = this.id,
        name = this.name,
        type = this.type.toFuzzySystemTypeNet(),
        specializationType = this.specializationType.toSpecializationTypeNet()
    )

    companion object {
        fun fromModel(system: InferenceSystem) = SystemDTONet(
            id = system.id,
            name = system.name,
            type = FuzzySystemTypeDTONet.fromFuzzySystemType(system.type),
            specializationType = SpecializationTypeDTONet.fromSpecializationType(system.specializationType)
        )
    }
}

data class SystemTemplateDTONet(
    val id: Int?,
    val name: String,
    val type: FuzzySystemTypeDTONet,
    val specializationType: SpecializationTypeDTONet
) {
    fun toModel(id: Int) = SystemTemplate(
        id = id,
        name = this.name,
        type = this.type.toFuzzySystemType(),
        specializationType = this.specializationType.toSpecializationType()
    )

    companion object {
        fun fromModelNet(systemTemplateNet: SystemTemplateNet, id: Int?) = SystemTemplateDTONet(
            id = id,
            name = systemTemplateNet.name,
            type = FuzzySystemTypeDTONet.fromFuzzySystemTypeNet(systemTemplateNet.type),
            specializationType = SpecializationTypeDTONet.fromSpecializationTypeNet(systemTemplateNet.specializationType)
        )
    }
}


