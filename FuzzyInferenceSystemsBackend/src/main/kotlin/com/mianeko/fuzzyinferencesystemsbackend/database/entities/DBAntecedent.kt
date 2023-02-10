package com.mianeko.fuzzyinferencesystemsbackend.database.entities

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
)


@Entity
@Table(name = "antecedent", schema = "public")
data class DBInsertableAntecedent (
    @Id
    @Column(name = "a_id")
    val id: Int,

    @Column(name = "m_id")
    val membershipFunction: Int,
)