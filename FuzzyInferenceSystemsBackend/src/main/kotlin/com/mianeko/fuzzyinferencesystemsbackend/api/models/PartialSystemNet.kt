package com.mianeko.fuzzyinferencesystemsbackend.api.models

import com.mianeko.fuzzyinferencesystemsbackend.DTO.PartialSystemDTO
import com.mianeko.fuzzyinferencesystemsbackend.DTO.SystemDTO


data class PartialSystemNet(
    val id: Int,
    val name: String
) {
    fun toDTO() = PartialSystemDTO(
        id = id,
        name = name
    )

    companion object {
        fun fromDTO(system: SystemDTO) = PartialSystemNet(
            id = system.id,
            name = system.name
        )
    }
}
