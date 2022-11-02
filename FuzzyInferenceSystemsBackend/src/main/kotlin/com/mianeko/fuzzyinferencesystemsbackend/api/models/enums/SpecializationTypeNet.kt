package com.mianeko.fuzzyinferencesystemsbackend.api.models.enums

import com.fasterxml.jackson.annotation.JsonValue
import com.mianeko.fuzzyinferencesystemsbackend.exceptions.SystemSpecializationException
import com.mianeko.fuzzyinferencesystemsbackend.services.models.enums.SpecializationType

enum class SpecializationTypeNet(@JsonValue val text: String) {
    Physics("physics"),
    Chemistry("chemistry"),
    Informatics("informatics");

    fun toSpecializationType() =
        when (this) {
            Physics -> SpecializationType.Physics
            Chemistry -> SpecializationType.Chemistry
            Informatics -> SpecializationType.Informatics
        }

    companion object {
        fun fromString(s: String?) =
            when (s) {
                "physics" -> Physics
                "chemistry" -> Chemistry
                "informatics" -> Informatics
                else -> throw SystemSpecializationException(s)
            }

        fun fromSpecializationType(specializationType: SpecializationType) =
            when (specializationType) {
                SpecializationType.Physics -> Physics
                SpecializationType.Chemistry -> Chemistry
                SpecializationType.Informatics -> Informatics
            }
    }
}