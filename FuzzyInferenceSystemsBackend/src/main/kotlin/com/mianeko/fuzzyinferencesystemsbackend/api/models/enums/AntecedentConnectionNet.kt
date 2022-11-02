package com.mianeko.fuzzyinferencesystemsbackend.api.models.enums

import com.fasterxml.jackson.annotation.JsonValue
import com.mianeko.fuzzyinferencesystemsbackend.exceptions.AntecedentConnectionException
import com.mianeko.fuzzyinferencesystemsbackend.services.models.enums.AntecedentConnection

enum class AntecedentConnectionNet(@JsonValue val text: String) {
    Or("or"),
    And("and");

    fun toAntecedentConnection() =
        when (this) {
            Or -> AntecedentConnection.Or
            And -> AntecedentConnection.And
        }

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