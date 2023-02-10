package com.mianeko.fuzzyinferencesystemsbackend.database.repositories.objectBuilders.db

import com.mianeko.fuzzyinferencesystemsbackend.database.entities.DBConsequent
import com.mianeko.fuzzyinferencesystemsbackend.database.entities.DBMembershipFunction
import com.mianeko.fuzzyinferencesystemsbackend.database.entities.DBRule
import com.mianeko.fuzzyinferencesystemsbackend.database.entities.DBVariable

class DBConsequentBuilder private constructor(
    private var id: Int = defaultId,
    private var membershipFunction: DBMembershipFunction = defaultMembershipFunction,
    private var rule: DBRule = defaultRule,
    private var variable: DBVariable? = defaultVariable
){
    fun build() =
        DBConsequent(
            id = this.id,
            membershipFunction = this.membershipFunction,
            rule = this.rule,
            variable = this.variable
        )

    fun withId(id: Int): DBConsequentBuilder {
        this.id = id
        return this
    }

    fun withMembershipFunction(dbMembershipFunction: DBMembershipFunction): DBConsequentBuilder {
        this.membershipFunction = dbMembershipFunction
        return this
    }

    fun withRule(rule: DBRule): DBConsequentBuilder {
        this.rule = rule
        return this
    }

    fun withVariable(variable: DBVariable?): DBConsequentBuilder {
        this.variable = variable
        return this
    }

    companion object {
        val defaultId: Int = 1
        val defaultMembershipFunction: DBMembershipFunction =
            DBMembershipFunctionBuilder.nDBMembershipFunction()
                .build()
        val defaultRule: DBRule = DBRule(1, DBSystemBuilder.nDBSystem().build(), "and", 1.0, listOf(), listOf())
        val defaultVariable: DBVariable? = null

        fun nDBConsequent() = DBConsequentBuilder()
    }

}