package com.mianeko.fuzzyinferencesystemsbackend.DTODb.enums

import com.mianeko.fuzzyinferencesystemsbackend.database.entities.enums.DBFuzzySystemType
import com.mianeko.fuzzyinferencesystemsbackend.exceptions.SystemTypeException
import com.mianeko.fuzzyinferencesystemsbackend.services.models.enums.FuzzySystemType

enum class FuzzySystemTypeDTODb(private val text: String) {
    Mamdani("Mamdani"),
    Sugeno("Sugeno");

    override fun toString(): String {
        return text
    }

    fun toFuzzySystemTypeDb() =
        when (this) {
            Mamdani -> DBFuzzySystemType.Mamdani
            Sugeno -> DBFuzzySystemType.Sugeno
        }

    fun toFuzzySystemType() =
        when(this) {
            Mamdani -> FuzzySystemType.Mamdani
            Sugeno -> FuzzySystemType.Sugeno
        }

    companion object {
        fun fromString(s: String) =
            when (s) {
                "Mamdani" -> Mamdani
                "Sugeno" -> Sugeno
                else -> throw SystemTypeException(s)
            }

        fun fromFuzzySystemType(f: FuzzySystemType) =
            when(f) {
                FuzzySystemType.Mamdani -> Mamdani
                FuzzySystemType.Sugeno -> Sugeno
            }

        fun fromFuzzySystemTypeDb(f: DBFuzzySystemType) =
            when(f) {
                DBFuzzySystemType.Mamdani -> Mamdani
                DBFuzzySystemType.Sugeno -> Sugeno
            }
    }
}