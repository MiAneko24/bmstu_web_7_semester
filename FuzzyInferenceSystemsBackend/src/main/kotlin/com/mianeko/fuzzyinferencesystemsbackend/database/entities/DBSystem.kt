package com.mianeko.fuzzyinferencesystemsbackend.database.entities

import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.Id
import javax.persistence.Table

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
)

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
)