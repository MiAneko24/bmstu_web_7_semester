package com.mianeko.fuzzyinferencesystemsbackend.database.repositories.objectBuilders.db

import com.mianeko.fuzzyinferencesystemsbackend.database.entities.DBJob
import java.util.*

class DBJobBuilder private constructor(
    private var id: UUID = defaultId,
    private var inputVariables: Map<Int, Double> = defaultVariables,
    private var systemId: Int = defaultSystemId
) {
    fun build() = DBJob(
        id = this.id,
        inputVariables = this.inputVariables,
        systemId = this.systemId
    )

    fun withId(id: UUID): DBJobBuilder {
        this.id = id
        return this
    }

    fun withInputVariables(inputVariables: Map<Int, Double>): DBJobBuilder {
        this.inputVariables = inputVariables
        return this
    }

    fun withSystemId(systemId: Int): DBJobBuilder {
        this.systemId = systemId
        return this
    }

    companion object {
        val defaultId = UUID.fromString("956b60b0-49eb-4beb-9f73-53a20960f4d5")
        val defaultVariables: Map<Int, Double> = mapOf(
            1 to 5.0
        )
        val defaultSystemId = 1

        fun nDBJob() = DBJobBuilder()
    }
}