package com.mianeko.fuzzyinferencesystemsbackend.services.models.enums

import com.mianeko.fuzzyinferencesystemsbackend.exceptions.SystemTypeException

enum class FuzzySystemType(private val text: String) {
    Mamdani("Mamdani"),
    Sugeno("Sugeno");

    override fun toString(): String {
        return text
    }

    companion object {
        fun fromString(s: String) =
            when (s) {
                "Mamdani" -> Mamdani
                "Sugeno" -> Sugeno
                else -> throw SystemTypeException(s)
            }
    }
}