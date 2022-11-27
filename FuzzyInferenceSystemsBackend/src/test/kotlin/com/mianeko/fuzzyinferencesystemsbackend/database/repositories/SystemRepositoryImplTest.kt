package com.mianeko.fuzzyinferencesystemsbackend.database.repositories

import com.mianeko.fuzzyinferencesystemsbackend.DTODb.SystemTemplateDTODb
import com.mianeko.fuzzyinferencesystemsbackend.database.entities.DBSystem
import com.mianeko.fuzzyinferencesystemsbackend.database.repositories.objectBuilders.db.DBSystemBuilder
import com.mianeko.fuzzyinferencesystemsbackend.lookupEntities.PageSettings
import com.mianeko.fuzzyinferencesystemsbackend.objectBuilders.DTODb.SystemDTODbBuilder
import com.mianeko.fuzzyinferencesystemsbackend.objectBuilders.DTODb.SystemTemplateDTODbBuilder
import com.mianeko.fuzzyinferencesystemsbackend.objectBuilders.SystemLookupBuilder
import io.ebean.Database
import io.ebean.Query
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import org.mockito.kotlin.*

internal class SystemRepositoryImplTest {
    private val database: Database = mock()
    private val systemRepositoryImpl = SystemRepositoryImpl(database)
    private val queryMock: Query<DBSystem> = mock()
    private val defaultSystem = DBSystemBuilder.nDBSystem().build()
    private val defaultSystem2 = DBSystemBuilder.nDBSystem().withId(2).withName("oaoa").build()

    init {
        whenever(database.find(DBSystem::class.java, 1)).thenReturn(defaultSystem)

        whenever(database.find(DBSystem::class.java)).thenReturn(queryMock)
        whenever(queryMock.setMaxRows(any())).thenReturn(queryMock)
        whenever(queryMock.setFirstRow(any())).thenReturn(queryMock)
        whenever(queryMock.findList()).thenReturn(listOf(defaultSystem, defaultSystem2))
    }

    @Test
    fun idExists() {
        val systemId = 1

        val expected = true
        val got = systemRepositoryImpl.idExists(systemId)

        Assertions.assertEquals(expected, got)
        verify(database).find(DBSystem::class.java, systemId)
    }

    @Test
    fun save() {
        val systemTemplate: SystemTemplateDTODb = SystemTemplateDTODbBuilder
            .nSystemTemplateDTODb()
            .withName("oaoa")
            .build()

        val expected = SystemDTODbBuilder
            .nSystemDTODb()
            .withName("oaoa")
            .build()

        val updSystem = DBSystemBuilder
            .nDBSystem()
            .withName("oaoa")
            .build()

        whenever(
            database.find(DBSystem::class.java, systemTemplate.id)
        ).thenReturn(updSystem)

        val got = systemRepositoryImpl.save(systemTemplate)

        Assertions.assertEquals(expected, got)
        verify(database, times(2)).find(DBSystem::class.java, systemTemplate.id)
        verify(database).update(systemTemplate.toModelDb())
    }

    @Test
    fun delete() {
        val deletingId = 1
        val deletedLookup = SystemLookupBuilder
            .nSystemLookup()
            .withSystemId(deletingId)
            .build()
        whenever(database.delete(DBSystem::class.java, deletingId)).thenReturn(1)

        systemRepositoryImpl.delete(deletedLookup)

        verify(database).delete(DBSystem::class.java, deletingId)

    }

    @Test
    fun findOne() {
        val idToFind = 1
        val findLookup = SystemLookupBuilder
            .nSystemLookup()
            .withSystemId(idToFind)
            .build()
        val expected = SystemDTODbBuilder
            .nSystemDTODb()
            .build()

        val got = systemRepositoryImpl.findOne(findLookup)

        Assertions.assertEquals(expected, got)
    }

    @Test
    fun findAll() {
        val expected = listOf(
            SystemDTODbBuilder.nSystemDTODb().build(),
            SystemDTODbBuilder.nSystemDTODb().withId(2).withName("oaoa").build())
        val pageSettings = PageSettings(1, 10)
        val lookup = SystemLookupBuilder.nSystemLookup().build()

        val got = systemRepositoryImpl.findAll(lookup, pageSettings)

        Assertions.assertEquals(expected, got)
    }
}