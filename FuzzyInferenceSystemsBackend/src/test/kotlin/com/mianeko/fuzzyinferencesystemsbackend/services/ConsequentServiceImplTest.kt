package com.mianeko.fuzzyinferencesystemsbackend.services

import com.mianeko.fuzzyinferencesystemsbackend.database.repositories.ConsequentRepository
import com.mianeko.fuzzyinferencesystemsbackend.database.repositories.RuleRepository
import com.mianeko.fuzzyinferencesystemsbackend.database.repositories.SystemRepository
import com.mianeko.fuzzyinferencesystemsbackend.lookupEntities.PageSettings
import com.mianeko.fuzzyinferencesystemsbackend.objectBuilders.ConsequentLookupBuilder
import com.mianeko.fuzzyinferencesystemsbackend.objectBuilders.DTODb.ConsequentDTODbBuilder
import com.mianeko.fuzzyinferencesystemsbackend.objectBuilders.DTONet.ConsequentDTONetBuilder
import com.mianeko.fuzzyinferencesystemsbackend.objectBuilders.DTONet.ConsequentTemplateDTONetBuilder
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.mockito.kotlin.any
import org.mockito.kotlin.mock
import org.mockito.kotlin.verify
import org.mockito.kotlin.whenever

internal class ConsequentServiceImplTest {
    private val consequentRepository: ConsequentRepository = mock()
    private val systemRepository: SystemRepository = mock()
    private val ruleRepository: RuleRepository = mock()

    private val consequentServiceImpl: ConsequentServiceImpl = ConsequentServiceImpl(consequentRepository, systemRepository, ruleRepository)

    private val defaultPageSettings = PageSettings(1, 10)
    private val consequentLookup =
        ConsequentLookupBuilder
            .nConsequentLookup()
            .withConsequentId(1)
            .build()
    private val defaultConsequentDTODb = ConsequentDTODbBuilder.nConsequentDTODb().build()
    private val defaultConsequentTemplateDTO = ConsequentTemplateDTONetBuilder.nConsequentTemplateDTONet().build()

    init {
        whenever(systemRepository.idExists(1)).thenReturn(true)
        whenever(ruleRepository.idExists(1)).thenReturn(true)
        whenever(consequentRepository.idExists(1)).thenReturn(true)

        whenever(consequentRepository.save(any())).thenReturn(defaultConsequentDTODb)
        whenever(consequentRepository.findOne(consequentLookup)).thenReturn(defaultConsequentDTODb)
        whenever(consequentRepository.findAll(any(), any())).thenReturn(listOf(defaultConsequentDTODb))
    }
    
    @Test
    fun create() {
        val templateDTO = defaultConsequentTemplateDTO
        val expected = ConsequentDTONetBuilder.nConsequentDTONet().build()
        val got = consequentServiceImpl.create(templateDTO)

        assertEquals(expected, got)
        verify(consequentRepository).save(any())
        verify(systemRepository).idExists(1)
        verify(ruleRepository).idExists(1)
    }

    @Test
    fun get() {
        val lookup =
            ConsequentLookupBuilder
                .nConsequentLookup()
                .withConsequentId(1)
                .build()

        val got = consequentServiceImpl.get(lookup)
        val expected = ConsequentDTONetBuilder.nConsequentDTONet().withId(1).build()

        assertEquals(got, expected)
        verify(consequentRepository).findOne(lookup)
        verify(systemRepository).idExists(1)
        verify(ruleRepository).idExists(1)
    }

    @Test
    fun getAll() {
        val lookup =
            ConsequentLookupBuilder
                .nConsequentLookup()
                .build()

        val expected = listOf(ConsequentDTONetBuilder.nConsequentDTONet().build())
        val got = consequentServiceImpl.getAll(lookup, defaultPageSettings)
        assertEquals(expected, got)
        verify(consequentRepository).findAll(lookup, defaultPageSettings)
        verify(systemRepository).idExists(1)
        verify(ruleRepository).idExists(1)
    }

    @Test
    fun update() {
        val templateDTO =
            ConsequentTemplateDTONetBuilder
                .nConsequentTemplateDTONet()
                .withId(1)
                .build()

        val expected = ConsequentDTONetBuilder.nConsequentDTONet().build()

        try {
            val got = consequentServiceImpl.update(templateDTO)

            assertEquals(expected, got)
            verify(consequentRepository).save(any())
            verify(systemRepository).idExists(1)
            verify(ruleRepository).idExists(1)
            verify(consequentRepository).idExists(1)
        } catch (e: Exception) {
            println(e.message)
            assert(false)
        }
    }

    @Test
    fun delete() {
        val lookup = ConsequentLookupBuilder.nConsequentLookup().build()

        consequentRepository.delete(lookup)

        verify(consequentRepository).delete(lookup)
    }
}