package com.mianeko.fuzzyinferencesystemsbackend.database.repositories.objectBuilders

import com.mianeko.fuzzyinferencesystemsbackend.services.models.InferenceSystem
import com.mianeko.fuzzyinferencesystemsbackend.services.models.enums.FuzzySystemType
import com.mianeko.fuzzyinferencesystemsbackend.services.models.enums.SpecializationType

class SystemBuilder private constructor(
    private var id: Int = defaultId,
    private var name: String = defaultName,
    private var type: FuzzySystemType = defaultType,
    private var specializationType: SpecializationType = defaultSpecializationType,
){
    fun build() =
        InferenceSystem(
            id = this.id,
            name = this.name,
            type = this.type,
            specializationType = this.specializationType
        )

    fun withId(id: Int): SystemBuilder {
        this.id = id
        return this
    }

    fun withName(name: String): SystemBuilder {
        this.name = name
        return this
    }

    fun withType(type: FuzzySystemType): SystemBuilder {
        this.type = type
        return this
    }

    fun withSpecializationType(specializationType: SpecializationType): SystemBuilder {
        this.specializationType = specializationType
        return this
    }

    companion object {
        var defaultId: Int = 1
        var defaultName: String = "System"
        var defaultType: FuzzySystemType = FuzzySystemType.Mamdani
        var defaultSpecializationType: SpecializationType = SpecializationType.Chemistry

        fun nSystem() = SystemBuilder()
    }
}