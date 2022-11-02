package com.mianeko.fuzzyinferencesystemsbackend.api.models

import com.mianeko.fuzzyinferencesystemsbackend.DTO.PartialVariableDTO
import com.mianeko.fuzzyinferencesystemsbackend.DTO.VariableDTO


data class PartialVariableNet(
    val id: Int,
    val name: String
) {
    fun toDTO(systemId: Int) = PartialVariableDTO(
        id = this.id,
        name = name,
        systemId = systemId
    )

    companion object {
        fun fromDTO(variable: VariableDTO) = PartialVariableNet(
            id = variable.id,
            name = variable.name
        )

        fun fromPartialDTO(variable: PartialVariableDTO) = PartialVariableNet(
            id = variable.id,
            name = variable.name
        )
    }
}
