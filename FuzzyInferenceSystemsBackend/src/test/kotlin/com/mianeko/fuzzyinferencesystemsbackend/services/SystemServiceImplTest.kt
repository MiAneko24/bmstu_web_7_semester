package com.mianeko.fuzzyinferencesystemsbackend.services

import com.mianeko.fuzzyinferencesystemsbackend.database.repositories.SystemRepository
import com.mianeko.fuzzyinferencesystemsbackend.lookupEntities.PageSettings
import com.mianeko.fuzzyinferencesystemsbackend.objectBuilders.DTODb.SystemDTODbBuilder
import com.mianeko.fuzzyinferencesystemsbackend.objectBuilders.DTONet.SystemDTONetBuilder
import com.mianeko.fuzzyinferencesystemsbackend.objectBuilders.DTONet.SystemTemplateDTONetBuilder
import com.mianeko.fuzzyinferencesystemsbackend.objectBuilders.SystemLookupBuilder
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import org.mockito.kotlin.any
import org.mockito.kotlin.mock
import org.mockito.kotlin.verify
import org.mockito.kotlin.whenever

class SystemServiceImplTest {
    private val systemRepository: SystemRepository = mock()

    private val systemServiceImpl: SystemServiceImpl = SystemServiceImpl(systemRepository)

    private val defaultPageSettings = PageSettings(1, 10)
    private val systemLookup =
        SystemLookupBuilder
            .nSystemLookup()
            .withSystemId(1)
            .build()
    private val defaultSystemDTODb = SystemDTODbBuilder.nSystemDTODb().build()
    private val defaultSystemTemplateDTO = SystemTemplateDTONetBuilder.nSystemTemplateDTONet().build()

    init {
        whenever(systemRepository.idExists(1)).thenReturn(true)

        whenever(systemRepository.save(any())).thenReturn(defaultSystemDTODb)
        whenever(systemRepository.findOne(systemLookup)).thenReturn(defaultSystemDTODb)
        whenever(systemRepository.findAll(any(), any())).thenReturn(listOf(defaultSystemDTODb))
    }

    @Test
    fun create() {
        val templateDTO = defaultSystemTemplateDTO
        val expected = SystemDTONetBuilder.nSystemDTONet().build()
        val got = systemServiceImpl.create(templateDTO)

        Assertions.assertEquals(expected, got)
        verify(systemRepository).save(any())
    }

    @Test
    fun get() {
        val lookup =
            SystemLookupBuilder
                .nSystemLookup()
                .withSystemId(1)
                .build()

        val got = systemServiceImpl.get(lookup)
        val expected = SystemDTONetBuilder.nSystemDTONet().withId(1).build()

        Assertions.assertEquals(got, expected)
        verify(systemRepository).findOne(lookup)
    }

    @Test
    fun getAll() {
        val lookup =
            SystemLookupBuilder
                .nSystemLookup()
                .build()

        val expected = listOf(SystemDTONetBuilder.nSystemDTONet().build())
        val got = systemServiceImpl.getAll(lookup, defaultPageSettings)
        Assertions.assertEquals(expected, got)
        verify(systemRepository).findAll(lookup, defaultPageSettings)
    }

    @Test
    fun update() {
        val templateDTO =
            SystemTemplateDTONetBuilder
                .nSystemTemplateDTONet()
                .withId(1)
                .build()

        val expected = SystemDTONetBuilder.nSystemDTONet().build()

        try {
            val got = systemServiceImpl.update(templateDTO)

            Assertions.assertEquals(expected, got)
            verify(systemRepository).save(any())
            verify(systemRepository).idExists(1)
        } catch (e: Exception) {
            println(e.message)
            assert(false)
        }
    }

    @Test
    fun delete() {
        val lookup = SystemLookupBuilder.nSystemLookup().build()

        systemRepository.delete(lookup)

        verify(systemRepository).delete(lookup)
    }
}