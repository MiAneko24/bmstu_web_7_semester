package com.mianeko.fuzzyinferencesystemsbackend.jobs

import com.mianeko.fuzzyinferencesystemsbackend.database.repositories.*
import com.mianeko.fuzzyinferencesystemsbackend.exceptions.JobNotEnoughInputParametersException
import com.mianeko.fuzzyinferencesystemsbackend.lookupEntities.PageSettings
import com.mianeko.fuzzyinferencesystemsbackend.objectBuilders.AntecedentLookupBuilder
import com.mianeko.fuzzyinferencesystemsbackend.objectBuilders.DTODb.*
import com.mianeko.fuzzyinferencesystemsbackend.objectBuilders.DTONet.JobDTONetBuilder
import com.mianeko.fuzzyinferencesystemsbackend.objectBuilders.DTONet.JobTemplateDTONetBuilder
import com.mianeko.fuzzyinferencesystemsbackend.objectBuilders.DTONet.OutputResultDTONetBuilder
import com.mianeko.fuzzyinferencesystemsbackend.objectBuilders.VariableLookupBuilder
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.mockito.kotlin.any
import org.mockito.kotlin.mock
import org.mockito.kotlin.verify
import org.mockito.kotlin.whenever
import java.util.*

internal class JobsServiceImplTest {
    private val jobRepository: JobRepository = mock()
    private val systemRepository: SystemRepository = mock()
    private val outputRepository: OutputRepository = mock()
    private val variableRepository: VariableRepository = mock()
    private val antecedentRepository: AntecedentRepository = mock()
    private val jobsServiceImpl = JobsServiceImpl(jobRepository, systemRepository, outputRepository,
        variableRepository, antecedentRepository)

    private val defaultPageSettings = PageSettings(1, Int.MAX_VALUE)
    private val varLookup = VariableLookupBuilder.nVariableLookup().withVariableId(1).build()
    private val variable = VariableDTODbBuilder.nVariableDTODb().build()

    init {
        whenever(variableRepository.findOne(varLookup)).thenReturn(variable)

        whenever(systemRepository.idExists(1)).thenReturn(true)
    }

    @Test
    fun getAll() {
        val job1 = JobDTODbBuilder.nJobDTODb().build()
        val job2 = JobDTODbBuilder.nJobDTODb().withId(UUID.randomUUID()).build()
        whenever(jobRepository.getAll(1, defaultPageSettings)).thenReturn(listOf(job1, job2))

        val expected = listOf(
            JobDTONetBuilder.nJobDTONet().build(),
            JobDTONetBuilder.nJobDTONet().withId(job2.id).build()
        )

        val got = jobsServiceImpl.getAll(1, defaultPageSettings)

        assertEquals(expected, got)
        verify(systemRepository).idExists(1)
        verify(jobRepository).getAll(1, defaultPageSettings)
    }

    @Test
    fun addJob() {
        val antLookup = AntecedentLookupBuilder.nAntecedentLookup().build()
        val ant = AntecedentDTODbBuilder.nAntecedentDTODb().build()
        whenever(antecedentRepository.findAll(antLookup, defaultPageSettings)).thenReturn(listOf(ant))

        whenever(jobRepository.save(any())).thenReturn(JobDTODbBuilder.nJobDTODb().build())

        val expected = JobDTONetBuilder.nJobDTONet().build()

        val got = jobsServiceImpl.addJob(JobTemplateDTONetBuilder.nJobTemplateDTONet().build())

        assertEquals(expected.inputVariables, got.inputVariables)
        assertEquals(expected.systemId, got.systemId)

        verify(antecedentRepository).findAll(antLookup, defaultPageSettings)
        verify(variableRepository).findOne(varLookup)
        verify(jobRepository).save(any())
    }

    @Test
    fun addJobNotEnoughVariables() {
        val antLookup = AntecedentLookupBuilder.nAntecedentLookup().build()
        val ant = AntecedentDTODbBuilder.nAntecedentDTODb().build()
        val variable2 = VariableDTODbBuilder.nVariableDTODb().withId(2).build()
        val ant2 = AntecedentDTODbBuilder
            .nAntecedentDTODb()
            .withMembershipFunction(
                MembershipFunctionDTODbBuilder
                    .nMembershipFunctionDTODb()
                    .withVariable(variable2)
                    .build())
            .build()

        whenever(antecedentRepository.findAll(antLookup, defaultPageSettings)).thenReturn(listOf(ant, ant2))

        val varLookup2 = VariableLookupBuilder.nVariableLookup().withVariableId(2).build()
        whenever(variableRepository.findOne(varLookup2)).thenReturn(variable2)

        whenever(jobRepository.save(any())).thenReturn(JobDTODbBuilder.nJobDTODb().build())

        try {
            jobsServiceImpl.addJob(JobTemplateDTONetBuilder.nJobTemplateDTONet().build())
            assert(false)
        } catch (e: JobNotEnoughInputParametersException) {
            assert(true)
        }
    }

    @Test
    fun executeJob() {
        val job = JobDTODbBuilder.nJobDTODb().build()
        whenever(jobRepository.get(job.id)).thenReturn(job)

        val varTemplate = VariableTemplateDTODbBuilder
            .nVariableTemplateDTODb()
            .withValue(5.0)
            .build()
        whenever(variableRepository.save(varTemplate))
            .thenReturn(variable.copy(value = 5.0))

        whenever(outputRepository.findAll(job.systemId)).thenReturn(
            listOf(OutputResultDTODbBuilder.nOutputResultDTODb().build())
        )

        val expected = listOf(OutputResultDTONetBuilder.nOutputResultDTONet().build())

        val got = jobsServiceImpl.executeJob(1, job.id)

        assertEquals(expected, got)

        verify(jobRepository).get(job.id)
        verify(variableRepository).findOne(varLookup)
        verify(variableRepository).save(varTemplate)
        verify(outputRepository).findAll(1)
    }

    @Test
    fun delete() {
        val jobId = JobDTODbBuilder.nJobDTODb().build().id

        jobsServiceImpl.delete(jobId)

        verify(jobRepository).delete(jobId)
    }
}