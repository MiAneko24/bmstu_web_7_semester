package com.mianeko.fuzzyinferencesystemsbackend.api.models.enums

import com.fasterxml.jackson.annotation.JsonValue
import com.mianeko.fuzzyinferencesystemsbackend.exceptions.SystemTypeException
import com.mianeko.fuzzyinferencesystemsbackend.services.models.enums.FuzzySystemType


enum class FuzzySystemTypeNet(@JsonValue val text: String) {
    Mamdani("Mamdani"),
    Sugeno("Sugeno");

    fun toFuzzySystemType() =
        when (this) {
            Mamdani -> FuzzySystemType.Mamdani
            Sugeno -> FuzzySystemType.Sugeno
        }

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