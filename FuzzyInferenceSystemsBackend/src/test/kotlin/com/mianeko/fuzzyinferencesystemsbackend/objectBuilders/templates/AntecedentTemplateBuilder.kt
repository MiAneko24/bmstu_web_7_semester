package com.mianeko.fuzzyinferencesystemsbackend.objectBuilders.templates

import com.mianeko.fuzzyinferencesystemsbackend.services.models.AntecedentTemplate

class AntecedentTemplateBuilder private constructor(
    private var id: Int = defaultId,
    private var memberhshipFunctionId: Int = defaultMembershipFunctionId,
    private var systemId: Int = defaultSystemId
) {
    fun build() =
        AntecedentTemplate(
            id = this.id,
            membershipFunctionId = this.memberhshipFunctionId,
            systemId = this.systemId
        )

    fun withId(id: Int): AntecedentTemplateBuilder {
        this.id = id
        return this
    }

    fun withMembershipFunctionId(memberhshipFunctionId: Int): AntecedentTemplateBuilder {
        this.memberhshipFunctionId = memberhshipFunctionId
        return this
    }

    fun withSystemId(systemId: Int): AntecedentTemplateBuilder {
        this.systemId = systemId
        return this
    }

    companion object {
        val defaultId = 1
        val defaultMembershipFunctionId = 1
        val defaultSystemId = 1

        fun nAntecedentTemplate() = AntecedentTemplateBuilder()
    }
}
