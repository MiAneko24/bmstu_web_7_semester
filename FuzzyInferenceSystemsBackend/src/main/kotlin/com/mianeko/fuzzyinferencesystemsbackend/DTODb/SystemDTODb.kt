package com.mianeko.fuzzyinferencesystemsbackend.DTODb

import com.mianeko.fuzzyinferencesystemsbackend.database.entities.DBInsertableSystem
import com.mianeko.fuzzyinferencesystemsbackend.database.entities.DBSystem
import com.mianeko.fuzzyinferencesystemsbackend.services.models.InferenceSystem
import com.mianeko.fuzzyinferencesystemsbackend.services.models.SystemTemplate
import com.mianeko.fuzzyinferencesystemsbackend.services.models.enums.FuzzySystemType
import com.mianeko.fuzzyinferencesystemsbackend.services.models.enums.SpecializationType

data class SystemDTODb(
    val id: Int,
    val name: String,
    val type: FuzzySystemType,
    val specializationType: SpecializationType
) {
    fun toModel() = InferenceSystem(
        id = this.id,
        name = this.name,
        type = this.type,
        specializationType = this.specializationType
    )

    fun toModelDb() = DBSystem(
        id = this.id,
        name = this.name,
        type = this.type.toString(),
        specializationType = this.specializationType.toString()
    )

    companion object {
        fun fromModel(system: InferenceSystem) = SystemDTODb(
            id = system.id,
            name = system.name,
            type = system.type,
            specializationType = system.specializationType
        )

        fun fromModelDb(dbSystem: DBSystem) = SystemDTODb(
            id = dbSystem.id,
            name = dbSystem.name,
            type = FuzzySystemType.fromString(dbSystem.type),
            specializationType = SpecializationType.fromString(dbSystem.specializationType)
        )
    }
}

data class SystemTemplateDTODb(
    val id: Int,
    val name: String,
    val type: FuzzySystemType,
    val specializationType: SpecializationType
) {
    fun toModelDb() = DBInsertableSystem(
        id = this.id,
        name = this.name,
        type = this.type.toString(),
        specializationType = this.specializationType.toString()
    )

    companion object {
        fun fromModel(systemTemplate: SystemTemplate) = SystemTemplateDTODb(
            id = systemTemplate.id,
            name = systemTemplate.name,
            type = systemTemplate.type,
            specializationType = systemTemplate.specializationType
        )
    }
}


