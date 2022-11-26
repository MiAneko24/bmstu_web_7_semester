package com.mianeko.fuzzyinferencesystemsbackend.DTODb.enums

import com.mianeko.fuzzyinferencesystemsbackend.database.entities.enums.DBSpecializationType
import com.mianeko.fuzzyinferencesystemsbackend.exceptions.SystemSpecializationException
import com.mianeko.fuzzyinferencesystemsbackend.services.models.enums.SpecializationType

enum class SpecializationTypeDTODb(private val text: String) {
    Physics("physics"),
    Chemistry("chemistry"),
    Informatics("informatics");

    override fun toString(): String {
        return text
    }

    fun toSpecializationType() =
        when (this) {
            Physics -> SpecializationType.Physics
            Chemistry -> SpecializationType.Chemistry
            Informatics -> SpecializationType.Informatics
        }

    fun toSpecializationTypeDb() =
        when (this) {
            Physics -> DBSpecializationType.Physics
            Chemistry -> DBSpecializationType.Chemistry
            Informatics -> DBSpecializationType.Informatics
        }

    companion object {
        fun fromString(s: String): SpecializationTypeDTODb {
            val st: SpecializationTypeDTODb
            st = when (s) {
                "physics" -> Physics
                "chemistry" -> Chemistry
                "informatics" -> Informatics
                else -> throw SystemSpecializationException(s)
            }
            return st
        }

        fun fromSpecializationType(s: SpecializationType) =
            when (s) {
                SpecializationType.Physics -> Physics
                SpecializationType.Chemistry -> Chemistry
                SpecializationType.Informatics -> Informatics
            }

        fun fromSpecializationTypeDb(s: DBSpecializationType) =
            when (s) {
                DBSpecializationType.Physics -> Physics
                DBSpecializationType.Chemistry -> Chemistry
                DBSpecializationType.Informatics -> Informatics
            }
    }
}
