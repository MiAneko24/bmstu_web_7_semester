package com.mianeko.fuzzyinferencesystemsbackend.objectBuilders.DTONet

import com.mianeko.fuzzyinferencesystemsbackend.DTODb.enums.LinguisticHedgeTypeDTONet
import com.mianeko.fuzzyinferencesystemsbackend.DTODb.enums.MembershipFunctionTypeDTONet
import com.mianeko.fuzzyinferencesystemsbackend.DTONet.MembershipFunctionDTONet
import com.mianeko.fuzzyinferencesystemsbackend.DTONet.PartialMembershipFunctionDTONet
import com.mianeko.fuzzyinferencesystemsbackend.DTONet.PartialVariableDTONet

class MembershipFunctionDTONetBuilder private constructor(
    private var id: Int = defaultId,
    private var term: String? = defaultTerm,
    private var functionType: MembershipFunctionTypeDTONet = defaultFunctionType,
    private var variable: PartialVariableDTONet? = defaultVariable,
    private var parameter1: Double? = defaultParameter1,
    private var parameter2: Double? = defaultParameter2,
    private var parameter3: Double? = defaultParameter3,
    private var parameter4: Double? = defaultParameter4,
    private var value: Double? = defaultValue,
    private var parent: PartialMembershipFunctionDTONet? = defaultParent,
    private var hedgeType: LinguisticHedgeTypeDTONet = defaultHedgeType,
    private var systemId: Int = defaultSystemId
) {
    fun build() =
        MembershipFunctionDTONet(
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
            hedgeType = hedgeType,
            systemId = systemId
        )

    fun buildPartial() =
        PartialMembershipFunctionDTONet(
            id = id,
            term = term,
            systemId = systemId,
            variableId = variable?.id
        )

    fun withId(id: Int): MembershipFunctionDTONetBuilder {
        this.id = id
        return this
    }

    fun withTerm(term: String?): MembershipFunctionDTONetBuilder {
        this.term = term
        return this
    }

    fun withFunctionType(
        functionType: MembershipFunctionTypeDTONet,
        parameter1: Double?,
        parameter2: Double,
        parameter3: Double?,
        parameter4: Double?,
        parent: PartialMembershipFunctionDTONet?,
        hedgeType: LinguisticHedgeTypeDTONet
    ): MembershipFunctionDTONetBuilder {
        this.functionType = functionType
        this.parameter1 = parameter1
        this.parameter2 = parameter2
        this.parameter3 = parameter3
        this.parameter4 = parameter4
        this.parent = parent
        this.hedgeType = hedgeType
        return this
    }

    fun withVariable(variable: PartialVariableDTONet): MembershipFunctionDTONetBuilder {
        this.variable = variable
        return this
    }

    fun withValue(value: Double?): MembershipFunctionDTONetBuilder {
        this.value = value
        return this
    }

    companion object {
        val defaultId: Int = 1
        val defaultTerm: String = "Term"
        val defaultFunctionType: MembershipFunctionTypeDTONet = MembershipFunctionTypeDTONet.Gauss
        val defaultVariable: PartialVariableDTONet? = VariableDTONetBuilder.nVariableDTONet().buildPartial()
        val defaultParameter1: Double = 1.5
        val defaultParameter2: Double = 100.2
        val defaultParameter3: Double? = null
        val defaultParameter4: Double? = null
        val defaultValue: Double? = null
        val defaultParent: PartialMembershipFunctionDTONet? = null
        val defaultHedgeType: LinguisticHedgeTypeDTONet = LinguisticHedgeTypeDTONet.Nothing
        val defaultSystemId: Int = 1

        fun nMembershipFunctionDTONet() =
            MembershipFunctionDTONetBuilder()
    }
}