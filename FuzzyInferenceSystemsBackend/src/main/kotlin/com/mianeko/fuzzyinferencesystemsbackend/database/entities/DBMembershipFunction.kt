package com.mianeko.fuzzyinferencesystemsbackend.database.entities

import com.mianeko.fuzzyinferencesystemsbackend.database.entities.enums.DBLinguisticHedgeType
import com.mianeko.fuzzyinferencesystemsbackend.database.entities.enums.DBMembershipFunctionType
import com.mianeko.fuzzyinferencesystemsbackend.services.models.MembershipFunction
import com.mianeko.fuzzyinferencesystemsbackend.services.models.MembershipFunctionTemplate
import com.mianeko.fuzzyinferencesystemsbackend.services.models.enums.LinguisticHedgeType
import com.mianeko.fuzzyinferencesystemsbackend.services.models.enums.MembershipFunctionType
import javax.persistence.*

@Entity
@Table(name = "membership_function", schema = "public")
data class DBMembershipFunction(
    @Id
    @Column(name = "m_id")
    val id: Int,
    val term: String?,

    @Column(name = "m_type")
    val functionType: String,

    @ManyToOne(optional = false)
    @JoinColumn(name = "v_id", referencedColumnName = "v_id")
    val variable: DBVariable?,

    val parameter1: Double?,
    val parameter2: Double?,
    val parameter3: Double?,
    val parameter4: Double?,

    @Column(name = "m_value")
    val value: Double?,

    @ManyToOne
    @JoinColumn(name = "p_id", referencedColumnName = "m_id")
    val parent: DBMembershipFunction?,

    @Column(name = "barrier")
    val hedgeType: String?
) {
    fun toModel(): MembershipFunction = MembershipFunction(
        id = this.id,
        term = this.term,
        functionType = DBMembershipFunctionType.fromString(this.functionType).toMembershipFunctionType(),
        variable = this.variable?.toModel(),
        parameter1 = this.parameter1,
        parameter2 = this.parameter2,
        parameter3 = this.parameter3,
        parameter4 = this.parameter4,
        value = this.value,
        parent = this.parent?.toModel(),
        hedgeType = DBLinguisticHedgeType.fromString(this.hedgeType).toLinguisticHedgeType()
    )
}


@Entity
@Table(name = "membership_function", schema = "public")
data class DBInsertableMembershipFunction(
    @Id
    @Column(name = "m_id")
    val id: Int,
    val term: String?,

    @Column(name = "m_type")
    val functionType: String,

    @Column(name="v_id")
    val variable: Int?,

    val parameter1: Double?,
    val parameter2: Double?,
    val parameter3: Double?,
    val parameter4: Double?,

    @Column(name = "m_value")
    val value: Double?,

    @Column(name = "p_id")
    val parentId: Int?,

    @Column(name = "barrier")
    val hedgeType: String?
) {
    companion object {
        fun fromModel(membershipFunction: MembershipFunctionTemplate) = DBInsertableMembershipFunction(
            id = membershipFunction.id,
            term = membershipFunction.term,
            functionType = DBMembershipFunctionType.fromMembershipFunctionType(membershipFunction.functionType).text,
            variable = membershipFunction.variableId,
            parameter1 = membershipFunction.parameter1,
            parameter2 = membershipFunction.parameter2,
            parameter3 = membershipFunction.parameter3,
            parameter4 = membershipFunction.parameter4,
            value = membershipFunction.value,
            parentId = membershipFunction.parentId,
            hedgeType = DBLinguisticHedgeType.fromLinguisticHedgeType(membershipFunction.hedgeType).text
        )
    }
}

