package com.mianeko.fuzzyinferencesystemsbackend.DTODb

import com.mianeko.fuzzyinferencesystemsbackend.database.entities.DBInsertableJob
import com.mianeko.fuzzyinferencesystemsbackend.database.entities.DBJob
import com.mianeko.fuzzyinferencesystemsbackend.services.models.Job
import java.util.UUID

data class JobDTODb(
    val id: UUID,
    val inputVariables: Map<Int, Double>,
    val systemId: Int
) {
    fun toModel() = Job(id, inputVariables, systemId)

    fun toModelDb() = DBInsertableJob(id, inputVariables, systemId)

    companion object {
        fun fromModel(job: Job) =
            JobDTODb(job.id, job.inputVariables, job.systemId)

        fun fromModelDb(dbJob: DBJob) =
            JobDTODb(dbJob.id, dbJob.inputVariables, dbJob.systemId)
    }
}