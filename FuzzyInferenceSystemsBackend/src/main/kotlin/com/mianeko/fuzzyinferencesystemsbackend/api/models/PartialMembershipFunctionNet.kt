package com.mianeko.fuzzyinferencesystemsbackend.api.models

import com.mianeko.fuzzyinferencesystemsbackend.DTO.MembershipFunctionDTO
import com.mianeko.fuzzyinferencesystemsbackend.DTO.PartialMembershipFunctionDTO


data class PartialMembershipFunctionNet(
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

    companion object {
        fun fromDTO(membershipFunction: MembershipFunctionDTO) =
            PartialMembershipFunctionNet(
                id = membershipFunction.id,
                term = membershipFunction.term,
                variableId = membershipFunction.variable?.id
            )

        fun fromPartialDTO(membershipFunction: PartialMembershipFunctionDTO) =
            PartialMembershipFunctionNet(
                id = membershipFunction.id,
                term = membershipFunction.term,
                variableId = membershipFunction.variableId
            )
    }
}
