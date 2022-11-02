package com.mianeko.fuzzyinferencesystemsbackend.database.entities.enums

import com.fasterxml.jackson.annotation.JsonValue
import com.mianeko.fuzzyinferencesystemsbackend.exceptions.SystemTypeException
import com.mianeko.fuzzyinferencesystemsbackend.services.models.enums.FuzzySystemType


enum class DBFuzzySystemType(@JsonValue val text: String) {
    Mamdani("Mamdani"),
    Sugeno("Sugeno");

    fun toFuzzySystemType() =
        FuzzySystemType.fromString(text)

    companion object {
        fun fromFuzzySystemType(fuzzySystemType: FuzzySystemType) =
            when (fuzzySystemType) {
                FuzzySystemType.Mamdani -> Mamdani
                FuzzySystemType.Sugeno -> Sugeno
            }

        fun fromString(s: String) =
            when (s) {
                "Mamdani" -> Mamdani
                "Sugeno" -> Sugeno
                else -> throw SystemTypeException(s)
            }
    }
}