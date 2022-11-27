package com.mianeko.fuzzyinferencesystemsbackend.services

import com.mianeko.fuzzyinferencesystemsbackend.database.repositories.AntecedentRepository
import com.mianeko.fuzzyinferencesystemsbackend.database.repositories.RuleRepository
import com.mianeko.fuzzyinferencesystemsbackend.database.repositories.SystemRepository
import com.mianeko.fuzzyinferencesystemsbackend.lookupEntities.PageSettings
import com.mianeko.fuzzyinferencesystemsbackend.objectBuilders.AntecedentLookupBuilder
import com.mianeko.fuzzyinferencesystemsbackend.objectBuilders.DTODb.AntecedentDTODbBuilder
import com.mianeko.fuzzyinferencesystemsbackend.objectBuilders.DTODb.RuleDTODbBuilder
import com.mianeko.fuzzyinferencesystemsbackend.objectBuilders.DTONet.RuleDTONetBuilder
import com.mianeko.fuzzyinferencesystemsbackend.objectBuilders.DTONet.RuleTemplateDTONetBuilder
import com.mianeko.fuzzyinferencesystemsbackend.objectBuilders.RuleLookupBuilder
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import org.mockito.kotlin.any
import org.mockito.kotlin.mock
import org.mockito.kotlin.verify
import org.mockito.kotlin.whenever

internal class RuleServiceImplTest {
    private val antecedentRepository: AntecedentRepository = mock()
    private val systemRepository: SystemRepository = mock()
    private val ruleRepository: RuleRepository = mock()

    private val ruleServiceImpl: RuleServiceImpl = RuleServiceImpl(ruleRepository, antecedentRepository, systemRepository)

    private val defaultPageSettings = PageSettings(1, 10)
    private val ruleLookup =
        RuleLookupBuilder
            .nRuleLookup()
            .withRuleId(1)
            .build()
    private val defaultRuleDTODb = RuleDTODbBuilder.nRuleDTODb().build()
    private val defaultRuleTemplateDTO = RuleTemplateDTONetBuilder.nRuleTemplateDTONet().build()

    init {
        whenever(systemRepository.idExists(1)).thenReturn(true)
        whenever(ruleRepository.idExists(1)).thenReturn(true)
        whenever(antecedentRepository.idExists(1)).thenReturn(true)

        whenever(ruleRepository.save(any())).thenReturn(defaultRuleDTODb)
        whenever(ruleRepository.findOne(ruleLookup)).thenReturn(defaultRuleDTODb)
        whenever(ruleRepository.findAll(any(), any())).thenReturn(listOf(defaultRuleDTODb))
    }

    @Test
    fun create() {
        val templateDTO = defaultRuleTemplateDTO
        val expected = RuleDTONetBuilder.nRuleDTONet().build()
        val got = ruleServiceImpl.create(templateDTO)

        Assertions.assertEquals(expected, got)
        verify(ruleRepository).save(any())
        verify(systemRepository).idExists(1)
    }

    @Test
    fun get() {
        val lookup =
            RuleLookupBuilder
                .nRuleLookup()
                .withRuleId(1)
                .build()

        val got = ruleServiceImpl.get(lookup)
        val expected = RuleDTONetBuilder.nRuleDTONet().withId(1).build()

        Assertions.assertEquals(got, expected)
        verify(ruleRepository).findOne(lookup)
        verify(systemRepository).idExists(1)
    }

    @Test
    fun getAll() {
        val lookup =
            RuleLookupBuilder
                .nRuleLookup()
                .build()

        val expected = listOf(RuleDTONetBuilder.nRuleDTONet().build())
        val got = ruleServiceImpl.getAll(lookup, defaultPageSettings)
        Assertions.assertEquals(expected, got)
        verify(ruleRepository).findAll(lookup, defaultPageSettings)
        verify(systemRepository).idExists(1)
    }

    @Test
    fun update() {
        val antLookup = AntecedentLookupBuilder.nAntecedentLookup().withAntecedentId(1).build()
        whenever(antecedentRepository.findOne(antLookup)).thenReturn(AntecedentDTODbBuilder.nAntecedentDTODb().build())

        val templateDTO =
            RuleTemplateDTONetBuilder
                .nRuleTemplateDTONet()
                .withId(1)
                .build()

        val expected = RuleDTONetBuilder.nRuleDTONet().build()

        try {
            val got = ruleServiceImpl.update(templateDTO)

            Assertions.assertEquals(expected, got)
            verify(ruleRepository).save(any())
            verify(systemRepository).idExists(1)
            verify(ruleRepository).idExists(1)
            verify(ruleRepository).idExists(1)
        } catch (e: Exception) {
            println(e.message)
            assert(false)
        }
    }

    @Test
    fun delete() {
        val lookup = RuleLookupBuilder.nRuleLookup().build()

        ruleRepository.delete(lookup)

        verify(ruleRepository).delete(lookup)
    }
}