package com.mianeko.fuzzyinferencesystemsbackend.objectBuilders.DTONet

import com.mianeko.fuzzyinferencesystemsbackend.DTODb.enums.AntecedentConnectionDTONet
import com.mianeko.fuzzyinferencesystemsbackend.DTONet.RuleDTONet

class RuleDTONetBuilder private constructor(
    private var id: Int = defaultId,
    private var text: String = defaultText,
    private var systemId: Int = defaultSystemId,
    private var antecedentConnection: AntecedentConnectionDTONet = defaultAntecedentConnection,
    private var weight: Double = defaultWeight,
    private var antecedentIds: List<Int> = defaultAntecedents
){
    fun build() = RuleDTONet(
        id = this.id,
        text = text,
        systemId = this.systemId,
        antecedentConnection = this.antecedentConnection,
        weight = this.weight,
        antecedentIds = this.antecedentIds
    )

    fun withId(id: Int): RuleDTONetBuilder {
        this.id = id
        return this
    }

    fun withSystem(system: Int): RuleDTONetBuilder {
        this.systemId = system
        return this
    }

    fun withAntecedentConnection(antecedentConnection: AntecedentConnectionDTONet): RuleDTONetBuilder {
        this.antecedentConnection = antecedentConnection
        return this
    }

    fun withWeight(weight: Double): RuleDTONetBuilder {
        this.weight = weight
        return this
    }

    fun withAntecedents(antecedentIds: List<Int>): RuleDTONetBuilder {
        this.antecedentIds = antecedentIds
        return this
    }

    fun withText(text: String): RuleDTONetBuilder {
        this.text = text
        return this
    }

    companion object {
        val defaultId: Int = 1
        val defaultText: String = "if Variable is Term then Variable is Term"
        val defaultSystemId: Int = 1
        val defaultAntecedentConnection: AntecedentConnectionDTONet = AntecedentConnectionDTONet.And
        val defaultWeight: Double = 1.0
        val defaultAntecedents: List<Int> = listOf(1)

        fun nRuleDTONet() = RuleDTONetBuilder()
    }
}