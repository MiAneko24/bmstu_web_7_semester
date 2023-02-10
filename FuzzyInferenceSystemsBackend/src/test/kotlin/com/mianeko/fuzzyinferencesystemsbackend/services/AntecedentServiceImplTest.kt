package com.mianeko.fuzzyinferencesystemsbackend.services

import com.mianeko.fuzzyinferencesystemsbackend.database.repositories.AntecedentRepository
import com.mianeko.fuzzyinferencesystemsbackend.database.repositories.RuleRepository
import com.mianeko.fuzzyinferencesystemsbackend.database.repositories.SystemRepository
import com.mianeko.fuzzyinferencesystemsbackend.exceptions.AntecedentNotExistException
import com.mianeko.fuzzyinferencesystemsbackend.exceptions.RuleNotExistException
import com.mianeko.fuzzyinferencesystemsbackend.exceptions.SystemNotExistException
import com.mianeko.fuzzyinferencesystemsbackend.lookupEntities.PageSettings
import com.mianeko.fuzzyinferencesystemsbackend.objectBuilders.AntecedentLookupBuilder
import com.mianeko.fuzzyinferencesystemsbackend.objectBuilders.DTODb.AntecedentDTODbBuilder
import com.mianeko.fuzzyinferencesystemsbackend.objectBuilders.DTONet.AntecedentDTONetBuilder
import com.mianeko.fuzzyinferencesystemsbackend.objectBuilders.DTONet.AntecedentTemplateDTONetBuilder
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.mockito.kotlin.any
import org.mockito.kotlin.mock
import org.mockito.kotlin.verify
import org.mockito.kotlin.whenever

class AntecedentServiceImplTest {
    private val antecedentRepository: AntecedentRepository = mock()
    private val systemRepository: SystemRepository = mock()
    private val ruleRepository: RuleRepository = mock()
    private val antecedentServiceImpl = AntecedentServiceImpl(antecedentRepository, systemRepository, ruleRepository)

    private val defaultPageSettings = PageSettings(1, 10)
    private val antecedentLookup =
        AntecedentLookupBuilder
            .nAntecedentLookup()
            .withAntecedentId(1)
            .build()
    private val defaultAntecedentDTODb = AntecedentDTODbBuilder.nAntecedentDTODb().build()
    private val defaultAntecedentTemplateDTO = AntecedentTemplateDTONetBuilder.nAntecedentTemplateDTO().build()

    init {
        whenever(systemRepository.idExists(1)).thenReturn(true)
        whenever(systemRepository.idExists(5)).thenReturn(false)

        whenever(ruleRepository.idExists(1)).thenReturn(true)
        whenever(ruleRepository.idExists(7)).thenReturn(false)

        whenever(antecedentRepository.idExists(1)).thenReturn(true)

        whenever(antecedentRepository.save(any())).thenReturn(defaultAntecedentDTODb)
        whenever(antecedentRepository.findOne(antecedentLookup)).thenReturn(defaultAntecedentDTODb)
        whenever(antecedentRepository.findAll(any(), any())).thenReturn(listOf(defaultAntecedentDTODb))
    }

    @Test
    fun createSystemNotExist() {
        val templateDTO =
            AntecedentTemplateDTONetBuilder
                .nAntecedentTemplateDTO()
                .withSystemId(5)
                .build()

        try {
            antecedentServiceImpl.create(templateDTO)
            verify(systemRepository).idExists(1)
        } catch (e: SystemNotExistException) {
            assert(true)
            return
        }
        assert(false)
    }

    @Test
    fun createOk() {
        val templateDTO = defaultAntecedentTemplateDTO
        val expected = AntecedentDTONetBuilder.nAntecedentDTONet().build()
        val got = antecedentServiceImpl.create(templateDTO)

        assertEquals(expected, got)
        verify(antecedentRepository).save(any())
        verify(systemRepository).idExists(1)
    }

    @Test
    fun getNoSystem() {
        val lookup =
            AntecedentLookupBuilder
                .nAntecedentLookup()
                .withSystemId(5)
                .withAntecedentId(1)
                .build()

        try {
            antecedentServiceImpl.get(lookup)
            verify(systemRepository).idExists(5)
        } catch (e: SystemNotExistException) {
            assert(true)
            return
        }
        assert(false)
    }


    @Test
    fun getNoRule() {
        val lookup =
            AntecedentLookupBuilder
                .nAntecedentLookup()
                .withRuleId(7)
                .withAntecedentId(1)
                .build()

        try {
            antecedentServiceImpl.get(lookup)
            verify(ruleRepository).idExists(7)
        } catch (e: RuleNotExistException) {
            assert(true)
            return
        }
        assert(false)
    }

    @Test
    fun getOk() {
        val lookup =
            AntecedentLookupBuilder
                .nAntecedentLookup()
                .withAntecedentId(1)
                .build()

        val got = antecedentServiceImpl.get(lookup)
        val expected = AntecedentDTONetBuilder.nAntecedentDTONet().withId(1).build()

        assertEquals(got, expected)
        verify(antecedentRepository).findOne(lookup)
        verify(systemRepository).idExists(1)
    }

    @Test
    fun getAllNoSystem() {
        val lookup =
            AntecedentLookupBuilder
                .nAntecedentLookup()
                .withSystemId(5)
                .build()

        try {
            antecedentServiceImpl.getAll(lookup, defaultPageSettings)
            verify(systemRepository).idExists(5)
        } catch (e: SystemNotExistException) {
            assert(true)
            return
        }
        assert(false)
    }

    @Test
    fun getAllNoRule() {
        val lookup =
            AntecedentLookupBuilder
                .nAntecedentLookup()
                .withRuleId(7)
                .build()

        try {
            antecedentServiceImpl.getAll(lookup, defaultPageSettings)
            verify(ruleRepository).idExists(7)
        } catch (e: RuleNotExistException) {
            assert(true)
            return
        }
        assert(false)
    }

    @Test
    fun getAllOk() {
        val lookup =
            AntecedentLookupBuilder
                .nAntecedentLookup()
                .build()

        val expected = listOf(AntecedentDTONetBuilder.nAntecedentDTONet().build())
        val got = antecedentServiceImpl.getAll(lookup, defaultPageSettings)
        assertEquals(expected, got)
        verify(antecedentRepository).findAll(lookup, defaultPageSettings)
        verify(systemRepository).idExists(1)
    }

    @Test
    fun updateSystemNotExist() {
        val templateDTO =
            AntecedentTemplateDTONetBuilder
                .nAntecedentTemplateDTO()
                .withSystemId(5)
                .build()

        try {
            antecedentServiceImpl.update(templateDTO)
            verify(systemRepository).idExists(5)
        } catch (e: SystemNotExistException) {
            assert(true)
            return
        }
        assert(false)
    }

    @Test
    fun updateAntecedentNotExist() {
        val templateDTO =
            AntecedentTemplateDTONetBuilder
                .nAntecedentTemplateDTO()
                .withId(5)
                .build()

        try {
            antecedentServiceImpl.update(templateDTO)
            verify(antecedentRepository).idExists(5)
            verify(systemRepository).idExists(1)
        } catch (e: AntecedentNotExistException) {
            assert(true)
            return
        }
        assert(false)
    }

    @Test
    fun updateOk() {
        val templateDTO =
            AntecedentTemplateDTONetBuilder
                .nAntecedentTemplateDTO()
                .withId(1)
                .build()

        val expected = AntecedentDTONetBuilder.nAntecedentDTONet().build()

        try {
            val got = antecedentServiceImpl.update(templateDTO)

            assertEquals(expected, got)
            verify(antecedentRepository).save(any())
            verify(antecedentRepository).idExists(1)
            verify(systemRepository).idExists(1)
        } catch (e: Exception) {
            println(e.message)
            assert(false)
        }
    }

    @Test
    fun delete() {
        val lookup = AntecedentLookupBuilder.nAntecedentLookup().build()

        antecedentRepository.delete(lookup)

        verify(antecedentRepository).delete(lookup)
    }
}