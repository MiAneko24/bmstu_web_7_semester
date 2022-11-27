package com.mianeko.fuzzyinferencesystemsbackend.database.repositories

import com.mianeko.fuzzyinferencesystemsbackend.DTODb.ConsequentTemplateDTODb
import com.mianeko.fuzzyinferencesystemsbackend.database.entities.DBConsequent
import com.mianeko.fuzzyinferencesystemsbackend.database.repositories.objectBuilders.db.DBConsequentBuilder
import com.mianeko.fuzzyinferencesystemsbackend.database.repositories.objectBuilders.db.DBRuleBuilder
import com.mianeko.fuzzyinferencesystemsbackend.lookupEntities.PageSettings
import com.mianeko.fuzzyinferencesystemsbackend.objectBuilders.ConsequentLookupBuilder
import com.mianeko.fuzzyinferencesystemsbackend.objectBuilders.DTODb.ConsequentDTODbBuilder
import com.mianeko.fuzzyinferencesystemsbackend.objectBuilders.DTODb.ConsequentTemplateDTODbBuilder
import io.ebean.Database
import io.ebean.Query
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.mockito.kotlin.any
import org.mockito.kotlin.mock
import org.mockito.kotlin.verify
import org.mockito.kotlin.whenever

class ConsequentRepositoryImplTest {
    private val database: Database = mock()
    private val consequentRepositoryImpl = ConsequentRepositoryImpl(database)
    private val queryMock: Query<DBConsequent> = mock()
    private val defaultConsequent = DBConsequentBuilder.nDBConsequent().build()
    private val defaultConsequent2 = DBConsequentBuilder.nDBConsequent().withId(2).build()
    private val sqlOne = "select c.* " +
            "from consequent c join rule r on r.r_id = c.r_id " +
            "where r.s_id = ? and c.c_id = ? and r.r_id = ?;"

    private val sqlAll = "select c.* " +
            "from consequent c join rule r on r.r_id = c.r_id " +
            "where r.s_id = ? and r.r_id = ? "

    init {
        whenever(database.find(DBConsequent::class.java, 1)).thenReturn(defaultConsequent)

        whenever(database.findNative(DBConsequent::class.java, sqlOne)).thenReturn(queryMock)
        whenever(queryMock.setParameter(any<Int>(), any())).thenReturn(queryMock)
        whenever(queryMock.findOne()).thenReturn(defaultConsequent)

        whenever(database.findNative(DBConsequent::class.java, sqlAll)).thenReturn(queryMock)
        whenever(queryMock.setMaxRows(any())).thenReturn(queryMock)
        whenever(queryMock.setFirstRow(any())).thenReturn(queryMock)
        whenever(queryMock.findList()).thenReturn(listOf(defaultConsequent, defaultConsequent2))
    }

    @Test
    fun idExists() {
        val consequentId = 1

        val expected = true
        val got = consequentRepositoryImpl.idExists(consequentId)

        assertEquals(expected, got)
        verify(database).find(DBConsequent::class.java, consequentId)
    }

    @Test
    fun save() {
        val consTemplate: ConsequentTemplateDTODb = ConsequentTemplateDTODbBuilder
            .nConsequentTemplateDTODb()
            .withRuleId(5)
            .build()

        val expected = ConsequentDTODbBuilder
            .nConsequentDTODb()
            .withRuleId(5)
            .build()

        val updCons = DBConsequentBuilder
            .nDBConsequent()
            .withRule(DBRuleBuilder.nDBRule().withId(5).build())
            .build()

        var cons: DBConsequent? = null
        whenever(database.update(consTemplate.toModelDb())).then {
            cons = updCons
            return@then Unit
        }

        val saveQueryMock: Query<DBConsequent> = mock()

        whenever(database.findNative(DBConsequent::class.java, sqlOne)).thenReturn(saveQueryMock)
        whenever(saveQueryMock.setParameter(any<Int>(), any())).thenReturn(saveQueryMock)
        whenever(saveQueryMock.findOne()).thenReturn(updCons)

        whenever(
            database.find(DBConsequent::class.java, consTemplate.id)
        ).thenReturn(cons)

        val got = consequentRepositoryImpl.save(consTemplate)

        assertEquals(expected, got)
        verify(database).find(DBConsequent::class.java, consTemplate.id)
        verify(database).update(consTemplate.toModelDb())
    }

    @Test
    fun delete() {
        val deletingId = 1
        val deletedLookup = ConsequentLookupBuilder
            .nConsequentLookup()
            .withConsequentId(deletingId)
            .build()
        whenever(database.delete(DBConsequent::class.java, deletingId)).thenReturn(1)

        consequentRepositoryImpl.delete(deletedLookup)

        verify(database).delete(DBConsequent::class.java, deletingId)

    }

    @Test
    fun findOne() {
        val idToFind = 1
        val findLookup = ConsequentLookupBuilder
            .nConsequentLookup()
            .withConsequentId(idToFind)
            .build()
        val expected = ConsequentDTODbBuilder
            .nConsequentDTODb()
            .build()

        val got = consequentRepositoryImpl.findOne(findLookup)

        assertEquals(expected, got)
    }

    @Test
    fun findAll() {
        val expected = listOf(ConsequentDTODbBuilder.nConsequentDTODb().build(),
            ConsequentDTODbBuilder.nConsequentDTODb().withId(2).build())
        val pageSettings = PageSettings(1, 10)
        val lookup = ConsequentLookupBuilder.nConsequentLookup().build()

        val got = consequentRepositoryImpl.findAll(lookup, pageSettings)

        assertEquals(expected, got)
    }
}