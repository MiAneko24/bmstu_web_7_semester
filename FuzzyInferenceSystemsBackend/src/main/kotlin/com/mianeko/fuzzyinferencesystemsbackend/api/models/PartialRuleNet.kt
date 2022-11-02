package com.mianeko.fuzzyinferencesystemsbackend.api.models

import com.mianeko.fuzzyinferencesystemsbackend.DTO.PartialRuleDTO
import com.mianeko.fuzzyinferencesystemsbackend.DTO.RuleDTO


data class PartialRuleNet(
    val id: Int,
    val text: String
) {
    fun toDTO(systemId: Int) = PartialRuleDTO(
        id = this.id,
        text = this.text,
        systemId = systemId
    )

    companion object {
        fun fromDTO(rule: RuleDTO) = PartialRuleNet(
                id = rule.id,
                text = rule.text
            )
    }
}
