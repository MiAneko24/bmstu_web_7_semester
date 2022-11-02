package com.mianeko.fuzzyinferencesystemsbackend.api.models

import com.mianeko.fuzzyinferencesystemsbackend.DTO.AntecedentDTO
import com.mianeko.fuzzyinferencesystemsbackend.services.models.AntecedentTemplateDTO

data class AntecedentNet(
    val id: Int,
    val text: String,
    val membershipFunctionId: Int
) {
    fun toDTO(systemId: Int, antecedentId: Int) = AntecedentDTO(
        id = antecedentId,
        text = this.text,
        membershipFunctionId = this.membershipFunctionId,
        systemId = systemId
    )

    companion object {
        fun fromDTO(antecedent: AntecedentDTO) =
            AntecedentNet(
                id = antecedent.id,
                text = antecedent.text,
                membershipFunctionId = antecedent.membershipFunctionId
            )
    }
}

data class AntecedentTemplateNet(
    val membershipFunctionId: Int
) {
    fun toDTO(systemId: Int, antecedentId: Int?) = AntecedentTemplateDTO(
        id = antecedentId,
        membershipFunctionId = this.membershipFunctionId,
        systemId = systemId
    )
}