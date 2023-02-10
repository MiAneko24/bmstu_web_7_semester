package com.mianeko.fuzzyinferencesystemsbackend.DTONet

import com.mianeko.fuzzyinferencesystemsbackend.api.models.OutputResultNet
import com.mianeko.fuzzyinferencesystemsbackend.services.models.OutputResult

data class OutputResultDTONet(
    val name: String?,
    val value: Double?
) {
    fun toModelNet() = OutputResultNet(
        name = this.name,
        value = this.value
    )

    companion object {
        fun fromModel(outputResult: OutputResult) = OutputResultDTONet(
            name = outputResult.name,
            value = outputResult.value
        )
    }
}
