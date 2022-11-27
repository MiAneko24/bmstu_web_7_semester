package com.mianeko.fuzzyinferencesystemsbackend.objectBuilders.DTODb

import com.mianeko.fuzzyinferencesystemsbackend.DTODb.MembershipFunctionTemplateDTODb
import com.mianeko.fuzzyinferencesystemsbackend.DTODb.enums.LinguisticHedgeTypeDTODb
import com.mianeko.fuzzyinferencesystemsbackend.DTODb.enums.MembershipFunctionTypeDTODb

class MembershipFunctionTemplateDTODbBuilder  private constructor(
    private var id: Int = defaultId,
    private var term: String? = defaultTerm,
    private var functionType: MembershipFunctionTypeDTODb = defaultFunctionType,
    private var variableId: Int? = defaultVariableId,
    private var parameter1: Double? = defaultParameter1,
    private var parameter2: Double? = defaultParameter2,
    private var parameter3: Double? = defaultParameter3,
    private var parameter4: Double? = defaultParameter4,
    private var value: Double? = defaultValue,
    private var parentId: Int? = defaultParentId,
    private var hedgeType: LinguisticHedgeTypeDTODb = defaultHedgeType,
    private var systemId: Int = defaultSystemId
) {
    fun build() =
        MembershipFunctionTemplateDTODb(
            id = id,
            term = term,
            functionType = functionType,
            variableId = variableId,
            parameter1 = parameter1,
            parameter2 = parameter2,
            parameter3 = parameter3,
            parameter4 = parameter4,
            value = value,
            parentId = parentId,
            hedgeType = hedgeType,
            systemId = systemId
        )

    fun withId(id: Int): MembershipFunctionTemplateDTODbBuilder {
        this.id = id
        return this
    }

    fun withTerm(term: String?): MembershipFunctionTemplateDTODbBuilder {
        this.term = term
        return this
    }

    fun withFunctionType(
        functionType: MembershipFunctionTypeDTODb,
        parameter1: Double?,
        parameter2: Double,
        parameter3: Double?,
        parameter4: Double?,
        parent: Int?,
        hedgeType: LinguisticHedgeTypeDTODb
    ): MembershipFunctionTemplateDTODbBuilder {
        this.functionType = functionType
        this.parameter1 = parameter1
        this.parameter2 = parameter2
        this.parameter3 = parameter3
        this.parameter4 = parameter4
        this.parentId = parent
        this.hedgeType = hedgeType
        return this
    }

    fun withVariable(variable: Int): MembershipFunctionTemplateDTODbBuilder {
        this.variableId = variable
        return this
    }

    fun withValue(value: Double?): MembershipFunctionTemplateDTODbBuilder {
        this.value = value
        return this
    }

    companion object {
        val defaultId: Int = 1
        val defaultTerm: String = "Term"
        val defaultFunctionType: MembershipFunctionTypeDTODb = MembershipFunctionTypeDTODb.Gauss
        val defaultVariableId: Int? = 1
        val defaultParameter1: Double = 1.5
        val defaultParameter2: Double = 100.2
        val defaultParameter3: Double? = null
        val defaultParameter4: Double? = null
        val defaultValue: Double? = null
        val defaultParentId: Int? = null
        val defaultHedgeType: LinguisticHedgeTypeDTODb = LinguisticHedgeTypeDTODb.Nothing
        val defaultSystemId: Int = 1

        fun nMembershipFunctionTemplateDTODb() =
            MembershipFunctionTemplateDTODbBuilder()
    }
}