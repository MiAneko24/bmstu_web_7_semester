package com.mianeko.fuzzyinferencesystemsbackend.database.repositories.objectBuilders.db

import com.mianeko.fuzzyinferencesystemsbackend.database.entities.DBAntecedent
import com.mianeko.fuzzyinferencesystemsbackend.database.entities.DBMembershipFunction

class DBAntecedentBuilder private constructor(
    private var id: Int = defaultId,
    private var memberhshipFunction: DBMembershipFunction = defaultMembershipFunction
) {
    fun build() =
        DBAntecedent(
            id = id,
            membershipFunction = memberhshipFunction
        )

    fun withId(id: Int): DBAntecedentBuilder {
        this.id = id
        return this
    }

    fun withMembershipFunction(dbMembershipFunction: DBMembershipFunction): DBAntecedentBuilder {
        this.memberhshipFunction = dbMembershipFunction
        return this
    }

    companion object {
        val defaultId = 1
        val defaultMembershipFunction =
            DBMembershipFunctionBuilder.nDBMembershipFunction()
                .build()

        fun nDBAntecedent() = DBAntecedentBuilder()
    }
}
