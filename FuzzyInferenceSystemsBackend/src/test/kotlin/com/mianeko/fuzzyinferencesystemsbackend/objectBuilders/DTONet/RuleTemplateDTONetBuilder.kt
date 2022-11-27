package com.mianeko.fuzzyinferencesystemsbackend.objectBuilders.DTONet

import com.mianeko.fuzzyinferencesystemsbackend.DTODb.enums.AntecedentConnectionDTONet
import com.mianeko.fuzzyinferencesystemsbackend.DTONet.RuleTemplateDTONet

class RuleTemplateDTONetBuilder private constructor(
    private var id: Int = defaultId,
    private var systemId: Int = defaultSystemId,
    private var antecedentConnection: AntecedentConnectionDTONet = defaultAntecedentConnection,
    private var weight: Double = defaultWeight,
    private var antecedents: List<Int>? = defaultAntecedents,
){
    fun build() = RuleTemplateDTONet(
        id = this.id,
        systemId = this.systemId,
        antecedentConnection = this.antecedentConnection,
        weight = this.weight,
        antecedents = this.antecedents
    )

    fun withId(id: Int): RuleTemplateDTONetBuilder {
        this.id = id
        return this
    }

    fun withSystem(systemId: Int): RuleTemplateDTONetBuilder {
        this.systemId = systemId
        return this
    }

    fun withAntecedentConnection(antecedentConnection: AntecedentConnectionDTONet): RuleTemplateDTONetBuilder {
        this.antecedentConnection = antecedentConnection
        return this
    }

    fun withWeight(weight: Double): RuleTemplateDTONetBuilder {
        this.weight = weight
        return this
    }

    fun withAntecedents(antecedents: List<Int>?): RuleTemplateDTONetBuilder {
        this.antecedents = antecedents
        return this
    }

    companion object {
        val defaultId: Int = 1
        val defaultSystemId: Int = 1
        val defaultAntecedentConnection: AntecedentConnectionDTONet = AntecedentConnectionDTONet.And
        val defaultWeight: Double = 1.0
        val defaultAntecedents: List<Int> = listOf(1)

        fun nRuleTemplateDTONet() = RuleTemplateDTONetBuilder()
    }
}