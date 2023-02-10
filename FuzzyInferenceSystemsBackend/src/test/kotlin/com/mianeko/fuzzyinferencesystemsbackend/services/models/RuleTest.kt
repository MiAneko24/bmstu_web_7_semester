package com.mianeko.fuzzyinferencesystemsbackend.services.models

import com.mianeko.fuzzyinferencesystemsbackend.database.repositories.objectBuilders.*
import com.mianeko.fuzzyinferencesystemsbackend.services.models.enums.AntecedentConnection
import com.mianeko.fuzzyinferencesystemsbackend.services.models.enums.FuzzySystemType
import com.mianeko.fuzzyinferencesystemsbackend.services.models.enums.LinguisticHedgeType
import com.mianeko.fuzzyinferencesystemsbackend.services.models.enums.MembershipFunctionType
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

internal class RuleTest {

    @Test
    fun getTextMamdani() {
        val variable1 = VariableBuilder
            .nVariable()
            .withName("Temperature")
            .build()
        val variable2 = VariableBuilder
            .nVariable()
            .withName("Wind")
            .build()
        val variable3 = VariableBuilder
            .nVariable()
            .withName("Weather")
            .build()
        val variable4 = VariableBuilder
            .nVariable()
            .withName("Clothes")
            .build()

        val mf1 = MembershipFunctionBuilder
            .nMembershipFunction()
            .withTerm("Cold")
            .withVariable(variable1)
            .build()
        val mf2 = MembershipFunctionBuilder
            .nMembershipFunction()
            .withTerm("Strong")
            .withVariable(variable2)
            .build()
        val mf3 = MembershipFunctionBuilder
            .nMembershipFunction()
            .withTerm("Bad")
            .withVariable(variable3)
            .build()
        val mf4 = MembershipFunctionBuilder
            .nMembershipFunction()
            .withTerm("Warm")
            .withVariable(variable4)
            .build()

        val ant1 = AntecedentBuilder
            .nAntecedent()
            .withMembershipFunction(mf1)
            .build()
        val ant2 = AntecedentBuilder
            .nAntecedent()
            .withMembershipFunction(mf2)
            .build()
        val cons1 = ConsequentBuilder
            .nConsequent()
            .withMembershipFunction(mf3)
            .build()
        val cons2 = ConsequentBuilder
            .nConsequent()
            .withMembershipFunction(mf4)
            .build()

        val rule = RuleBuilder
            .nRule()
            .withAntecedents(listOf(
                ant1,
                ant2
            ))
            .withConsequents(listOf(
                cons1,
                cons2
            ))
            .build()

        val expectedText = "if Temperature is Cold and Wind is Strong then Weather is Bad and Clothes is Warm"

        val got = rule.getText()

        assertEquals(expectedText, got)
    }

    @Test
    fun getTextSugeno() {
        val variable1 = VariableBuilder
            .nVariable()
            .withName("Temperature")
            .build()
        val variable2 = VariableBuilder
            .nVariable()
            .withName("Wind")
            .build()
        val variable3 = VariableBuilder
            .nVariable()
            .withName("Pressure")
            .build()
        val variable4 = VariableBuilder
            .nVariable()
            .withName("Clothes")
            .build()

        val mf1 = MembershipFunctionBuilder
            .nMembershipFunction()
            .withTerm("Cold")
            .withVariable(variable1)
            .build()
        val mf2 = MembershipFunctionBuilder
            .nMembershipFunction()
            .withTerm("Strong")
            .withVariable(variable2)
            .build()
        val mf3 = MembershipFunctionBuilder
            .nMembershipFunction()
            .withTerm(null)
            .withFunctionType(
                MembershipFunctionType.Linear,
                3.5,
                null,
                null,
                null,
                null,
                LinguisticHedgeType.Nothing
            )
            .withVariable(variable1)
            .build()
        val mf31 = MembershipFunctionBuilder
            .nMembershipFunction()
            .withTerm(null)
            .withFunctionType(
                MembershipFunctionType.Linear,
                0.9,
                null,
                null,
                null,
                null,
                LinguisticHedgeType.Nothing
            )
            .withVariable(variable2)
            .build()
        val mf32 = MembershipFunctionBuilder
            .nMembershipFunction()
            .withTerm(null)
            .withFunctionType(
                MembershipFunctionType.Crisp,
                10.0,
                null,
                null,
                null,
                null,
                LinguisticHedgeType.Nothing
            )
            .withVariable(null)
            .build()
        val mf4 = MembershipFunctionBuilder
            .nMembershipFunction()
            .withTerm(null)
            .withFunctionType(
                MembershipFunctionType.Crisp,
                220.0,
                null,
                null,
                null,
                null,
                LinguisticHedgeType.Nothing
            )
            .withVariable(null)
            .build()

        val ant1 = AntecedentBuilder
            .nAntecedent()
            .withMembershipFunction(mf1)
            .build()
        val ant2 = AntecedentBuilder
            .nAntecedent()
            .withMembershipFunction(mf2)
            .build()
        val cons11 = ConsequentBuilder
            .nConsequent()
            .withMembershipFunction(mf3)
            .withVariable(variable3)
            .build()
        val cons12 = ConsequentBuilder
            .nConsequent()
            .withMembershipFunction(mf31)
            .withVariable(variable3)
            .build()
        val cons13 = ConsequentBuilder
            .nConsequent()
            .withMembershipFunction(mf32)
            .withVariable(variable3)
            .build()
        val cons2 = ConsequentBuilder
            .nConsequent()
            .withMembershipFunction(mf4)
            .withVariable(variable4)
            .build()
        val system = SystemBuilder.nSystem().withType(FuzzySystemType.Sugeno).build()

        val rule = RuleBuilder
            .nRule()
            .withAntecedents(listOf(
                ant1,
                ant2
            ))
            .withAntecedentConnection(AntecedentConnection.Or)
            .withConsequents(listOf(
                cons11,
                cons12,
                cons13,
                cons2
            ))
            .withSystem(system)
            .build()

        val expectedText = "if Temperature is Cold or Wind is Strong then Pressure = 3.5 Temperature + 0.9 Wind + 10.0 and Clothes = 220.0 "

        val got = rule.getText()

        assertEquals(expectedText, got)
    }
}