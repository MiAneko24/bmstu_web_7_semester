package com.mianeko.fuzzyinferencesystemsbackend.database.repositories

import com.mianeko.fuzzyinferencesystemsbackend.DTODb.RuleTemplateDTODb
import com.mianeko.fuzzyinferencesystemsbackend.database.entities.DBRule
import com.mianeko.fuzzyinferencesystemsbackend.database.repositories.objectBuilders.db.DBRuleBuilder
import com.mianeko.fuzzyinferencesystemsbackend.lookupEntities.PageSettings
import com.mianeko.fuzzyinferencesystemsbackend.objectBuilders.DTODb.RuleDTODbBuilder
import com.mianeko.fuzzyinferencesystemsbackend.objectBuilders.DTODb.RuleTemplateDTODbBuilder
import com.mianeko.fuzzyinferencesystemsbackend.objectBuilders.RuleLookupBuilder
import io.ebean.Database
import io.ebean.ExpressionList
import io.ebean.Query
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import org.mockito.kotlin.any
import org.mockito.kotlin.mock
import org.mockito.kotlin.verify
import org.mockito.kotlin.whenever

class RuleRepositoryImplTest {
    private val database: Database = mock()
    private val ruleRepositoryImpl: RuleRepositoryImpl = RuleRepositoryImpl(database)
    private val queryMock: Query<DBRule> = mock()
    private val defaultRule = DBRuleBuilder.nDBRule().build()
    private val defaultRule2 = DBRuleBuilder.nDBRule().withId(2).build()
    private val expressionListMock: ExpressionList<DBRule> = mock()

    init {
        whenever(database.find(DBRule::class.java, 1)).thenReturn(defaultRule)

        whenever(database.find(DBRule::class.java)).thenReturn(queryMock)
        whenever(queryMock.where()).thenReturn(expressionListMock)
        whenever(expressionListMock.eq(any(), any())).thenReturn(expressionListMock)
        whenever(expressionListMock.findOne()).thenReturn(defaultRule)

        whenever(expressionListMock.setMaxRows(any())).thenReturn(expressionListMock)
        whenever(expressionListMock.setFirstRow(any())).thenReturn(expressionListMock)
        whenever(expressionListMock.findList()).thenReturn(listOf(defaultRule, defaultRule2))
    }


    @Test
    fun idExists() {
        val ruleId = 1

        val expected = true
        val got = ruleRepositoryImpl.idExists(ruleId)

        Assertions.assertEquals(expected, got)
        verify(database).find(DBRule::class.java, ruleId)
    }

    @Test
    fun save() {
        val ruleTemplate: RuleTemplateDTODb = RuleTemplateDTODbBuilder
            .nRuleTemplateDTODb()
            .withWeight(0.2)
            .build()

        val expected = RuleDTODbBuilder
            .nRuleDTODb()
            .withWeight(0.2)
            .build()

        val updRule = DBRuleBuilder
            .nDBRule()
            .withWeight(0.2)
            .build()

        var rule: DBRule? = null
        whenever(database.update(ruleTemplate.toModelDb())).then {
            rule = updRule
            return@then Unit
        }

        val saveQueryMock: Query<DBRule> = mock()
        val saveExprMock: ExpressionList<DBRule> = mock()

        whenever(database.find(DBRule::class.java)).thenReturn(saveQueryMock)
        whenever(saveQueryMock.where()).thenReturn(saveExprMock)
        whenever(saveExprMock.eq(any(), any())).thenReturn(saveExprMock)
        whenever(saveExprMock.findOne()).thenReturn(updRule)

        whenever(
            database.find(DBRule::class.java, ruleTemplate.id)
        ).thenReturn(rule)

        val got = ruleRepositoryImpl.save(ruleTemplate)

        Assertions.assertEquals(expected, got)
        verify(database).find(DBRule::class.java, ruleTemplate.id)
        verify(database).update(ruleTemplate.toModelDb())
    }

    @Test
    fun delete() {
        val deletingId = 1
        val deletedLookup = RuleLookupBuilder
            .nRuleLookup()
            .withRuleId(deletingId)
            .build()
        whenever(database.delete(DBRule::class.java, deletingId)).thenReturn(1)

        ruleRepositoryImpl.delete(deletedLookup)

        verify(database).delete(DBRule::class.java, deletingId)
    }

    @Test
    fun findOne() {
        val idToFind = 1
        val findLookup = RuleLookupBuilder
            .nRuleLookup()
            .withRuleId(idToFind)
            .build()
        val expected = RuleDTODbBuilder
            .nRuleDTODb()
            .build()

        val got = ruleRepositoryImpl.findOne(findLookup)

        Assertions.assertEquals(expected, got)
    }

    @Test
    fun findAll() {
        val expected = listOf(
            RuleDTODbBuilder.nRuleDTODb().build(),
            RuleDTODbBuilder.nRuleDTODb().withId(2).build())
        val pageSettings = PageSettings(1, 10)
        val lookup = RuleLookupBuilder.nRuleLookup().build()

        val got = ruleRepositoryImpl.findAll(lookup, pageSettings)

        Assertions.assertEquals(expected, got)
    }
}