package com.mianeko.fuzzyinferencesystemsbackend.DTODb.enums

import com.mianeko.fuzzyinferencesystemsbackend.api.models.enums.AntecedentConnectionNet
import com.mianeko.fuzzyinferencesystemsbackend.services.models.enums.AntecedentConnection

enum class AntecedentConnectionDTONet(private val text: String) {
    Or("or"),
    And("and");

    override fun toString(): String {
        return text
    }

    fun toAntecedentConnectionNet() =
        when (this) {
            Or -> AntecedentConnectionNet.or
            And -> AntecedentConnectionNet.and
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

        fun fromAntecedentConnectionNet(a: AntecedentConnectionNet) =
            when (a) {
                AntecedentConnectionNet.or -> Or
                AntecedentConnectionNet.and -> And
            }
    }
}