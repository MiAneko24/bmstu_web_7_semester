package com.mianeko.fuzzyinferencesystemsbackend.database.entities.enums

import com.fasterxml.jackson.annotation.JsonValue
import com.mianeko.fuzzyinferencesystemsbackend.exceptions.SystemTypeException


enum class DBFuzzySystemType(@JsonValue val text: String) {
    Mamdani("Mamdani"),
    Sugeno("Sugeno");

    companion object {
        fun fromString(s: String) =
            when (s) {
                "Mamdani" -> Mamdani
                "Sugeno" -> Sugeno
                else -> throw SystemTypeException(s)
            }
    }
}