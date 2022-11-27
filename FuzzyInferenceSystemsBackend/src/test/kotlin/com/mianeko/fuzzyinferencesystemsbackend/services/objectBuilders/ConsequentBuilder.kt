package com.mianeko.fuzzyinferencesystemsbackend.database.repositories.objectBuilders

import com.mianeko.fuzzyinferencesystemsbackend.database.entities.DBConsequent
import com.mianeko.fuzzyinferencesystemsbackend.database.entities.DBMembershipFunction
import com.mianeko.fuzzyinferencesystemsbackend.database.entities.DBRule
import com.mianeko.fuzzyinferencesystemsbackend.database.entities.DBVariable
import com.mianeko.fuzzyinferencesystemsbackend.services.models.Consequent
import com.mianeko.fuzzyinferencesystemsbackend.services.models.MembershipFunction
import com.mianeko.fuzzyinferencesystemsbackend.services.models.Rule
import com.mianeko.fuzzyinferencesystemsbackend.services.models.Variable

class ConsequentBuilder private constructor(
    private var id: Int = defaultId,
    private var membershipFunction: MembershipFunction = defaultMembershipFunction,
    private var ruleId: Int = defaultRuleId,
    private var variable: Variable? = defaultVariable,
    private var systemId: Int = defaultSystemId
){
    fun build() =
        Consequent(
            id = this.id,
            membershipFunction = this.membershipFunction,
            ruleId = this.ruleId,
            variable = this.variable,
            systemId = this.systemId
        )

    fun withId(id: Int): ConsequentBuilder {
        this.id = id
        return this
    }

    fun withMembershipFunction(membershipFunction: MembershipFunction): ConsequentBuilder {
        this.membershipFunction = membershipFunction
        return this
    }

    fun withRuleId(ruleId: Int): ConsequentBuilder {
        this.ruleId = ruleId
        return this
    }

    fun withVariable(variable: Variable?): ConsequentBuilder {
        this.variable = variable
        return this
    }

    fun withSystemId(systemId: Int): ConsequentBuilder {
        this.systemId = systemId
        return this
    }

    companion object {
        val defaultId: Int = 1
        val defaultMembershipFunction: MembershipFunction =
            MembershipFunctionBuilder
                .nMembershipFunction()
                .build()
        val defaultRuleId: Int = 1
        val defaultVariable: Variable? = null
        val defaultSystemId: Int = 1

        fun nConsequent() = ConsequentBuilder()
    }
}