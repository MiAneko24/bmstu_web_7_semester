package com.mianeko.fuzzyinferencesystemsbackend.database.entities

import com.mianeko.fuzzyinferencesystemsbackend.services.models.Variable
import com.mianeko.fuzzyinferencesystemsbackend.services.models.VariableTemplate
import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.Id
import javax.persistence.JoinColumn
import javax.persistence.ManyToOne
import javax.persistence.Table

@Entity
@Table(name = "variable", schema = "public")
data class DBVariable(
    @Id
    @Column(name = "v_id")
    val id: Int,

    @Column(name = "v_name")
    val name: String,

    @Column(name = "min_value")
    val minValue: Double,

    @Column(name = "max_value")
    val maxValue: Double,

    @Column(name = "v_value")
    val value: Double?,

    @ManyToOne
    @JoinColumn(name = "s_id", referencedColumnName = "s_id")
    val system: DBSystem
) {
    fun toModel() = Variable(
        id = this.id,
        name = this.name,
        minValue = this.minValue,
        maxValue = this.maxValue,
        value = this.value,
        inferenceSystem = this.system.toModel()
    )

    companion object {
        fun fromModel(variable: Variable) = DBVariable(
            id = variable.id,
            name = variable.name,
            minValue = variable.minValue,
            maxValue = variable.maxValue,
            value = variable.value,
            system = DBSystem.fromModel(variable.inferenceSystem)
        )
    }
}

@Entity
@Table(name = "variable", schema = "public")
data class DBInsertableVariable(
    @Id
    @Column(name = "v_id")
    val id: Int,

    @Column(name = "v_name")
    val name: String,

    @Column(name = "min_value")
    val minValue: Double,

    @Column(name = "max_value")
    val maxValue: Double,

    @Column(name = "v_value")
    val value: Double?,

    @Column(name = "s_id")
    val systemId: Int
) {
    companion object {
        fun fromModel(variable: VariableTemplate) = DBInsertableVariable(
            id = variable.id,
            name = variable.name,
            minValue = variable.minValue,
            maxValue = variable.maxValue,
            value = variable.value,
            systemId = variable.systemId
        )
    }
}