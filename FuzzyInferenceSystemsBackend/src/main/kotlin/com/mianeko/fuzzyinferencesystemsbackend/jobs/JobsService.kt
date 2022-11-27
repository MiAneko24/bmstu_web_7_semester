package com.mianeko.fuzzyinferencesystemsbackend.jobs

import com.mianeko.fuzzyinferencesystemsbackend.DTODb.JobDTODb
import com.mianeko.fuzzyinferencesystemsbackend.DTODb.VariableTemplateDTODb
import com.mianeko.fuzzyinferencesystemsbackend.DTONet.JobDTONet
import com.mianeko.fuzzyinferencesystemsbackend.DTONet.JobTemplateDTONet
import com.mianeko.fuzzyinferencesystemsbackend.DTONet.OutputResultDTONet
import com.mianeko.fuzzyinferencesystemsbackend.database.repositories.*
import com.mianeko.fuzzyinferencesystemsbackend.exceptions.JobNotEnoughInputParametersException
import com.mianeko.fuzzyinferencesystemsbackend.exceptions.JobNotExistException
import com.mianeko.fuzzyinferencesystemsbackend.exceptions.SystemNotExistException
import com.mianeko.fuzzyinferencesystemsbackend.exceptions.VariableNotExistException
import com.mianeko.fuzzyinferencesystemsbackend.lookupEntities.AntecedentLookup
import com.mianeko.fuzzyinferencesystemsbackend.lookupEntities.PageSettings
import com.mianeko.fuzzyinferencesystemsbackend.lookupEntities.VariableLookup
import org.springframework.stereotype.Service
import java.util.*

interface JobsService {
    fun getAll(systemId: Int, pageSettings: PageSettings): List<JobDTONet>

    fun addJob(jobTemplateDTONet: JobTemplateDTONet): JobDTONet

    fun executeJob(systemId: Int, jobId: UUID): List<OutputResultDTONet>

    fun delete(jobId: UUID)
}

@Service
class JobsServiceImpl(
    private val jobRepository: JobRepository,
    private val systemRepository: SystemRepository,
    private val outputRepository: OutputRepository,
    private val variableRepository: VariableRepository,
    private val antecedentRepository: AntecedentRepository
): JobsService {
    override fun getAll(systemId: Int, pageSettings: PageSettings): List<JobDTONet> {
        if (!systemRepository.idExists(systemId))
            throw SystemNotExistException(systemId)

        return jobRepository.getAll(systemId, pageSettings).map { JobDTONet.fromModel(it.toModel()) }
    }

    override fun addJob(jobTemplateDTONet: JobTemplateDTONet): JobDTONet {
        val job = jobTemplateDTONet.toModel(UUID.randomUUID())

        val antecedentLookup = AntecedentLookup(
            systemId = job.systemId,
            ruleId = null,
            antecedentId = null
        )
        val pageSettings = PageSettings(1, Int.MAX_VALUE)
        val neededVariables =
            antecedentRepository
                .findAll(antecedentLookup, pageSettings)
                .mapNotNull {
                    it.membershipFunction
                        .toModel()
                        .variable
                }
        val neededIds = neededVariables.map { it.id }

        if (!job.inputVariables.keys.containsAll(neededIds))
            throw JobNotEnoughInputParametersException(
                neededVariables
                    .filter { !job.inputVariables.keys.contains(it.id) }
                    .map { it.name }
            )

        job.inputVariables.map {
            val lookup = VariableLookup(
                systemId = job.systemId,
                variableId = it.key
            )
            variableRepository.findOne(lookup)
                ?: throw VariableNotExistException(it.key)
        }

        return JobDTONet.fromModel(
            jobRepository.save(
                JobDTODb.fromModel(job)
            ).toModel()
        )
    }

    override fun executeJob(systemId: Int, jobId: UUID): List<OutputResultDTONet> {
        val job = jobRepository.get(jobId)
            ?: throw JobNotExistException(jobId)

        if (job.systemId != systemId)
            throw JobNotExistException(jobId)

        job.inputVariables.map {
            val lookup = VariableLookup(
                systemId = job.systemId,
                variableId = it.key
            )
            val variable = variableRepository
                .findOne(lookup)
                ?.toModel()
                ?.copy(value = it.value)
                ?: throw VariableNotExistException(it.key)
            variableRepository.save(
                VariableTemplateDTODb.fromModel(
                    variable.toTemplate()
                )
            )
        }
        return outputRepository
            .findAll(job.systemId)
            .map { OutputResultDTONet.fromModel(it.toModel()) }
    }

    override fun delete(jobId: UUID) {
        jobRepository.delete(jobId)
    }

}