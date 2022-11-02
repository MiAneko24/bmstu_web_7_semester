package com.mianeko.fuzzyinferencesystemsbackend.database.entities

import com.mianeko.fuzzyinferencesystemsbackend.database.entities.enums.DBAntecedentConnection
import com.mianeko.fuzzyinferencesystemsbackend.services.models.Rule
import com.mianeko.fuzzyinferencesystemsbackend.services.models.RuleTemplate
import javax.persistence.*

@Entity
@Table(name="rule", schema = "public")
data class DBRule(
    @Id
    @Column(name = "r_id")
    val id: Int,

    @ManyToOne
    @JoinColumn(name = "s_id", referencedColumnName = "s_id")
    val system: DBSystem,

    @Column(name = "antecedent_connection")
    val antecedentConnection: String,
    val weight: Double,

    @ManyToMany(cascade = [CascadeType.ALL])
    @JoinTable(name = "rule_antecedents", schema = "public",
        joinColumns = [JoinColumn(name = "r_id", referencedColumnName = "r_id")],
        inverseJoinColumns = [JoinColumn(name = "a_id", referencedColumnName = "a_id")])
    val antecedents: List<DBAntecedent>,

    @OneToMany(mappedBy = "rule")
    @Column(name = "c_id")
    val consequents: List<DBConsequent>,
) {
    fun toModel() = Rule(
        id = this.id,
        inferenceSystem = this.system.toModel(),
        antecedentConnection = DBAntecedentConnection.fromString(this.antecedentConnection).toAntecedentConnection(),
        weight = this.weight,
        antecedents = this.antecedents.map { it.toModel() },
        consequents = this.consequents.map { it.toModel() }
    )
}


@Entity
@Table(name="rule", schema = "public")
data class DBInsertableRule(
    @Id
    @Column(name="r_id")
    val id: Int,

    @Column(name = "s_id")
    val system: Int,

    @Column(name = "antecedent_connection")
    val antecedentConnection: String,
    val weight: Double,

    @ManyToMany(cascade = [CascadeType.ALL])
    @JoinTable(name = "rule_antecedents", schema = "public",
        joinColumns = [JoinColumn(name = "r_id", referencedColumnName = "r_id")],
        inverseJoinColumns = [JoinColumn(name = "a_id", referencedColumnName = "a_id")])
    val antecedents: List<DBInsertableAntecedent>,

//    @OneToMany(cascade = [CascadeType.ALL], fetch = FetchType.LAZY)
//    @JoinColumn(name = "c_id", referencedColumnName = "r_id")
//    val consequents: List<DBInsertableConsequent>,
) {
    companion object {
        fun fromModel(rule: RuleTemplate): DBInsertableRule {
            return DBInsertableRule(
                id = rule.id,
                system = rule.systemId,
                antecedentConnection = rule.antecedentConnection.toString(),
                weight = rule.weight,
                antecedents = rule.antecedents.map { DBInsertableAntecedent.fromModel(it) },
//                consequents = rule.consequents.map { DBInsertableConsequent.fromModel(it) }
            )
        }
    }
}
