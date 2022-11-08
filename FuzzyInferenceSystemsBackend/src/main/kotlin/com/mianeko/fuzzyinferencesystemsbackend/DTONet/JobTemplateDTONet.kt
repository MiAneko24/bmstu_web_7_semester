package com.mianeko.fuzzyinferencesystemsbackend.DTONet

import com.mianeko.fuzzyinferencesystemsbackend.api.models.DataElementNet
import com.mianeko.fuzzyinferencesystemsbackend.api.models.JobNet
import com.mianeko.fuzzyinferencesystemsbackend.api.models.JobTemplateNet
import com.mianeko.fuzzyinferencesystemsbackend.services.models.Job
import java.util.*

data class JobTemplateDTONet(
    val inputVariables: Map<Int, Double>,
    val systemId: Int
) {
    fun toModel(id: UUID) = Job(id, inputVariables, systemId)

    companion object {
        fun fromModelNet(jobTemplateNet: JobTemplateNet, systemId: Int) =
            JobTemplateDTONet(
                jobTemplateNet.inputVariables.map {
                    it.key to it.value
                }.toMap(),
                systemId
            )
    }
}

data class JobDTONet(
    val id: UUID,
    val inputVariables: Map<Int, Double>,
    val systemId: Int
) {
    fun toModelNet() = JobNet(
        id,
        inputVariables.map {
            DataElementNet(it.key, it.value)
        }
    )

    companion object {
        fun fromModel(job: Job) =
            JobDTONet(job.id, job.inputVariables, job.systemId)
    }
}

