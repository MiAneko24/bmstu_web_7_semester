package com.mianeko.fuzzyinferencesystemsbackend.database.entities

import com.mianeko.fuzzyinferencesystemsbackend.services.models.OutputResult
import javax.persistence.Column
import javax.persistence.Entity

@Entity
data class DBOutputResult(
    @Column(name = "var_name")
    val variableName: String?,
    val value: Double?
) {
    fun toModel() = OutputResult(
        name = this.variableName,
        value = this.value
    )
}
