package com.mianeko.fuzzyinferencesystemsbackend.objectBuilders.DTONet

import com.mianeko.fuzzyinferencesystemsbackend.DTONet.AntecedentDTONet

class AntecedentDTONetBuilder private constructor(
    private var id: Int = defaultId,
    private var memberhshipFunctionId: Int = defaultMembershipFunctionId,
    private var text: String = defaultText,
    private var systemId: Int = defaultSystemId
) {
    fun build() =
        AntecedentDTONet(
            id = id,
            membershipFunctionId = memberhshipFunctionId,
            text = text,
            systemId = systemId
        )

    fun withId(id: Int): AntecedentDTONetBuilder {
        this.id = id
        return this
    }

    fun withMembershipFunctionId(membershipFunctionId: Int): AntecedentDTONetBuilder {
        this.memberhshipFunctionId = membershipFunctionId
        return this
    }

    companion object {
        val defaultId = 1
        val defaultMembershipFunctionId = 1
        val defaultText = "Variable is Term"
        val defaultSystemId = 1

        fun nAntecedentDTONet() = AntecedentDTONetBuilder()
    }
}