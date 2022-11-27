package com.mianeko.fuzzyinferencesystemsbackend.services

import com.mianeko.fuzzyinferencesystemsbackend.database.repositories.MembershipFunctionRepository
import com.mianeko.fuzzyinferencesystemsbackend.database.repositories.SystemRepository
import com.mianeko.fuzzyinferencesystemsbackend.database.repositories.VariableRepository
import com.mianeko.fuzzyinferencesystemsbackend.lookupEntities.PageSettings
import com.mianeko.fuzzyinferencesystemsbackend.objectBuilders.DTODb.MembershipFunctionDTODbBuilder
import com.mianeko.fuzzyinferencesystemsbackend.objectBuilders.DTONet.MembershipFunctionDTONetBuilder
import com.mianeko.fuzzyinferencesystemsbackend.objectBuilders.DTONet.MembershipFunctionTemplateDTONetBuilder
import com.mianeko.fuzzyinferencesystemsbackend.objectBuilders.MembershipFunctionLookupBuilder
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.mockito.kotlin.any
import org.mockito.kotlin.mock
import org.mockito.kotlin.verify
import org.mockito.kotlin.whenever

internal class MembershipFunctionServiceImplTest {
    private val membershipFunctionRepository: MembershipFunctionRepository = mock()
    private val systemRepository: SystemRepository = mock()
    private val variableRepository: VariableRepository = mock()

    private val membershipFunctionServiceImpl: MembershipFunctionServiceImpl = MembershipFunctionServiceImpl(membershipFunctionRepository, systemRepository, variableRepository)

    private val defaultPageSettings = PageSettings(1, 10)
    private val membershipFunctionLookup =
        MembershipFunctionLookupBuilder
            .nMembershipFunctionLookup()
            .withMembershipFunctionId(1)
            .build()
    private val membershipFunctionLookup2 =
        MembershipFunctionLookupBuilder
            .nMembershipFunctionLookup()
            .withMembershipFunctionId(1)
            .withVariableId(1)
            .build()
    private val defaultMembershipFunctionDTODb = MembershipFunctionDTODbBuilder.nMembershipFunctionDTODb().build()
    private val defaultMembershipFunctionTemplateDTO = MembershipFunctionTemplateDTONetBuilder.nMembershipFunctionTemplateDTONet().build()

    init {
        whenever(systemRepository.idExists(1)).thenReturn(true)
        whenever(variableRepository.idExists(1)).thenReturn(true)
        whenever(membershipFunctionRepository.idExists(1)).thenReturn(true)

        whenever(membershipFunctionRepository.save(any())).thenReturn(defaultMembershipFunctionDTODb)
        whenever(membershipFunctionRepository.findOne(membershipFunctionLookup)).thenReturn(defaultMembershipFunctionDTODb)
        whenever(membershipFunctionRepository.findOne(membershipFunctionLookup2)).thenReturn(defaultMembershipFunctionDTODb)
        whenever(membershipFunctionRepository.findAll(any(), any())).thenReturn(listOf(defaultMembershipFunctionDTODb))
    }

    @Test
    fun create() {
        val templateDTO = defaultMembershipFunctionTemplateDTO
        val expected = MembershipFunctionDTONetBuilder.nMembershipFunctionDTONet().build()
        val got = membershipFunctionServiceImpl.create(templateDTO)

        assertEquals(expected, got)
        verify(membershipFunctionRepository).save(any())
        verify(systemRepository).idExists(1)
    }

    @Test
    fun get() {
        val lookup =
            MembershipFunctionLookupBuilder
                .nMembershipFunctionLookup()
                .withMembershipFunctionId(1)
                .withVariableId(1)
                .build()

        val got = membershipFunctionServiceImpl.get(lookup)
        val expected = MembershipFunctionDTONetBuilder.nMembershipFunctionDTONet().withId(1).build()

        assertEquals(got, expected)
        verify(membershipFunctionRepository).findOne(lookup)
        verify(systemRepository).idExists(1)
        verify(variableRepository).idExists(1)
    }

    @Test
    fun getAll() {
        val lookup =
            MembershipFunctionLookupBuilder
                .nMembershipFunctionLookup()
                .build()

        val expected = listOf(MembershipFunctionDTONetBuilder.nMembershipFunctionDTONet().build())
        val got = membershipFunctionServiceImpl.getAll(lookup, defaultPageSettings)
        assertEquals(expected, got)
        verify(membershipFunctionRepository).findAll(lookup, defaultPageSettings)
        verify(systemRepository).idExists(1)
    }

    @Test
    fun update() {
        val templateDTO =
            MembershipFunctionTemplateDTONetBuilder
                .nMembershipFunctionTemplateDTONet()
                .withId(1)
                .build()

        val expected = MembershipFunctionDTONetBuilder.nMembershipFunctionDTONet().build()

        try {
            val got = membershipFunctionServiceImpl.update(templateDTO)

            assertEquals(expected, got)
            verify(membershipFunctionRepository).save(any())
            verify(systemRepository).idExists(1)
            verify(membershipFunctionRepository).idExists(1)
        } catch (e: Exception) {
            println(e.message)
            assert(false)
        }
    }

    @Test
    fun delete() {
        val lookup = MembershipFunctionLookupBuilder.nMembershipFunctionLookup().build()

        membershipFunctionRepository.delete(lookup)

        verify(membershipFunctionRepository).delete(lookup)
    }
}