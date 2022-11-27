package com.mianeko.fuzzyinferencesystemsbackend.objectBuilders

import com.mianeko.fuzzyinferencesystemsbackend.lookupEntities.MembershipFunctionLookup

class MembershipFunctionLookupBuilder private constructor(
    private var systemId: Int = defaultSystemId,
    private var variableId: Int? = defaultVariableId,
    private var membershipFunctionId: Int? = defaultMembershipFunctionId
){
    fun build() = MembershipFunctionLookup(
        systemId = this.systemId,
        variableId = this.variableId,
        membershipFunctionId = this.membershipFunctionId
    )

    fun withSystemId(systemId: Int): MembershipFunctionLookupBuilder {
        this.systemId = systemId
        return this
    }

    fun withVariableId(variableId: Int): MembershipFunctionLookupBuilder {
        this.variableId = variableId
        return this
    }

    fun withMembershipFunctionId(membershipFunctionId: Int?): MembershipFunctionLookupBuilder {
        this.membershipFunctionId = membershipFunctionId
        return this
    }

    companion object {
        val defaultSystemId: Int = 1
        val defaultVariableId: Int? = null
        val defaultMembershipFunctionId: Int? = null

        fun nMembershipFunctionLookup() = MembershipFunctionLookupBuilder()
    }
}