package com.mianeko.fuzzyinferencesystemsbackend.database.repositories.objectBuilders

import com.mianeko.fuzzyinferencesystemsbackend.services.models.Antecedent
import com.mianeko.fuzzyinferencesystemsbackend.services.models.Consequent
import com.mianeko.fuzzyinferencesystemsbackend.services.models.InferenceSystem
import com.mianeko.fuzzyinferencesystemsbackend.services.models.Rule
import com.mianeko.fuzzyinferencesystemsbackend.services.models.enums.AntecedentConnection


class RuleBuilder private constructor(
    private var id: Int = defaultId,
    private var system: InferenceSystem = defaultSystem,
    private var antecedentConnection: AntecedentConnection = defaultAntecedentConnection,
    private var weight: Double = defaultWeight,
    private var antecedents: List<Antecedent> = defaultAntecedents,
    private var consequents: List<Consequent> = defaultConsequents,
){
    fun build() = Rule(
        id = this.id,
        inferenceSystem = this.system,
        antecedentConnection = this.antecedentConnection,
        weight = this.weight,
        antecedents = this.antecedents,
        consequents = this.consequents
    )

    fun withId(id: Int): RuleBuilder {
        this.id = id
        return this
    }

    fun withSystem(system: InferenceSystem): RuleBuilder {
        this.system = system
        return this
    }

    fun withAntecedentConnection(antecedentConnection: AntecedentConnection): RuleBuilder {
        this.antecedentConnection = antecedentConnection
        return this
    }

    fun withWeight(weight: Double): RuleBuilder {
        this.weight = weight
        return this
    }

    fun withAntecedents(antecedents: List<Antecedent>): RuleBuilder {
        this.antecedents = antecedents
        return this
    }

    fun withConsequents(consequents: List<Consequent>): RuleBuilder {
        this.consequents = consequents
        return this
    }

    companion object {
        val defaultId: Int = 1
        val defaultSystem: InferenceSystem = SystemBuilder.nSystem().build()
        val defaultAntecedentConnection: AntecedentConnection = AntecedentConnection.And
        val defaultWeight: Double = 1.0
        val defaultAntecedents: List<Antecedent> = listOf(AntecedentBuilder.nAntecedent().build())
        val defaultConsequents: List<Consequent> = listOf(ConsequentBuilder.nConsequent().build())

        fun nRule() = RuleBuilder()
    }
}