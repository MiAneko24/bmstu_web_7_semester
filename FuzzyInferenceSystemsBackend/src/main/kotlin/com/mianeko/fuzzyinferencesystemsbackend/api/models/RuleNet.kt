package com.mianeko.fuzzyinferencesystemsbackend.api.models

import com.mianeko.fuzzyinferencesystemsbackend.DTONet.RuleDTONet
import com.mianeko.fuzzyinferencesystemsbackend.DTONet.RuleTemplateDTONet
import com.mianeko.fuzzyinferencesystemsbackend.api.models.enums.AntecedentConnectionNet

data class RuleNet(
    val text: String,
    val antecedentConnection: AntecedentConnectionNet,
    val weight: Double = 1.0,
    val antecedents: List<Int>
)

data class RuleTemplateNet(
    val antecedentConnection: AntecedentConnectionNet,
    val weight: Double
)

data class RulePatchNet(
    val antecedents: List<Int>
) {
    fun applyTo(ruleDTONet: RuleDTONet) = RuleTemplateDTONet(
        id = ruleDTONet.id,
        systemId = ruleDTONet.systemId,
        antecedentConnection = ruleDTONet.antecedentConnection,
        weight = ruleDTONet.weight,
        antecedents = this.antecedents
    )
}