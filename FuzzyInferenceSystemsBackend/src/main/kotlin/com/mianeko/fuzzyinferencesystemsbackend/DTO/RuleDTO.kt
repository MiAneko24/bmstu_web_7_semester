package com.mianeko.fuzzyinferencesystemsbackend.DTO

import com.mianeko.fuzzyinferencesystemsbackend.services.models.*
import com.mianeko.fuzzyinferencesystemsbackend.services.models.enums.AntecedentConnection


data class RuleDTO(
    val id: Int,
    val text: String,
    val systemId: Int,
    val antecedentIds: List<Int>,
    val antecedentConnection: AntecedentConnection,
    val weight: Double,
) {
    fun toModel(antecedents: List<AntecedentTemplate>) = RuleTemplate(
        id = this.id,
        systemId = this.systemId,
        antecedentConnection = this.antecedentConnection,
        weight = this.weight,
        antecedents = antecedents
    )
}

data class PartialRuleDTO(
    val id: Int,
    val text: String,
    val systemId: Int
)
