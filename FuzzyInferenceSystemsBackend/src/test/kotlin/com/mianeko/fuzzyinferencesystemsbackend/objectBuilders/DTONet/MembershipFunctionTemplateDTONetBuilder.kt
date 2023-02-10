package com.mianeko.fuzzyinferencesystemsbackend.objectBuilders.DTONet

import com.mianeko.fuzzyinferencesystemsbackend.DTODb.enums.LinguisticHedgeTypeDTONet
import com.mianeko.fuzzyinferencesystemsbackend.DTODb.enums.MembershipFunctionTypeDTONet
import com.mianeko.fuzzyinferencesystemsbackend.DTONet.MembershipFunctionTemplateDTONet

class MembershipFunctionTemplateDTONetBuilder  private constructor(
    private var id: Int = defaultId,
    private var term: String? = defaultTerm,
    private var functionType: MembershipFunctionTypeDTONet = defaultFunctionType,
    private var variableId: Int? = defaultVariableId,
    private var parameter1: Double? = defaultParameter1,
    private var parameter2: Double? = defaultParameter2,
    private var parameter3: Double? = defaultParameter3,
    private var parameter4: Double? = defaultParameter4,
    private var value: Double? = defaultValue,
    private var parentId: Int? = defaultParentId,
    private var hedgeType: LinguisticHedgeTypeDTONet = defaultHedgeType,
    private var systemId: Int = defaultSystemId
) {
    fun build() =
        MembershipFunctionTemplateDTONet(
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

    fun withId(id: Int): MembershipFunctionTemplateDTONetBuilder {
        this.id = id
        return this
    }

    fun withTerm(term: String?): MembershipFunctionTemplateDTONetBuilder {
        this.term = term
        return this
    }

    fun withFunctionType(
        functionType: MembershipFunctionTypeDTONet,
        parameter1: Double?,
        parameter2: Double,
        parameter3: Double?,
        parameter4: Double?,
        parent: Int?,
        hedgeType: LinguisticHedgeTypeDTONet
    ): MembershipFunctionTemplateDTONetBuilder {
        this.functionType = functionType
        this.parameter1 = parameter1
        this.parameter2 = parameter2
        this.parameter3 = parameter3
        this.parameter4 = parameter4
        this.parentId = parent
        this.hedgeType = hedgeType
        return this
    }

    fun withVariable(variable: Int): MembershipFunctionTemplateDTONetBuilder {
        this.variableId = variable
        return this
    }

    fun withValue(value: Double?): MembershipFunctionTemplateDTONetBuilder {
        this.value = value
        return this
    }

    companion object {
        val defaultId: Int = 1
        val defaultTerm: String = "Term"
        val defaultFunctionType: MembershipFunctionTypeDTONet = MembershipFunctionTypeDTONet.Gauss
        val defaultVariableId: Int? = 1
        val defaultParameter1: Double = 1.5
        val defaultParameter2: Double = 100.2
        val defaultParameter3: Double? = null
        val defaultParameter4: Double? = null
        val defaultValue: Double? = null
        val defaultParentId: Int? = null
        val defaultHedgeType: LinguisticHedgeTypeDTONet = LinguisticHedgeTypeDTONet.Nothing
        val defaultSystemId: Int = 1

        fun nMembershipFunctionTemplateDTONet() =
            MembershipFunctionTemplateDTONetBuilder()
    }
}