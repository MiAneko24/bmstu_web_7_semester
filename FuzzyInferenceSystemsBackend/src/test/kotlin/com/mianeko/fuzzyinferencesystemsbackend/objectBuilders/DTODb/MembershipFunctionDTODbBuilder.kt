package com.mianeko.fuzzyinferencesystemsbackend.objectBuilders.DTODb

import com.mianeko.fuzzyinferencesystemsbackend.DTODb.MembershipFunctionDTODb
import com.mianeko.fuzzyinferencesystemsbackend.DTODb.VariableDTODb
import com.mianeko.fuzzyinferencesystemsbackend.DTODb.enums.LinguisticHedgeTypeDTODb
import com.mianeko.fuzzyinferencesystemsbackend.DTODb.enums.MembershipFunctionTypeDTODb
import com.mianeko.fuzzyinferencesystemsbackend.database.entities.DBMembershipFunction
import com.mianeko.fuzzyinferencesystemsbackend.database.entities.DBVariable
import com.mianeko.fuzzyinferencesystemsbackend.database.entities.enums.DBLinguisticHedgeType
import com.mianeko.fuzzyinferencesystemsbackend.database.entities.enums.DBMembershipFunctionType
import com.mianeko.fuzzyinferencesystemsbackend.database.repositories.objectBuilders.db.DBMembershipFunctionBuilder
import com.mianeko.fuzzyinferencesystemsbackend.database.repositories.objectBuilders.db.DBVariableBuilder

class MembershipFunctionDTODbBuilder private constructor(
    private var id: Int = defaultId,
    private var term: String? = defaultTerm,
    private var functionType: MembershipFunctionTypeDTODb = defaultFunctionType,
    private var variable: VariableDTODb? = defaultVariable,
    private var parameter1: Double? = defaultParameter1,
    private var parameter2: Double? = defaultParameter2,
    private var parameter3: Double? = defaultParameter3,
    private var parameter4: Double? = defaultParameter4,
    private var value: Double? = defaultValue,
    private var parent: MembershipFunctionDTODb? = defaultParent,
    private var hedgeType: LinguisticHedgeTypeDTODb = defaultHedgeType
) {
    fun build() =
        MembershipFunctionDTODb(
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

    fun withId(id: Int): MembershipFunctionDTODbBuilder {
        this.id = id
        return this
    }

    fun withTerm(term: String?): MembershipFunctionDTODbBuilder {
        this.term = term
        return this
    }

    fun withFunctionType(
        functionType: MembershipFunctionTypeDTODb,
        parameter1: Double?,
        parameter2: Double,
        parameter3: Double?,
        parameter4: Double?,
        parent: MembershipFunctionDTODb?,
        hedgeType: LinguisticHedgeTypeDTODb
    ): MembershipFunctionDTODbBuilder {
        this.functionType = functionType
        this.parameter1 = parameter1
        this.parameter2 = parameter2
        this.parameter3 = parameter3
        this.parameter4 = parameter4
        this.parent = parent
        this.hedgeType = hedgeType
        return this
    }

    fun withVariable(dbVariable: VariableDTODb): MembershipFunctionDTODbBuilder {
        this.variable = dbVariable
        return this
    }

    fun withValue(value: Double?): MembershipFunctionDTODbBuilder {
        this.value = value
        return this
    }

    companion object {
        val defaultId: Int = 1
        val defaultTerm: String = "Term"
        val defaultFunctionType: MembershipFunctionTypeDTODb = MembershipFunctionTypeDTODb.Gauss
        val defaultVariable: VariableDTODb? = VariableDTODbBuilder.nVariableDTODb().build()
        val defaultParameter1: Double = 1.5
        val defaultParameter2: Double = 100.2
        val defaultParameter3: Double? = null
        val defaultParameter4: Double? = null
        val defaultValue: Double? = null
        val defaultParent: MembershipFunctionDTODb? = null
        val defaultHedgeType: LinguisticHedgeTypeDTODb = LinguisticHedgeTypeDTODb.Nothing

        fun nMembershipFunctionDTODb() =
            MembershipFunctionDTODbBuilder()
    }
}