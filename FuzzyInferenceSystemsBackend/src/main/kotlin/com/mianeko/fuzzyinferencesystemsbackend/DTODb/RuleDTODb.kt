package com.mianeko.fuzzyinferencesystemsbackend.DTODb

import com.mianeko.fuzzyinferencesystemsbackend.database.entities.DBInsertableRule
import com.mianeko.fuzzyinferencesystemsbackend.database.entities.DBRule
import com.mianeko.fuzzyinferencesystemsbackend.database.entities.enums.DBAntecedentConnection
import com.mianeko.fuzzyinferencesystemsbackend.services.models.Rule
import com.mianeko.fuzzyinferencesystemsbackend.services.models.RuleTemplate
import com.mianeko.fuzzyinferencesystemsbackend.services.models.enums.AntecedentConnection


data class RuleDTODb(
    val id: Int,
    val inferenceSystem: SystemDTODb,
    val antecedentConnection: AntecedentConnection,
    val weight: Double,
    val antecedents: List<AntecedentDTODb>,
    val consequents: List<ConsequentDTODb>,
) {
    fun toModel() = Rule(
        id = this.id,
        inferenceSystem = this.inferenceSystem.toModel(),
        antecedentConnection = this.antecedentConnection,
        weight = this.weight,
        antecedents = this.antecedents.map { it.toModel() },
        consequents = this.consequents.map { it.toModel() }
    )

    companion object {
        fun fromModelDb(dbRule: DBRule) = RuleDTODb(
            id = dbRule.id,
            inferenceSystem = SystemDTODb.fromModelDb(dbRule.system),
            antecedentConnection = DBAntecedentConnection.fromString(dbRule.antecedentConnection).toAntecedentConnection(),
            weight = dbRule.weight,
            antecedents = dbRule.antecedents.map { AntecedentDTODb.fromModelDb(it) },
            consequents = dbRule.consequents.map { ConsequentDTODb.fromModelDb(it) }
        )
    }
}

data class RuleTemplateDTODb(
    val id: Int,
    val systemId: Int,
    val antecedentConnection: AntecedentConnection,
    val weight: Double,
    val antecedents: List<AntecedentTemplateDTODb>
) {
    fun toModelDb() = DBInsertableRule(
        id = id,
        system = this.systemId,
        antecedentConnection = DBAntecedentConnection.fromAntecedentConnection(this.antecedentConnection).toString(),
        weight = this.weight,
        antecedents = this.antecedents.map { it.toModelDb() }
    )

    companion object {
        fun fromModel(ruleTemplate: RuleTemplate) = RuleTemplateDTODb(
            id = ruleTemplate.id,
            systemId = ruleTemplate.systemId,
            antecedentConnection = ruleTemplate.antecedentConnection,
            weight = ruleTemplate.weight,
            antecedents = ruleTemplate.antecedents.map { AntecedentTemplateDTODb.fromModel(it) }
        )
    }
}
