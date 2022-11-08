package com.mianeko.fuzzyinferencesystemsbackend.database.entities

import javax.persistence.*

@Entity
@Table(name="consequent", schema="public")
data class DBConsequent(
    @Id
    @Column(name = "c_id")
    val id: Int,

    @ManyToOne
    @JoinColumn(name = "m_id", referencedColumnName = "m_id")
    val membershipFunction: DBMembershipFunction,

    @ManyToOne
    @JoinColumn(name = "r_id", referencedColumnName = "r_id")
    val rule: DBRule,

    @ManyToOne
    @JoinColumn(name = "v_id", referencedColumnName = "v_id")
    val variable: DBVariable?
)

@Entity
@Table(name="consequent", schema="public")
data class DBInsertableConsequent(
    @Id
    @Column(name = "c_id")
    val id: Int,

    @Column(name = "m_id")
    val membershipFunctionId: Int,

    @Column(name = "r_id")
    val ruleId: Int,

    @Column(name = "v_id")
    val variableId: Int?

)