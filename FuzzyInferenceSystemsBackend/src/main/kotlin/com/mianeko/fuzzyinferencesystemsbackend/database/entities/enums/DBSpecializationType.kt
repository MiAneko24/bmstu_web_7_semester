package com.mianeko.fuzzyinferencesystemsbackend.database.entities.enums

import com.fasterxml.jackson.annotation.JsonValue
import com.mianeko.fuzzyinferencesystemsbackend.exceptions.SystemSpecializationException

enum class DBSpecializationType(@JsonValue val text: String) {
    Physics("physics"),
    Chemistry("chemistry"),
    Informatics("informatics");

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