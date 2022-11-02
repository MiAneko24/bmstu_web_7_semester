package com.mianeko.fuzzyinferencesystemsbackend.services.models.enums

import com.mianeko.fuzzyinferencesystemsbackend.exceptions.AntecedentConnectionException

enum class AntecedentConnection(private val text: String) {
    Or("or"),
    And("and");

    override fun toString(): String {
        return text
    }

    companion object {
        fun fromString(s: String) =
            when (s) {
                "or" -> Or
                "and" -> And
                else -> throw AntecedentConnectionException(s)
            }
    }
}