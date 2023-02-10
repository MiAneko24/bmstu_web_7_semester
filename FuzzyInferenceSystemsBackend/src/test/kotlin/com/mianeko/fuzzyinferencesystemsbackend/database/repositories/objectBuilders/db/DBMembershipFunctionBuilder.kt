package com.mianeko.fuzzyinferencesystemsbackend.database.repositories.objectBuilders.db

import com.mianeko.fuzzyinferencesystemsbackend.database.entities.DBMembershipFunction
import com.mianeko.fuzzyinferencesystemsbackend.database.entities.DBVariable
import com.mianeko.fuzzyinferencesystemsbackend.database.entities.enums.DBLinguisticHedgeType
import com.mianeko.fuzzyinferencesystemsbackend.database.entities.enums.DBMembershipFunctionType

class DBMembershipFunctionBuilder private constructor(
    private var id: Int = defaultId,
    private var term: String? = defaultTerm,
    private var functionType: String = defaultFunctionType,
    private var variable: DBVariable? = defaultVariable,
    private var parameter1: Double? = defaultParameter1,
    private var parameter2: Double? = defaultParameter2,
    private var parameter3: Double? = defaultParameter3,
    private var parameter4: Double? = defaultParameter4,
    private var value: Double? = defaultValue,
    private var parent: DBMembershipFunction? = defaultParent,
    private var hedgeType: String? = defaultHedgeType
) {
    fun build() =
        DBMembershipFunction(
            id = id,
            term = term,
            functionType = functionType,
            variable = variable,
            parameter1 = parameter1,
            parameter2 = parameter2,
            parameter3 = parameter3,
            parameter4 = parameter4,
            value = value,
            parent = parent,
            hedgeType = hedgeType
        )

    fun withId(id: Int): DBMembershipFunctionBuilder {
        this.id = id
        return this
    }

    fun withTerm(term: String?): DBMembershipFunctionBuilder {
        this.term = term
        return this
    }

    fun withFunctionType(
        functionType: DBMembershipFunctionType,
        parameter1: Double?,
        parameter2: Double,
        parameter3: Double?,
        parameter4: Double?,
        parent: DBMembershipFunction?,
        hedgeType: DBLinguisticHedgeType
    ): DBMembershipFunctionBuilder {
        this.functionType = functionType.text
        this.parameter1 = parameter1
        this.parameter2 = parameter2
        this.parameter3 = parameter3
        this.parameter4 = parameter4
        this.parent = parent
        this.hedgeType = hedgeType.text
        return this
    }

    fun withVariable(dbVariable: DBVariable): DBMembershipFunctionBuilder {
        this.variable = dbVariable
        return this
    }

    fun withValue(value: Double?): DBMembershipFunctionBuilder {
        this.value = value
        return this
    }

    companion object {
        val defaultId: Int = 1
        val defaultTerm: String = "Term"
        val defaultFunctionType: String = "gauss"
        val defaultVariable: DBVariable? = DBVariableBuilder.nDBVariable().build()
        val defaultParameter1: Double = 1.5
        val defaultParameter2: Double = 100.2
        val defaultParameter3: Double? = null
        val defaultParameter4: Double? = null
        val defaultValue: Double? = null
        val defaultParent: DBMembershipFunction? = null
        val defaultHedgeType: String? = null

        fun nDBMembershipFunction() =
            DBMembershipFunctionBuilder()
    }
}