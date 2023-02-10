package com.mianeko.fuzzyinferencesystemsbackend.DTODb

import com.mianeko.fuzzyinferencesystemsbackend.database.entities.DBOutputResult
import com.mianeko.fuzzyinferencesystemsbackend.services.models.OutputResult

data class OutputResultDTODb(
    val name: String?,
    val value: Double?
) {
    fun toModel() = OutputResult(
        name = this.name,
        value = this.value
    )

    companion object {
        fun fromModelDb(dbOutputResult: DBOutputResult) = OutputResultDTODb(
            name = dbOutputResult.variableName,
            value = dbOutputResult.value
        )
    }
}
