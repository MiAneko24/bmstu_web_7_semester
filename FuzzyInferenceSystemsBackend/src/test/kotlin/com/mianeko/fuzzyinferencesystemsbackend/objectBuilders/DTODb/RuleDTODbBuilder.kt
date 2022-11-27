package com.mianeko.fuzzyinferencesystemsbackend.objectBuilders.DTODb

import com.mianeko.fuzzyinferencesystemsbackend.DTODb.AntecedentDTODb
import com.mianeko.fuzzyinferencesystemsbackend.DTODb.ConsequentDTODb
import com.mianeko.fuzzyinferencesystemsbackend.DTODb.RuleDTODb
import com.mianeko.fuzzyinferencesystemsbackend.DTODb.SystemDTODb
import com.mianeko.fuzzyinferencesystemsbackend.DTODb.enums.AntecedentConnectionDTODb

class RuleDTODbBuilder private constructor(
    private var id: Int = defaultId,
    private var system: SystemDTODb = defaultSystem,
    private var antecedentConnection: AntecedentConnectionDTODb = defaultAntecedentConnection,
    private var weight: Double = defaultWeight,
    private var antecedents: List<AntecedentDTODb> = defaultAntecedents,
    private var consequents: List<ConsequentDTODb> = defaultConsequents,
){
    fun build() = RuleDTODb(
        id = this.id,
        inferenceSystem = this.system,
        antecedentConnection = this.antecedentConnection,
        weight = this.weight,
        antecedents = this.antecedents,
        consequents = this.consequents
    )

    fun withId(id: Int): RuleDTODbBuilder {
        this.id = id
        return this
    }

    fun withSystem(system: SystemDTODb): RuleDTODbBuilder {
        this.system = system
        return this
    }

    fun withAntecedentConnection(antecedentConnection: AntecedentConnectionDTODb): RuleDTODbBuilder {
        this.antecedentConnection = antecedentConnection
        return this
    }

    fun withWeight(weight: Double): RuleDTODbBuilder {
        this.weight = weight
        return this
    }

    fun withAntecedents(antecedents: List<AntecedentDTODb>): RuleDTODbBuilder {
        this.antecedents = antecedents
        return this
    }

    fun withConsequents(consequents: List<ConsequentDTODb>): RuleDTODbBuilder {
        this.consequents = consequents
        return this
    }

    companion object {
        val defaultId: Int = 1
        val defaultSystem: SystemDTODb = SystemDTODbBuilder.nSystemDTODb().build()
        val defaultAntecedentConnection: AntecedentConnectionDTODb = AntecedentConnectionDTODb.And
        val defaultWeight: Double = 1.0
        val defaultAntecedents: List<AntecedentDTODb> = listOf(AntecedentDTODbBuilder.nAntecedentDTODb().build())
        val defaultConsequents: List<ConsequentDTODb> = listOf(ConsequentDTODbBuilder.nConsequentDTODb().build())

        fun nRuleDTODb() = RuleDTODbBuilder()
    }
}