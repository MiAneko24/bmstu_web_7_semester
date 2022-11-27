package com.mianeko.fuzzyinferencesystemsbackend.database.repositories.objectBuilders.db

import com.mianeko.fuzzyinferencesystemsbackend.database.entities.DBAntecedent
import com.mianeko.fuzzyinferencesystemsbackend.database.entities.DBConsequent
import com.mianeko.fuzzyinferencesystemsbackend.database.entities.DBRule
import com.mianeko.fuzzyinferencesystemsbackend.database.entities.DBSystem
import com.mianeko.fuzzyinferencesystemsbackend.database.entities.enums.DBAntecedentConnection

class DBRuleBuilder private constructor(
    private var id: Int = defaultId,
    private var system: DBSystem = defaultSystem,
    private var antecedentConnection: String = defaultAntecedentConnection,
    private var weight: Double = defaultWeight,
    private var antecedents: List<DBAntecedent> = defaultAntecedents,
    private var consequents: List<DBConsequent> = defaultConsequents,
){
    fun build() = DBRule(
        id = this.id,
        system = this.system,
        antecedentConnection = this.antecedentConnection,
        weight = this.weight,
        antecedents = this.antecedents,
        consequents = this.consequents
    )

    fun withId(id: Int): DBRuleBuilder {
        this.id = id
        return this
    }

    fun withSystem(dbSystem: DBSystem): DBRuleBuilder {
        this.system = dbSystem
        return this
    }

    fun withAntecedentConnection(dbAntecedentConnection: DBAntecedentConnection): DBRuleBuilder {
        this.antecedentConnection = dbAntecedentConnection.text
        return this
    }

    fun withWeight(weight: Double): DBRuleBuilder {
        this.weight = weight
        return this
    }

    fun withAntecedents(antecedents: List<DBAntecedent>): DBRuleBuilder {
        this.antecedents = antecedents
        return this
    }

    fun withConsequents(consequents: List<DBConsequent>): DBRuleBuilder {
        this.consequents = consequents
        return this
    }

    companion object {
        val defaultId: Int = 1
        val defaultSystem: DBSystem = DBSystemBuilder.nDBSystem().build()
        val defaultAntecedentConnection: String = "and"
        val defaultWeight: Double = 1.0
        val defaultAntecedents: List<DBAntecedent> = listOf(DBAntecedentBuilder.nDBAntecedent().build())
        val defaultConsequents: List<DBConsequent> = listOf(DBConsequentBuilder.nDBConsequent().build())

        fun nDBRule() = DBRuleBuilder()
    }
}