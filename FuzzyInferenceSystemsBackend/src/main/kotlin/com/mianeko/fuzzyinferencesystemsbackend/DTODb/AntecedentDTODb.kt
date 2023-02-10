package com.mianeko.fuzzyinferencesystemsbackend.DTODb

import com.mianeko.fuzzyinferencesystemsbackend.database.entities.DBAntecedent
import com.mianeko.fuzzyinferencesystemsbackend.database.entities.DBInsertableAntecedent
import com.mianeko.fuzzyinferencesystemsbackend.services.models.Antecedent
import com.mianeko.fuzzyinferencesystemsbackend.services.models.AntecedentTemplate


data class AntecedentDTODb(
    val id: Int,
    val membershipFunction: MembershipFunctionDTODb,
) {

    fun toModel(): Antecedent = Antecedent(
        id = this.id,
        membershipFunction = this.membershipFunction.toModel()
    )

    companion object {
        fun fromModelDb(dbAntecedent: DBAntecedent) = AntecedentDTODb(
            id = dbAntecedent.id,
            membershipFunction = MembershipFunctionDTODb.fromModelDb(dbAntecedent.membershipFunction),
        )
    }
}

data class AntecedentTemplateDTODb(
    val id: Int,
    val membershipFunctionId: Int,
    val systemId: Int
) {
    fun toModelDb() = DBInsertableAntecedent(
        id = this.id,
        membershipFunction = this.membershipFunctionId
    )

    companion object {
        fun fromModel(antecedentTemplate: AntecedentTemplate) = AntecedentTemplateDTODb(
            id = antecedentTemplate.id,
            membershipFunctionId = antecedentTemplate.membershipFunctionId,
            systemId = antecedentTemplate.systemId
        )
    }
}