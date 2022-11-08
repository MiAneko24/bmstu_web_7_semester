package com.mianeko.fuzzyinferencesystemsbackend.api.models.enums

import com.fasterxml.jackson.annotation.JsonValue
import com.mianeko.fuzzyinferencesystemsbackend.exceptions.SystemSpecializationException
import com.mianeko.fuzzyinferencesystemsbackend.services.models.enums.SpecializationType

enum class SpecializationTypeNet(@JsonValue val text: String) {
    physics("physics"),
    chemistry("chemistry"),
    informatics("informatics");

    fun toSpecializationType() =
        when (this) {
            physics -> SpecializationType.Physics
            chemistry -> SpecializationType.Chemistry
            informatics -> SpecializationType.Informatics
        }

    companion object {
        fun fromString(s: String?) =
            when (s) {
                "physics" -> physics
                "chemistry" -> chemistry
                "informatics" -> informatics
                else -> throw SystemSpecializationException(s)
            }

        fun fromSpecializationType(specializationType: SpecializationType) =
            when (specializationType) {
                SpecializationType.Physics -> physics
                SpecializationType.Chemistry -> chemistry
                SpecializationType.Informatics -> informatics
            }
    }
}