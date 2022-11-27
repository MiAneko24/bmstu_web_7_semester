package com.mianeko.fuzzyinferencesystemsbackend.objectBuilders.DTONet

import com.mianeko.fuzzyinferencesystemsbackend.DTONet.JobTemplateDTONet

class JobTemplateDTONetBuilder private constructor(
    private var inputVariables: Map<Int, Double> = defaultVariables,
    private var systemId: Int = defaultSystemId
) {
    fun build() = JobTemplateDTONet (
        inputVariables = this.inputVariables,
        systemId = this.systemId
    )

    fun withInputVariables(inputVariables: Map<Int, Double>): JobTemplateDTONetBuilder {
        this.inputVariables = inputVariables
        return this
    }

    fun withSystemId(systemId: Int): JobTemplateDTONetBuilder {
        this.systemId = systemId
        return this
    }

    companion object {
        val defaultVariables: Map<Int, Double> = mapOf(
            1 to 5.0
        )
        val defaultSystemId = 1

        fun nJobTemplateDTONet() = JobTemplateDTONetBuilder()
    }
}