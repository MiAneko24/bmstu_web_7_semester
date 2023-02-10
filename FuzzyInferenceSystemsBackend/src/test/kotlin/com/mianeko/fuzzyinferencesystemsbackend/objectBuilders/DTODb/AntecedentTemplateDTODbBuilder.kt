package com.mianeko.fuzzyinferencesystemsbackend.objectBuilders.DTODb

import com.mianeko.fuzzyinferencesystemsbackend.DTODb.AntecedentTemplateDTODb

class AntecedentTemplateDTODbBuilder private constructor(
    private var id: Int = defaultId,
    private var memberhshipFunctionId: Int = defaultMembershipFunctionId,
    private var systemId: Int = defaultSystemId
) {
    fun build() =
        AntecedentTemplateDTODb(
            id = id,
            membershipFunctionId = memberhshipFunctionId,
            systemId =  systemId
        )

    fun withId(id: Int): AntecedentTemplateDTODbBuilder {
        this.id = id
        return this
    }

    fun withMembershipFunctionId(membershipFunctionId: Int): AntecedentTemplateDTODbBuilder {
        this.memberhshipFunctionId = membershipFunctionId
        return this
    }

    companion object {
        val defaultId = 1
        val defaultMembershipFunctionId = 1
        val defaultSystemId = 1

        fun nAntecedentTemplateDTODb() = AntecedentTemplateDTODbBuilder()
    }
}