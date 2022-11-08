package com.mianeko.fuzzyinferencesystemsbackend.database.entities

import javax.persistence.*

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
)

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
)