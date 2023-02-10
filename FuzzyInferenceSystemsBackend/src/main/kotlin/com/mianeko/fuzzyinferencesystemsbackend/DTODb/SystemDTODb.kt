package com.mianeko.fuzzyinferencesystemsbackend.DTODb

import com.mianeko.fuzzyinferencesystemsbackend.DTODb.enums.FuzzySystemTypeDTODb
import com.mianeko.fuzzyinferencesystemsbackend.DTODb.enums.SpecializationTypeDTODb
import com.mianeko.fuzzyinferencesystemsbackend.database.entities.DBInsertableSystem
import com.mianeko.fuzzyinferencesystemsbackend.database.entities.DBSystem
import com.mianeko.fuzzyinferencesystemsbackend.database.entities.enums.DBFuzzySystemType
import com.mianeko.fuzzyinferencesystemsbackend.database.entities.enums.DBSpecializationType
import com.mianeko.fuzzyinferencesystemsbackend.services.models.InferenceSystem
import com.mianeko.fuzzyinferencesystemsbackend.services.models.SystemTemplate

data class SystemDTODb(
    val id: Int,
    val name: String,
    val type: FuzzySystemTypeDTODb,
    val specializationType: SpecializationTypeDTODb
) {
    fun toModel() = InferenceSystem(
        id = this.id,
        name = this.name,
        type = this.type.toFuzzySystemType(),
        specializationType = this.specializationType.toSpecializationType()
    )

    fun toModelDb() = DBSystem(
        id = this.id,
        name = this.name,
        type = this.type.toFuzzySystemTypeDb().text,
        specializationType = this.specializationType.toSpecializationTypeDb().text
    )

    companion object {
        fun fromModel(system: InferenceSystem) = SystemDTODb(
            id = system.id,
            name = system.name,
            type = FuzzySystemTypeDTODb.fromFuzzySystemType(system.type),
            specializationType = SpecializationTypeDTODb.fromSpecializationType(system.specializationType)
        )

        fun fromModelDb(dbSystem: DBSystem) = SystemDTODb(
            id = dbSystem.id,
            name = dbSystem.name,
            type = FuzzySystemTypeDTODb.fromFuzzySystemTypeDb(
                DBFuzzySystemType.fromString(dbSystem.type)
            ),
            specializationType = SpecializationTypeDTODb.fromSpecializationTypeDb(DBSpecializationType.fromString(dbSystem.specializationType))
        )
    }
}

data class SystemTemplateDTODb(
    val id: Int,
    val name: String,
    val type: FuzzySystemTypeDTODb,
    val specializationType: SpecializationTypeDTODb
) {
    fun toModelDb() = DBInsertableSystem(
        id = this.id,
        name = this.name,
        type = this.type.toFuzzySystemTypeDb().text,
        specializationType = this.specializationType.toString()
    )

    companion object {
        fun fromModel(systemTemplate: SystemTemplate) = SystemTemplateDTODb(
            id = systemTemplate.id,
            name = systemTemplate.name,
            type = FuzzySystemTypeDTODb.fromFuzzySystemType(systemTemplate.type),
            specializationType = SpecializationTypeDTODb.fromSpecializationType(systemTemplate.specializationType)
        )
    }
}


