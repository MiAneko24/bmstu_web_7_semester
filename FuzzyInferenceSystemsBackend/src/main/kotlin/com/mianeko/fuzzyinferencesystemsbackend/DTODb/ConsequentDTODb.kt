package com.mianeko.fuzzyinferencesystemsbackend.DTODb

import com.mianeko.fuzzyinferencesystemsbackend.database.entities.DBConsequent
import com.mianeko.fuzzyinferencesystemsbackend.database.entities.DBInsertableConsequent
import com.mianeko.fuzzyinferencesystemsbackend.services.models.Consequent
import com.mianeko.fuzzyinferencesystemsbackend.services.models.ConsequentTemplate

data class ConsequentDTODb(
    val id: Int,
    val ruleId: Int,
    val membershipFunction: MembershipFunctionDTODb,
    val variable: VariableDTODb?,
    val systemId: Int
) {
    fun toModel() = Consequent(
        id = this.id,
        ruleId = this.ruleId,
        membershipFunction = this.membershipFunction.toModel(),
        variable = this.variable?.toModel(),
        systemId = this.systemId
    )

    companion object {
        fun fromModelDb(dbConsequent: DBConsequent) = ConsequentDTODb(
            id = dbConsequent.id,
            ruleId = dbConsequent.rule.id,
            membershipFunction = MembershipFunctionDTODb.fromModelDb(dbConsequent.membershipFunction),
            variable = dbConsequent.variable?.let { VariableDTODb.fromModelDb(it) },
            systemId = dbConsequent.rule.system.id
        )
    }
}


data class ConsequentTemplateDTODb(
    val id: Int,
    val ruleId: Int,
    val membershipFunctionId: Int,
    val variableId: Int?,
    val systemId: Int
) {
    fun toModelDb() = DBInsertableConsequent(
        id = this.id,
        ruleId = this.ruleId,
        membershipFunctionId = this.membershipFunctionId,
        variableId = this.variableId
    )

    companion object {
        fun fromModel(consequent: ConsequentTemplate) = ConsequentTemplateDTODb(
            id = consequent.id,
            ruleId = consequent.ruleId,
            membershipFunctionId = consequent.membershipFunctionId,
            variableId = consequent.variableId,
            systemId = consequent.systemId
        )
    }
}