package com.mianeko.fuzzyinferencesystemsbackend.database.entities

import com.mianeko.fuzzyinferencesystemsbackend.services.models.Consequent
import com.mianeko.fuzzyinferencesystemsbackend.services.models.ConsequentTemplate
import javax.persistence.*

@Entity
@Table(name="consequent", schema="public")
data class DBConsequent(
    @Id
    @Column(name = "c_id")
    val id: Int,

    @ManyToOne
    @JoinColumn(name = "m_id", referencedColumnName = "m_id")
    val membershipFunction: DBMembershipFunction,

    @ManyToOne
    @JoinColumn(name = "r_id", referencedColumnName = "r_id")
    val rule: DBRule,

    @ManyToOne
    @JoinColumn(name = "v_id", referencedColumnName = "v_id")
    val variable: DBVariable?
) {
    fun toModel(): Consequent = Consequent(
        id = this.id,
        membershipFunction = this.membershipFunction.toModel(),
        ruleId = this.rule.id,
        variable = this.variable?.toModel(),
        systemId = this.rule.system.id
    )
}

@Entity
@Table(name="consequent", schema="public")
data class DBInsertableConsequent(
    @Id
    @Column(name = "c_id")
    val id: Int,

    @Column(name = "m_id")
    val membershipFunctionId: Int,

    @Column(name = "r_id")
    val ruleId: Int,

    @Column(name = "v_id")
    val variableId: Int?

) {
    companion object {
        fun fromModel(consequent: ConsequentTemplate) = DBInsertableConsequent(
            id = consequent.id,
            membershipFunctionId = consequent.membershipFunctionId,
            ruleId = consequent.ruleId,
            variableId = consequent.variableId
        )
    }
}