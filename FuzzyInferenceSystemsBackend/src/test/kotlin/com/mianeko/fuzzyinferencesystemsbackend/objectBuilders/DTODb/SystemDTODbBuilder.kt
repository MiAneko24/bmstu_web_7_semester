package com.mianeko.fuzzyinferencesystemsbackend.objectBuilders.DTODb

import com.mianeko.fuzzyinferencesystemsbackend.DTODb.SystemDTODb
import com.mianeko.fuzzyinferencesystemsbackend.DTODb.enums.FuzzySystemTypeDTODb
import com.mianeko.fuzzyinferencesystemsbackend.DTODb.enums.SpecializationTypeDTODb

class SystemDTODbBuilder private constructor(
    private var id: Int = defaultId,
    private var name: String = defaultName,
    private var type: FuzzySystemTypeDTODb = defaultType,
    private var specializationType: SpecializationTypeDTODb = defaultSpecializationType,
){
    fun build() =
        SystemDTODb(
            id = this.id,
            name = this.name,
            type = this.type,
            specializationType = this.specializationType
        )

    fun withId(id: Int): SystemDTODbBuilder {
        this.id = id
        return this
    }

    fun withName(name: String): SystemDTODbBuilder {
        this.name = name
        return this
    }

    fun withType(type: FuzzySystemTypeDTODb): SystemDTODbBuilder {
        this.type = type
        return this
    }

    fun withSpecializationType(specializationType: SpecializationTypeDTODb): SystemDTODbBuilder {
        this.specializationType = specializationType
        return this
    }

    companion object {
        var defaultId: Int = 1
        var defaultName: String = "System"
        var defaultType: FuzzySystemTypeDTODb = FuzzySystemTypeDTODb.Mamdani
        var defaultSpecializationType: SpecializationTypeDTODb = SpecializationTypeDTODb.Chemistry

        fun nSystemDTODb() = SystemDTODbBuilder()
    }
}