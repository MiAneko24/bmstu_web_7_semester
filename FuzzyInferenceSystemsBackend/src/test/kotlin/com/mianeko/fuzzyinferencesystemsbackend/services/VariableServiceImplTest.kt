package com.mianeko.fuzzyinferencesystemsbackend.services

import com.mianeko.fuzzyinferencesystemsbackend.database.repositories.SystemRepository
import com.mianeko.fuzzyinferencesystemsbackend.database.repositories.VariableRepository
import com.mianeko.fuzzyinferencesystemsbackend.lookupEntities.PageSettings
import com.mianeko.fuzzyinferencesystemsbackend.objectBuilders.DTODb.VariableDTODbBuilder
import com.mianeko.fuzzyinferencesystemsbackend.objectBuilders.DTONet.VariableDTONetBuilder
import com.mianeko.fuzzyinferencesystemsbackend.objectBuilders.DTONet.VariableTemplateDTONetBuilder
import com.mianeko.fuzzyinferencesystemsbackend.objectBuilders.VariableLookupBuilder
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import org.mockito.kotlin.any
import org.mockito.kotlin.mock
import org.mockito.kotlin.verify
import org.mockito.kotlin.whenever

internal class VariableServiceImplTest {
    private val variableRepository: VariableRepository = mock()
    private val systemRepository: SystemRepository = mock()

    private val variableServiceImpl: VariableServiceImpl = VariableServiceImpl(variableRepository, systemRepository)

    private val defaultPageSettings = PageSettings(1, 10)
    private val variableLookup =
        VariableLookupBuilder
            .nVariableLookup()
            .withVariableId(1)
            .build()
    private val defaultVariableDTODb = VariableDTODbBuilder.nVariableDTODb().build()
    private val defaultVariableTemplateDTO = VariableTemplateDTONetBuilder.nVariableTemplateDTONet().build()

    init {
        whenever(systemRepository.idExists(1)).thenReturn(true)
        whenever(variableRepository.idExists(1)).thenReturn(true)

        whenever(variableRepository.save(any())).thenReturn(defaultVariableDTODb)
        whenever(variableRepository.findOne(variableLookup)).thenReturn(defaultVariableDTODb)
        whenever(variableRepository.findAll(any(), any())).thenReturn(listOf(defaultVariableDTODb))
    }

    @Test
    fun create() {
        val templateDTO = defaultVariableTemplateDTO
        val expected = VariableDTONetBuilder.nVariableDTONet().build()
        val got = variableServiceImpl.create(templateDTO)

        Assertions.assertEquals(expected, got)
        verify(variableRepository).save(any())
        verify(systemRepository).idExists(1)
    }

    @Test
    fun get() {
        val lookup =
            VariableLookupBuilder
                .nVariableLookup()
                .withVariableId(1)
                .build()

        val got = variableServiceImpl.get(lookup)
        val expected = VariableDTONetBuilder.nVariableDTONet().withId(1).build()

        Assertions.assertEquals(got, expected)
        verify(variableRepository).findOne(lookup)
        verify(systemRepository).idExists(1)
    }

    @Test
    fun getAll() {
        val lookup =
            VariableLookupBuilder
                .nVariableLookup()
                .build()

        val expected = listOf(VariableDTONetBuilder.nVariableDTONet().build())
        val got = variableServiceImpl.getAll(lookup, defaultPageSettings)
        Assertions.assertEquals(expected, got)
        verify(variableRepository).findAll(lookup, defaultPageSettings)
        verify(systemRepository).idExists(1)
    }

    @Test
    fun update() {
        val templateDTO =
            VariableTemplateDTONetBuilder
                .nVariableTemplateDTONet()
                .withId(1)
                .build()

        val expected = VariableDTONetBuilder.nVariableDTONet().build()

        try {
            val got = variableServiceImpl.update(templateDTO)

            Assertions.assertEquals(expected, got)
            verify(variableRepository).save(any())
            verify(systemRepository).idExists(1)
            verify(variableRepository).idExists(1)
        } catch (e: Exception) {
            println(e.message)
            assert(false)
        }
    }

    @Test
    fun delete() {
        val lookup = VariableLookupBuilder.nVariableLookup().build()

        variableRepository.delete(lookup)

        verify(variableRepository).delete(lookup)
    }
}