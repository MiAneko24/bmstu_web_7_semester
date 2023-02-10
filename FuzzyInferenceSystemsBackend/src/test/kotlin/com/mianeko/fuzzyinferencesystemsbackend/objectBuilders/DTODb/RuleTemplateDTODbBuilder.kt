package com.mianeko.fuzzyinferencesystemsbackend.objectBuilders.DTODb

import com.mianeko.fuzzyinferencesystemsbackend.DTODb.AntecedentTemplateDTODb
import com.mianeko.fuzzyinferencesystemsbackend.DTODb.RuleTemplateDTODb
import com.mianeko.fuzzyinferencesystemsbackend.DTODb.enums.AntecedentConnectionDTODb

class RuleTemplateDTODbBuilder private constructor(
    private var id: Int = defaultId,
    private var systemId: Int = defaultSystemId,
    private var antecedentConnection: AntecedentConnectionDTODb = defaultAntecedentConnection,
    private var weight: Double = defaultWeight,
    private var antecedents: List<AntecedentTemplateDTODb> = defaultAntecedents,
){
    fun build() = RuleTemplateDTODb(
        id = this.id,
        systemId = this.systemId,
        antecedentConnection = this.antecedentConnection,
        weight = this.weight,
        antecedents = this.antecedents
    )

    fun withId(id: Int): RuleTemplateDTODbBuilder {
        this.id = id
        return this
    }

    fun withSystem(systemId: Int): RuleTemplateDTODbBuilder {
        this.systemId = systemId
        return this
    }

    fun withAntecedentConnection(antecedentConnection: AntecedentConnectionDTODb): RuleTemplateDTODbBuilder {
        this.antecedentConnection = antecedentConnection
        return this
    }

    fun withWeight(weight: Double): RuleTemplateDTODbBuilder {
        this.weight = weight
        return this
    }

    fun withAntecedents(antecedents: List<AntecedentTemplateDTODb>): RuleTemplateDTODbBuilder {
        this.antecedents = antecedents
        return this
    }

    companion object {
        val defaultId: Int = 1
        val defaultSystemId: Int = 1
        val defaultAntecedentConnection: AntecedentConnectionDTODb = AntecedentConnectionDTODb.And
        val defaultWeight: Double = 1.0
        val defaultAntecedents: List<AntecedentTemplateDTODb> = listOf(AntecedentTemplateDTODbBuilder.nAntecedentTemplateDTODb().build())

        fun nRuleTemplateDTODb() = RuleTemplateDTODbBuilder()
    }
}