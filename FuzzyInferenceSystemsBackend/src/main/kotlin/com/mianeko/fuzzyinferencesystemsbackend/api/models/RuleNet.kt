package com.mianeko.fuzzyinferencesystemsbackend.api.models

import com.mianeko.fuzzyinferencesystemsbackend.DTO.RuleDTO
import com.mianeko.fuzzyinferencesystemsbackend.api.models.enums.AntecedentConnectionNet
import com.mianeko.fuzzyinferencesystemsbackend.services.models.RuleTemplateDTO

data class RuleNet(
    val text: String,
    val antecedentConnection: AntecedentConnectionNet,
    val weight: Double = 1.0,
    val antecedents: List<Int>
) {
    fun toDTO(systemId: Int, ruleId: Int) = RuleDTO(
        id = ruleId,
        text = this.text,
        systemId = systemId,
        antecedentConnection = this.antecedentConnection.toAntecedentConnection(),
        antecedentIds = this.antecedents,
        weight = this.weight
    )

    companion object {
        fun fromDTO(rule: RuleDTO) = RuleNet(
            text = rule.text,
            antecedentConnection = AntecedentConnectionNet.fromAntecedentConnection(rule.antecedentConnection),
            antecedents = rule.antecedentIds,
            weight = rule.weight
        )
    }
}

data class RuleTemplateNet(
    val antecedentConnection: AntecedentConnectionNet,
    val weight: Double
) {
    fun toDTO(systemId: Int, ruleId: Int?) = RuleTemplateDTO(
        id = ruleId,
        systemId = systemId,
        antecedentConnection = this.antecedentConnection.toAntecedentConnection(),
        weight = this.weight,
        antecedents = null
    )
}

data class RulePatchNet(
    val antecedents: List<Int>
) {
    fun applyTo(ruleDTO: RuleDTO) = RuleTemplateDTO(
        id = ruleDTO.id,
        systemId = ruleDTO.systemId,
        antecedentConnection = ruleDTO.antecedentConnection,
        weight = ruleDTO.weight,
        antecedents = this.antecedents
    )
}