package com.mianeko.fuzzyinferencesystemsbackend.database.entities

import io.ebean.annotation.DbJsonB
import java.util.*
import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.Id
import javax.persistence.Table

@Entity
@Table(name = "job", schema = "public")
data class DBJob(
    @Id
    @Column(name = "j_id")
    val id: UUID,

    @DbJsonB
    @Column(name = "input_variables")
    val inputVariables: Map<Int, Double>,

    @Column(name = "s_id")
    val systemId: Int
)


@Entity
@Table(name = "job", schema = "public")
data class DBInsertableJob(
    @Id
    @Column(name = "j_id")
    val id: UUID,

    @DbJsonB
    @Column(name = "input_variables")
    val inputVariables: Map<Int, Double>,

    @Column(name = "s_id")
    val systemId: Int
)

