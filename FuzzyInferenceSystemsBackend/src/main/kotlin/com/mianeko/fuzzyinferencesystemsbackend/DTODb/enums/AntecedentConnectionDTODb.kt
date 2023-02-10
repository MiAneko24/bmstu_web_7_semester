package com.mianeko.fuzzyinferencesystemsbackend.DTODb.enums

import com.mianeko.fuzzyinferencesystemsbackend.database.entities.enums.DBAntecedentConnection
import com.mianeko.fuzzyinferencesystemsbackend.services.models.enums.AntecedentConnection

enum class AntecedentConnectionDTODb(private val text: String) {
    Or("or"),
    And("and");

    override fun toString(): String {
        return text
    }

    fun toAntecedentConnectionDb() =
        when (this) {
            Or -> DBAntecedentConnection.Or
            And -> DBAntecedentConnection.And
        }

    fun toAntecedentConnection() =
        when (this) {
            Or -> AntecedentConnection.Or
            And -> AntecedentConnection.And
        }

    companion object {
        fun fromAntecedentConnection(a: AntecedentConnection) =
            when (a) {
                AntecedentConnection.Or -> Or
                AntecedentConnection.And -> And
            }

        fun fromAntecedentConnectionDb(a: DBAntecedentConnection) =
            when (a) {
                DBAntecedentConnection.Or -> Or
                DBAntecedentConnection.And -> And
            }
    }
}