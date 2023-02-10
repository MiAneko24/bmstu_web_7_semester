package com.mianeko.fuzzyinferencesystemsbackend.objectBuilders.DTODb

import com.mianeko.fuzzyinferencesystemsbackend.DTODb.SystemTemplateDTODb
import com.mianeko.fuzzyinferencesystemsbackend.DTODb.enums.FuzzySystemTypeDTODb
import com.mianeko.fuzzyinferencesystemsbackend.DTODb.enums.SpecializationTypeDTODb

class SystemTemplateDTODbBuilder  private constructor(
    private var id: Int = defaultId,
    private var name: String = defaultName,
    private var type: FuzzySystemTypeDTODb = defaultType,
    private var specializationType: SpecializationTypeDTODb = defaultSpecializationType,
){
    fun build() =
        SystemTemplateDTODb(
            id = this.id,
            name = this.name,
            type = this.type,
            specializationType = this.specializationType
        )

    fun withId(id: Int): SystemTemplateDTODbBuilder {
        this.id = id
        return this
    }

    fun withName(name: String): SystemTemplateDTODbBuilder {
        this.name = name
        return this
    }

    fun withType(type: FuzzySystemTypeDTODb): SystemTemplateDTODbBuilder {
        this.type = type
        return this
    }

    fun withSpecializationType(specializationType: SpecializationTypeDTODb): SystemTemplateDTODbBuilder {
        this.specializationType = specializationType
        return this
    }

    companion object {
        var defaultId: Int = 1
        var defaultName: String = "System"
        var defaultType: FuzzySystemTypeDTODb = FuzzySystemTypeDTODb.Mamdani
        var defaultSpecializationType: SpecializationTypeDTODb = SpecializationTypeDTODb.Chemistry

        fun nSystemTemplateDTODb() = SystemTemplateDTODbBuilder()
    }
}