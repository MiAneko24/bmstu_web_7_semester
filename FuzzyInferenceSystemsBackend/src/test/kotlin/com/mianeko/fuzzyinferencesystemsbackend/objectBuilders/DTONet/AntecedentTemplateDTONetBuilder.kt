package com.mianeko.fuzzyinferencesystemsbackend.objectBuilders.DTONet

import com.mianeko.fuzzyinferencesystemsbackend.DTONet.AntecedentTemplateDTONet

class AntecedentTemplateDTONetBuilder private constructor(
    private var id: Int? = defaultId,
    private var memberhshipFunctionId: Int = defaultMembershipFunctionId,
    private var systemId: Int = defaultSystemId
) {
    fun build() =
        AntecedentTemplateDTONet(
            id = this.id,
            membershipFunctionId = this.memberhshipFunctionId,
            systemId = this.systemId
        )

    fun withId(id: Int): AntecedentTemplateDTONetBuilder {
        this.id = id
        return this
    }

    fun withMembershipFunctionId(memberhshipFunctionId: Int): AntecedentTemplateDTONetBuilder {
        this.memberhshipFunctionId = memberhshipFunctionId
        return this
    }

    fun withSystemId(systemId: Int): AntecedentTemplateDTONetBuilder {
        this.systemId = systemId
        return this
    }

    companion object {
        val defaultId: Int? = null
        val defaultMembershipFunctionId = 1
        val defaultSystemId = 1

        fun nAntecedentTemplateDTO() = AntecedentTemplateDTONetBuilder()
    }
}
