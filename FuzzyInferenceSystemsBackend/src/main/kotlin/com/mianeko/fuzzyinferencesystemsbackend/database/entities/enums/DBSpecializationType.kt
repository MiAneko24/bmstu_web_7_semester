package com.mianeko.fuzzyinferencesystemsbackend.database.entities.enums

import com.fasterxml.jackson.annotation.JsonValue
import com.mianeko.fuzzyinferencesystemsbackend.exceptions.SystemSpecializationException
import com.mianeko.fuzzyinferencesystemsbackend.services.models.enums.SpecializationType

enum class DBSpecializationType(@JsonValue val text: String) {
    Physics("physics"),
    Chemistry("chemistry"),
    Informatics("informatics");

    fun toSpecializationType() =
        SpecializationType.fromString(text)

    companion object {
        fun fromString(s: String?) =
            when (s) {
                "physics" -> Physics
                "chemistry" -> Chemistry
                "informatics" -> Informatics
                else -> throw SystemSpecializationException(s)
            }
    }
}