package com.mianeko.fuzzyinferencesystemsbackend.database.repositories.objectBuilders

import com.mianeko.fuzzyinferencesystemsbackend.services.models.Antecedent
import com.mianeko.fuzzyinferencesystemsbackend.services.models.MembershipFunction

class AntecedentBuilder private constructor(
    private var id: Int = defaultId,
    private var memberhshipFunction: MembershipFunction = defaultMembershipFunction
) {
    fun build() =
        Antecedent(
            id = id,
            membershipFunction = memberhshipFunction
        )

    fun withId(id: Int): AntecedentBuilder {
        this.id = id
        return this
    }

    fun withMembershipFunction(MembershipFunction: MembershipFunction): AntecedentBuilder {
        this.memberhshipFunction = MembershipFunction
        return this
    }

    companion object {
        val defaultId = 1
        val defaultMembershipFunction =
            MembershipFunctionBuilder
                .nMembershipFunction()
                .build()

        fun nAntecedent() = AntecedentBuilder()
    }
}
