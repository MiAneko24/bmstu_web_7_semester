package com.mianeko.fuzzyinferencesystemsbackend.DTODb

import com.mianeko.fuzzyinferencesystemsbackend.DTODb.enums.LinguisticHedgeTypeDTODb
import com.mianeko.fuzzyinferencesystemsbackend.DTODb.enums.MembershipFunctionTypeDTODb
import com.mianeko.fuzzyinferencesystemsbackend.database.entities.DBInsertableMembershipFunction
import com.mianeko.fuzzyinferencesystemsbackend.database.entities.DBMembershipFunction
import com.mianeko.fuzzyinferencesystemsbackend.database.entities.enums.DBLinguisticHedgeType
import com.mianeko.fuzzyinferencesystemsbackend.database.entities.enums.DBMembershipFunctionType
import com.mianeko.fuzzyinferencesystemsbackend.services.models.MembershipFunction
import com.mianeko.fuzzyinferencesystemsbackend.services.models.MembershipFunctionTemplate


data class MembershipFunctionDTODb(
    val id: Int,
    val term: String?,
    val functionType: MembershipFunctionTypeDTODb,
    val variable: VariableDTODb?,
    val parameter1: Double?,
    val parameter2: Double?,
    val parameter3: Double?,
    val parameter4: Double?,
    val value: Double?,
    val parent: MembershipFunctionDTODb?,
    val hedgeType: LinguisticHedgeTypeDTODb
) {
    fun toModel(): MembershipFunction {
        return MembershipFunction(
            id = this.id,
            term = this.term,
            functionType = this.functionType.toMembershipFunctionType(),
            variable = this.variable?.toModel(),
            parameter1 = this.parameter1,
            parameter2 = this.parameter2,
            parameter3 = this.parameter3,
            parameter4 = this.parameter4,
            value = this.value,
            parent = this.parent?.toModel(),
            hedgeType = this.hedgeType.toLinguisticHedgeType()
        )
    }

    companion object {
        fun fromModelDb(dbMembershipFunction: DBMembershipFunction): MembershipFunctionDTODb {
            return MembershipFunctionDTODb(
                id = dbMembershipFunction.id,
                term = dbMembershipFunction.term,
                functionType = MembershipFunctionTypeDTODb.fromMembershipFunctionTypeDb(
                    DBMembershipFunctionType.fromString(dbMembershipFunction.functionType)
                ),
                variable = dbMembershipFunction.variable?.let { VariableDTODb.fromModelDb(it) },
                parameter1 = dbMembershipFunction.parameter1,
                parameter2 = dbMembershipFunction.parameter2,
                parameter3 = dbMembershipFunction.parameter3,
                parameter4 = dbMembershipFunction.parameter4,
                value = dbMembershipFunction.value,
                parent = dbMembershipFunction.parent?.let { fromModelDb(it) },
                hedgeType = LinguisticHedgeTypeDTODb.fromLinguisticHedgeTypeDb(
                    DBLinguisticHedgeType.fromString(dbMembershipFunction.hedgeType)
                )
            )
        }
    }
}

data class MembershipFunctionTemplateDTODb(
    val id: Int,
    val term: String?,
    val functionType: MembershipFunctionTypeDTODb,
    val variableId: Int?,
    val parameter1: Double?,
    val parameter2: Double?,
    val parameter3: Double?,
    val parameter4: Double?,
    val value: Double?,
    val parentId: Int?,
    val hedgeType: LinguisticHedgeTypeDTODb,
    val systemId: Int
) {
    fun toModelDb() = DBInsertableMembershipFunction(
        id = this.id,
        term = this.term,
        functionType = this.functionType.toMembershipFunctionTypeDb().text,
        variable = this.variableId,
        parameter1 = this.parameter1,
        parameter2 = this.parameter2,
        parameter3 = this.parameter3,
        parameter4 = this.parameter4,
        value = this.value,
        parentId = this.parentId,
        hedgeType = this.hedgeType.toLinguisticHedgeTypeDb().text
    )

    companion object {
        fun fromModel(membershipFunction: MembershipFunctionTemplate) =
            MembershipFunctionTemplateDTODb(
                id = membershipFunction.id,
                term = membershipFunction.term,
                functionType = MembershipFunctionTypeDTODb.fromMembershipFunctionType(membershipFunction.functionType),
                variableId = membershipFunction.variableId,
                parameter1 = membershipFunction.parameter1,
                parameter2 = membershipFunction.parameter2,
                parameter3 = membershipFunction.parameter3,
                parameter4 = membershipFunction.parameter4,
                value = membershipFunction.value,
                parentId = membershipFunction.parentId,
                hedgeType = LinguisticHedgeTypeDTODb.fromLinguisticHedgeType(membershipFunction.hedgeType),
                systemId = membershipFunction.systemId
            )
    }
}
