package com.mianeko.fuzzyinferencesystemsbackend.services.models

import com.mianeko.fuzzyinferencesystemsbackend.DTO.PartialMembershipFunctionDTO

data class PartialMembershipFunction(
    val id: Int,
    val term: String?,
    val variableId: Int?
) {
    fun toDTO(systemId: Int) = PartialMembershipFunctionDTO(
        id = this.id,
        term = this.term,
        systemId = systemId,
        variableId = this.variableId
    )
}