package com.mianeko.fuzzyinferencesystemsbackend.database.entities

import com.mianeko.fuzzyinferencesystemsbackend.services.models.SystemTemplate
import com.mianeko.fuzzyinferencesystemsbackend.services.models.enums.FuzzySystemType
import com.mianeko.fuzzyinferencesystemsbackend.services.models.enums.SpecializationType
import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.Id
import javax.persistence.Table
import com.mianeko.fuzzyinferencesystemsbackend.services.models.InferenceSystem as Fis

@Entity
@Table(name = "system", schema = "public")
data class DBSystem(
    @Id
    @Column(name = "s_id")
    val id: Int,

    @Column(name = "s_name")
    val name: String,

    @Column(name = "s_type")
    val type: String,

    @Column(name = "specialization")
    val specializationType: String
) {
    fun toModel() = Fis (
        id = this.id,
        name = this.name,
        type = FuzzySystemType.fromString(this.type),
        specializationType = SpecializationType.fromString(this.specializationType)
    )

    companion object {
        fun fromModel(system: Fis) = DBSystem(
            id = system.id,
            name = system.name,
            type = system.type.toString(),
            specializationType = system.specializationType.toString()
        )
    }
}

@Entity
@Table(name = "system", schema = "public")
data class DBInsertableSystem(
    @Id
    @Column(name = "s_id")
    val id: Int,

    @Column(name = "s_name")
    val name: String,

    @Column(name = "s_type")
    val type: String,

    @Column(name = "specialization")
    val specializationType: String
) {
    companion object {
        fun fromModel(system: SystemTemplate) = DBInsertableSystem(
            id = system.id,
            name = system.name,
            type = system.type.toString(),
            specializationType = system.specializationType.toString()
        )
    }
}