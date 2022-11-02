package com.mianeko.fuzzyinferencesystemsbackend.DTO

import com.mianeko.fuzzyinferencesystemsbackend.services.models.AntecedentTemplate


data class AntecedentDTO(
    val id: Int,
    val membershipFunctionId: Int,
    val text: String,
    val systemId: Int
) {
    fun toModel() =
        AntecedentTemplate(
            id = this.id,
            membershipFunctionId = this.membershipFunctionId,
            systemId = this.systemId
        )
}
