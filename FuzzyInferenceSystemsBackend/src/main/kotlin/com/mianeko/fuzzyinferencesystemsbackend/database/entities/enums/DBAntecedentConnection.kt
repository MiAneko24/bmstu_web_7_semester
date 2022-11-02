package com.mianeko.fuzzyinferencesystemsbackend.database.entities.enums

import com.fasterxml.jackson.annotation.JsonValue
import com.mianeko.fuzzyinferencesystemsbackend.exceptions.AntecedentConnectionException
import com.mianeko.fuzzyinferencesystemsbackend.services.models.enums.AntecedentConnection

enum class DBAntecedentConnection(@JsonValue val text: String) {
    Or("or"),
    And("and");

    fun toAntecedentConnection() =
        AntecedentConnection.fromString(text)

    companion object {
        fun fromAntecedentConnection(antecedentConnection: AntecedentConnection) =
            when (antecedentConnection) {
                AntecedentConnection.Or -> Or
                AntecedentConnection.And -> And
            }

        fun fromString(s: String) =
            when(s) {
                "or" -> Or
                "and" -> And
                else -> throw AntecedentConnectionException(s)
            }

    }
}