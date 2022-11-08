package com.mianeko.fuzzyinferencesystemsbackend.api.models.enums

import com.fasterxml.jackson.annotation.JsonValue
import com.mianeko.fuzzyinferencesystemsbackend.exceptions.AntecedentConnectionException
import com.mianeko.fuzzyinferencesystemsbackend.services.models.enums.AntecedentConnection

enum class AntecedentConnectionNet(@JsonValue val text: String) {
    or("or"),
    and("and");

    fun toAntecedentConnection() =
        when (this) {
            or -> AntecedentConnection.Or
            and -> AntecedentConnection.And
        }

    companion object {
        fun fromAntecedentConnection(antecedentConnection: AntecedentConnection) =
            when (antecedentConnection) {
                AntecedentConnection.Or -> or
                AntecedentConnection.And -> and
            }

        fun fromString(s: String) =
            when(s) {
                "or" -> or
                "and" -> and
                else -> throw AntecedentConnectionException(s)
            }

    }
}