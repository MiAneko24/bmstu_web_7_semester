package com.mianeko.fuzzyinferencesystemsbackend.objectBuilders.DTODb

import com.mianeko.fuzzyinferencesystemsbackend.DTODb.ConsequentDTODb
import com.mianeko.fuzzyinferencesystemsbackend.DTODb.MembershipFunctionDTODb
import com.mianeko.fuzzyinferencesystemsbackend.DTODb.RuleDTODb
import com.mianeko.fuzzyinferencesystemsbackend.DTODb.VariableDTODb
import com.mianeko.fuzzyinferencesystemsbackend.database.entities.DBConsequent
import com.mianeko.fuzzyinferencesystemsbackend.database.entities.DBMembershipFunction
import com.mianeko.fuzzyinferencesystemsbackend.database.entities.DBRule
import com.mianeko.fuzzyinferencesystemsbackend.database.entities.DBVariable
import com.mianeko.fuzzyinferencesystemsbackend.database.repositories.objectBuilders.db.DBConsequentBuilder
import com.mianeko.fuzzyinferencesystemsbackend.database.repositories.objectBuilders.db.DBMembershipFunctionBuilder
import com.mianeko.fuzzyinferencesystemsbackend.database.repositories.objectBuilders.db.DBRuleBuilder

class ConsequentDTODbBuilder private constructor(
    private var id: Int = defaultId,
    private var membershipFunction: MembershipFunctionDTODb = defaultMembershipFunction,
    private var ruleId: Int = defaultRuleId,
    private var variable: VariableDTODb? = defaultVariable,
    private var systemId: Int = defaultSystemId
){
    fun build() =
        ConsequentDTODb(
            id = this.id,
            membershipFunction = this.membershipFunction,
            ruleId = this.ruleId,
            variable = this.variable,
            systemId = this.systemId
        )

    fun withId(id: Int): ConsequentDTODbBuilder {
        this.id = id
        return this
    }

    fun withMembershipFunction(dbMembershipFunction: MembershipFunctionDTODb): ConsequentDTODbBuilder {
        this.membershipFunction = dbMembershipFunction
        return this
    }

    fun withRuleId(ruleId: Int): ConsequentDTODbBuilder {
        this.ruleId = ruleId
        return this
    }

    fun withVariable(variable: VariableDTODb?): ConsequentDTODbBuilder {
        this.variable = variable
        return this
    }

    companion object {
        val defaultId: Int = 1
        val defaultMembershipFunction: MembershipFunctionDTODb =
            MembershipFunctionDTODbBuilder.nMembershipFunctionDTODb()
                .build()
        val defaultRuleId: Int = 1
        val defaultVariable: VariableDTODb? = null
        val defaultSystemId = 1

        fun nConsequentDTODb() = ConsequentDTODbBuilder()
    }
}