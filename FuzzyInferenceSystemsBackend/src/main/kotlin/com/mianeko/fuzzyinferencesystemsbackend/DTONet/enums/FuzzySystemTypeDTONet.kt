package com.mianeko.fuzzyinferencesystemsbackend.DTODb.enums

import com.mianeko.fuzzyinferencesystemsbackend.api.models.enums.FuzzySystemTypeNet
import com.mianeko.fuzzyinferencesystemsbackend.exceptions.SystemTypeException
import com.mianeko.fuzzyinferencesystemsbackend.services.models.enums.FuzzySystemType

enum class FuzzySystemTypeDTONet(private val text: String) {
    Mamdani("Mamdani"),
    Sugeno("Sugeno");

    override fun toString(): String {
        return text
    }

    fun toFuzzySystemTypeNet() =
        when (this) {
            Mamdani -> FuzzySystemTypeNet.Mamdani
            Sugeno -> FuzzySystemTypeNet.Sugeno
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

        fun fromFuzzySystemTypeNet(f: FuzzySystemTypeNet) =
            when(f) {
                FuzzySystemTypeNet.Mamdani -> Mamdani
                FuzzySystemTypeNet.Sugeno -> Sugeno
            }
    }
}