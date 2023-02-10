package com.mianeko.fuzzyinferencesystemsbackend.database.repositories.objectBuilders

import com.mianeko.fuzzyinferencesystemsbackend.services.models.MembershipFunction
import com.mianeko.fuzzyinferencesystemsbackend.services.models.Variable
import com.mianeko.fuzzyinferencesystemsbackend.services.models.enums.LinguisticHedgeType
import com.mianeko.fuzzyinferencesystemsbackend.services.models.enums.MembershipFunctionType

class MembershipFunctionBuilder private constructor(
    private var id: Int = defaultId,
    private var term: String? = defaultTerm,
    private var functionType: MembershipFunctionType = defaultFunctionType,
    private var variable: Variable? = defaultVariable,
    private var parameter1: Double? = defaultParameter1,
    private var parameter2: Double? = defaultParameter2,
    private var parameter3: Double? = defaultParameter3,
    private var parameter4: Double? = defaultParameter4,
    private var value: Double? = defaultValue,
    private var parent: MembershipFunction? = defaultParent,
    private var hedgeType: LinguisticHedgeType = defaultHedgeType
) {
    fun build() =
        MembershipFunction(
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

    fun withId(id: Int): MembershipFunctionBuilder {
        this.id = id
        return this
    }

    fun withTerm(term: String?): MembershipFunctionBuilder {
        this.term = term
        return this
    }

    fun withFunctionType(
        functionType: MembershipFunctionType,
        parameter1: Double?,
        parameter2: Double?,
        parameter3: Double?,
        parameter4: Double?,
        parent: MembershipFunction?,
        hedgeType: LinguisticHedgeType
    ): MembershipFunctionBuilder {
        this.functionType = functionType
        this.parameter1 = parameter1
        this.parameter2 = parameter2
        this.parameter3 = parameter3
        this.parameter4 = parameter4
        this.parent = parent
        this.hedgeType = hedgeType
        return this
    }

    fun withVariable(variable: Variable?): MembershipFunctionBuilder {
        this.variable = variable
        return this
    }

    fun withValue(value: Double?): MembershipFunctionBuilder {
        this.value = value
        return this
    }

    companion object {
        val defaultId: Int = 1
        val defaultTerm: String = "Term"
        val defaultFunctionType: MembershipFunctionType = MembershipFunctionType.Gauss
        val defaultVariable: Variable? = VariableBuilder.nVariable().build()
        val defaultParameter1: Double = 1.5
        val defaultParameter2: Double = 100.2
        val defaultParameter3: Double? = null
        val defaultParameter4: Double? = null
        val defaultValue: Double? = null
        val defaultParent: MembershipFunction? = null
        val defaultHedgeType: LinguisticHedgeType = LinguisticHedgeType.Nothing

        fun nMembershipFunction() =
            MembershipFunctionBuilder()
    }
}