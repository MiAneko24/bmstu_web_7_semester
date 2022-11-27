package com.mianeko.fuzzyinferencesystemsbackend.objectBuilders.DTODb

import com.mianeko.fuzzyinferencesystemsbackend.DTODb.AntecedentDTODb
import com.mianeko.fuzzyinferencesystemsbackend.DTODb.MembershipFunctionDTODb
import com.mianeko.fuzzyinferencesystemsbackend.database.entities.DBAntecedent
import com.mianeko.fuzzyinferencesystemsbackend.database.entities.DBMembershipFunction
import com.mianeko.fuzzyinferencesystemsbackend.database.repositories.objectBuilders.db.DBMembershipFunctionBuilder

class AntecedentDTODbBuilder private constructor(
    private var id: Int = defaultId,
    private var memberhshipFunction: MembershipFunctionDTODb = defaultMembershipFunction
) {
    fun build() =
        AntecedentDTODb(
            id = id,
            membershipFunction = memberhshipFunction
        )

    fun withId(id: Int): AntecedentDTODbBuilder {
        this.id = id
        return this
    }

    fun withMembershipFunction(dbMembershipFunction: MembershipFunctionDTODb): AntecedentDTODbBuilder {
        this.memberhshipFunction = dbMembershipFunction
        return this
    }

    companion object {
        val defaultId = 1
        val defaultMembershipFunction =
            MembershipFunctionDTODbBuilder.nMembershipFunctionDTODb()
                .build()

        fun nAntecedentDTODb() = AntecedentDTODbBuilder()
    }
}