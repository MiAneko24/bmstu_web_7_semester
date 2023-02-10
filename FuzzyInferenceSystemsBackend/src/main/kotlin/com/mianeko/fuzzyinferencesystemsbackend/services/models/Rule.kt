package com.mianeko.fuzzyinferencesystemsbackend.services.models

import com.mianeko.fuzzyinferencesystemsbackend.services.models.enums.AntecedentConnection
import com.mianeko.fuzzyinferencesystemsbackend.services.models.enums.FuzzySystemType
import kotlin.math.abs

data class Rule(
    val id: Int,
    val inferenceSystem: InferenceSystem,
    val antecedentConnection: AntecedentConnection,
    val weight: Double,
    val antecedents: List<Antecedent>,
    val consequents: List<Consequent>,
) {
    fun getText(): String {
        val antecedentsText = this.antecedents
            .mapIndexed { i, x ->
                val connection =
                    if (i == 0) "if"
                    else " ${this.antecedentConnection}"

                "$connection $x" }.joinToString("")

        val consequentsText =
            when (this.inferenceSystem.type) {
                FuzzySystemType.Mamdani -> this.consequents.map {
                    "${it.membershipFunction.variable?.name
                        ?: "variable"} is ${it.membershipFunction.term}"
                }.mapIndexed{ i, x ->
                    val connection = if (i != 0) " and" else "then"
                    "$connection $x"
                }.joinToString("")

                FuzzySystemType.Sugeno -> this.consequents.groupBy { it.variable }
                    .map { it.value.mapIndexed { i, x ->
                        if (i == 0)
                            "${it.key?.name
                                ?: "variable"} = " +
                                    "${x.membershipFunction.parameter1} ${x.membershipFunction.variable?.name ?: ""}"
                        else
                            " ${if (x.membershipFunction.parameter1 != null
                                && x.membershipFunction.parameter1 > 0.0)
                                "+ ${x.membershipFunction.parameter1}"
                            else if (x.membershipFunction.parameter1 != null)
                                "- ${abs(x.membershipFunction.parameter1)}"
                            else " + <invalid coefficient>"}" +
                                    " ${x.membershipFunction.variable?.name
                                        ?: ""}"
                    }.joinToString("") }

                    .mapIndexed{ i, x ->
                        val connection = if (i != 0) "and" else "then"
                        "$connection $x"
                    }.joinToString("")
            }
        return "$antecedentsText $consequentsText"
    }
}

data class RuleTemplate(
    val id: Int,
    val systemId: Int,
    val antecedentConnection: AntecedentConnection,
    val weight: Double,
    val antecedents: List<AntecedentTemplate>
)
