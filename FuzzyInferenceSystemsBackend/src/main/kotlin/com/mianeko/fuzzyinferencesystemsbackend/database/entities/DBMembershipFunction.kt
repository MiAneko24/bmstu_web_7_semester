package com.mianeko.fuzzyinferencesystemsbackend.database.entities

import javax.persistence.*

@Entity
@Table(name = "membership_function", schema = "public")
data class DBMembershipFunction(
    @Id
    @Column(name = "m_id")
    val id: Int,
    val term: String?,

    @Column(name = "m_type")
    val functionType: String,

    @ManyToOne(optional = false)
    @JoinColumn(name = "v_id", referencedColumnName = "v_id")
    val variable: DBVariable?,

    val parameter1: Double?,
    val parameter2: Double?,
    val parameter3: Double?,
    val parameter4: Double?,

    @Column(name = "m_value")
    val value: Double?,

    @ManyToOne
    @JoinColumn(name = "p_id", referencedColumnName = "m_id")
    val parent: DBMembershipFunction?,

    @Column(name = "barrier")
    val hedgeType: String?
)

@Entity
@Table(name = "membership_function", schema = "public")
data class DBInsertableMembershipFunction(
    @Id
    @Column(name = "m_id")
    val id: Int,
    val term: String?,

    @Column(name = "m_type")
    val functionType: String,

    @Column(name="v_id")
    val variable: Int?,

    val parameter1: Double?,
    val parameter2: Double?,
    val parameter3: Double?,
    val parameter4: Double?,

    @Column(name = "m_value")
    val value: Double?,

    @Column(name = "p_id")
    val parentId: Int?,

    @Column(name = "barrier")
    val hedgeType: String?
)

