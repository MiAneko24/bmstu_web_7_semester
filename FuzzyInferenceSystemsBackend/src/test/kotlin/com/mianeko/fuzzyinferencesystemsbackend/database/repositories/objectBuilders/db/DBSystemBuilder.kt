package com.mianeko.fuzzyinferencesystemsbackend.database.repositories.objectBuilders.db

import com.mianeko.fuzzyinferencesystemsbackend.database.entities.DBSystem
import com.mianeko.fuzzyinferencesystemsbackend.database.entities.enums.DBFuzzySystemType
import com.mianeko.fuzzyinferencesystemsbackend.database.entities.enums.DBSpecializationType

class DBSystemBuilder private constructor(
    private var id: Int = defaultId,
    private var name: String = defaultName,
    private var type: String = defaultType,
    private var specializationType: String = defaultSpecializationType,
){
    fun build() =
        DBSystem(
            id = this.id,
            name = this.name,
            type = this.type,
            specializationType = this.specializationType
        )

    fun withId(id: Int): DBSystemBuilder {
        this.id = id
        return this
    }

    fun withName(name: String): DBSystemBuilder {
        this.name = name
        return this
    }

    fun withType(type: DBFuzzySystemType): DBSystemBuilder {
        this.type = type.text
        return this
    }

    fun withSpecializationType(dbSpecializationType: DBSpecializationType): DBSystemBuilder {
        this.specializationType = dbSpecializationType.text
        return this
    }

    companion object {
        var defaultId: Int = 1
        var defaultName: String = "System"
        var defaultType: String = "Mamdani"
        var defaultSpecializationType: String = "chemistry"

        fun nDBSystem() = DBSystemBuilder()
    }
}