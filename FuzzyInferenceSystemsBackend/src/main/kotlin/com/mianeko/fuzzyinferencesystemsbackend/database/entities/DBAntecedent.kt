package com.mianeko.fuzzyinferencesystemsbackend.database.entities

import com.mianeko.fuzzyinferencesystemsbackend.services.models.Antecedent
import com.mianeko.fuzzyinferencesystemsbackend.services.models.AntecedentTemplate
import javax.persistence.*

@Entity
@Table(name = "antecedent", schema = "public")
data class DBAntecedent (
    @Id
    @Column(name = "a_id")
    val id: Int,

    @ManyToOne
    @JoinColumn(name = "m_id", referencedColumnName = "m_id")
    val membershipFunction: DBMembershipFunction,
) {
    fun toModel(): Antecedent = Antecedent(
        id = this.id,
        membershipFunction = this.membershipFunction.toModel()
    )
}


@Entity
@Table(name = "antecedent", schema = "public")
data class DBInsertableAntecedent (
    @Id
    @Column(name = "a_id")
    val id: Int,

    @Column(name = "m_id")
    val membershipFunction: Int,
) {
    companion object {
        fun fromModel(antecedent: AntecedentTemplate) = DBInsertableAntecedent(
            id = antecedent.id,
            membershipFunction = antecedent.membershipFunctionId
        )
    }
}