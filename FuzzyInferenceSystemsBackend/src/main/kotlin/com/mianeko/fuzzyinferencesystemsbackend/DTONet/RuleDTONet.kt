package com.mianeko.fuzzyinferencesystemsbackend.DTONet

import com.mianeko.fuzzyinferencesystemsbackend.DTODb.enums.AntecedentConnectionDTONet
import com.mianeko.fuzzyinferencesystemsbackend.api.models.PartialRuleNet
import com.mianeko.fuzzyinferencesystemsbackend.api.models.RuleNet
import com.mianeko.fuzzyinferencesystemsbackend.api.models.RuleTemplateNet
import com.mianeko.fuzzyinferencesystemsbackend.services.models.AntecedentTemplate
import com.mianeko.fuzzyinferencesystemsbackend.services.models.Rule
import com.mianeko.fuzzyinferencesystemsbackend.services.models.RuleTemplate


data class RuleDTONet(
    val id: Int,
    val text: String,
    val systemId: Int,
    val antecedentIds: List<Int>,
    val antecedentConnection: AntecedentConnectionDTONet,
    val weight: Double,
) {
    fun toModel(antecedents: List<AntecedentTemplate>) = RuleTemplate(
        id = this.id,
        systemId = this.systemId,
        antecedentConnection = this.antecedentConnection.toAntecedentConnection(),
        weight = this.weight,
        antecedents = antecedents
    )

    fun toPartialModelNet() = PartialRuleNet(
        id = this.id,
        text = this.text
    )

    fun toModelNet() = RuleNet(
        text = this.text,
        antecedentConnection = this.antecedentConnection.toAntecedentConnectionNet(),
        antecedents = this.antecedentIds,
        weight = this.weight
    )


    companion object {
        fun fromModel(rule: Rule) = RuleDTONet(
            id = rule.id,
            text = rule.getText(),
            systemId = rule.inferenceSystem.id,
            antecedentIds = rule.antecedents.map { it.id },
            antecedentConnection = AntecedentConnectionDTONet.fromAntecedentConnection(rule.antecedentConnection),
            weight = rule.weight
        )
    }
}

data class RuleTemplateDTONet(
    val id: Int?,
    val systemId: Int,
    val antecedentConnection: AntecedentConnectionDTONet,
    val weight: Double,
    val antecedents: List<Int>?
) {
    fun toModel(
        id: Int,
        antecedents: List<AntecedentTemplate>
    ) = RuleTemplate(
        id = id,
        systemId = this.systemId,
        antecedentConnection = this.antecedentConnection.toAntecedentConnection(),
        weight = this.weight,
        antecedents = antecedents
    )

    companion object {
        fun fromModelNet(ruleTemplateNet: RuleTemplateNet, systemId: Int, ruleId: Int?) = RuleTemplateDTONet(
            id = ruleId,
            systemId = systemId,
            antecedentConnection = AntecedentConnectionDTONet.fromAntecedentConnectionNet(ruleTemplateNet.antecedentConnection),
            weight = ruleTemplateNet.weight,
            antecedents = null
        )
    }
}
