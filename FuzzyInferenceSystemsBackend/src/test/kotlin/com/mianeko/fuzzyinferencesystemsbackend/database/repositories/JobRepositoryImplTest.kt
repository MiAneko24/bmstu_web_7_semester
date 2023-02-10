package com.mianeko.fuzzyinferencesystemsbackend.database.repositories

import com.mianeko.fuzzyinferencesystemsbackend.database.entities.DBJob
import com.mianeko.fuzzyinferencesystemsbackend.database.repositories.objectBuilders.db.DBJobBuilder
import com.mianeko.fuzzyinferencesystemsbackend.lookupEntities.PageSettings
import com.mianeko.fuzzyinferencesystemsbackend.objectBuilders.DTODb.JobDTODbBuilder
import io.ebean.Database
import io.ebean.ExpressionList
import io.ebean.Query
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.mockito.kotlin.*
import java.util.*

class JobRepositoryImplTest {
    private val database: Database = mock()
    private val jobRepositoryImpl: JobRepositoryImpl = JobRepositoryImpl(database)
    private val queryMock: Query<DBJob> = mock()
    private val defaultJob = DBJobBuilder.nDBJob().build()
    private val defaultJob2 = DBJobBuilder.nDBJob().withId(UUID.randomUUID()).build()

    init {
        whenever(database.find(DBJob::class.java)).thenReturn(queryMock)
        whenever(database.find(DBJob::class.java, defaultJob.id)).thenReturn(defaultJob)
    }

    @Test
    fun getAll() {
        val exprMock: ExpressionList<DBJob> = mock()
        whenever(queryMock.where()).thenReturn(exprMock)
        whenever(exprMock.eq(any(), any())).thenReturn(exprMock)
        whenever(exprMock.setFirstRow(any())).thenReturn(exprMock)
        whenever(exprMock.setMaxRows(any())).thenReturn(exprMock)
        whenever(exprMock.findList()).thenReturn(listOf(defaultJob, defaultJob2))

        val expected = listOf(
            JobDTODbBuilder.nJobDTODb().build(),
            JobDTODbBuilder.nJobDTODb().withId(defaultJob2.id).build()
        )
        val pageSettings = PageSettings(10, 10)

        val got = jobRepositoryImpl.getAll(1, pageSettings)

        assertEquals(expected, got)

        verify(exprMock).eq("s_id", 1)
    }

    @Test
    fun get() {
        val expected = JobDTODbBuilder.nJobDTODb().build()
        val got = jobRepositoryImpl.get(defaultJob.id)

        assertEquals(expected, got)

        verify(database).find(DBJob::class.java, defaultJob.id)
    }

    @Test
    fun save() {
        val adding = JobDTODbBuilder.nJobDTODb().withId(UUID.randomUUID()).build()
        var job: DBJob? = null

        whenever(database.save(adding.toModelDb())).then {
            job = DBJobBuilder.nDBJob().withId(adding.id).build()
            return@then Unit
        }

        whenever(database.find(DBJob::class.java, adding.id)).then {
            return@then job
        }

        val got = jobRepositoryImpl.save(adding)

        assertEquals(adding, got)
        verify(database, times(2)).find(DBJob::class.java, adding.id)
        verify(database).save(adding.toModelDb())
    }

    @Test
    fun delete() {
        val id = defaultJob.id
        whenever(database.delete(DBJob::class.java, id)).thenReturn(1)

        jobRepositoryImpl.delete(id)
        verify(database).delete(DBJob::class.java, id)

    }
}