package com.mianeko.fuzzyinferencesystemsbackend.database.entities.enums

import com.fasterxml.jackson.annotation.JsonValue
import com.mianeko.fuzzyinferencesystemsbackend.exceptions.AntecedentConnectionException

enum class DBAntecedentConnection(@JsonValue val text: String) {
    Or("or"),
    And("and");

    companion object {
        fun fromString(s: String) =
            when(s) {
                "or" -> Or
                "and" -> And
                else -> throw AntecedentConnectionException(s)
            }

    }
}