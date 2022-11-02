package com.mianeko.fuzzyinferencesystemsbackend.services.models.enums

import com.mianeko.fuzzyinferencesystemsbackend.exceptions.SystemSpecializationException

enum class SpecializationType(private val text: String) {
    Physics("physics"),
    Chemistry("chemistry"),
    Informatics("informatics");

    override fun toString(): String {
        return text
    }

    companion object {
        fun fromString(s: String): SpecializationType {
            val st: SpecializationType
            st = when (s) {
                "physics" -> Physics
                "chemistry" -> Chemistry
                "informatics" -> Informatics
                else -> throw SystemSpecializationException(s)
            }
            return st
        }
    }
}
