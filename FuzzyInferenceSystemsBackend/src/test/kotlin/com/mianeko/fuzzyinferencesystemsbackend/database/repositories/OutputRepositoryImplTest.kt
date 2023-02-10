package com.mianeko.fuzzyinferencesystemsbackend.database.repositories

import com.mianeko.fuzzyinferencesystemsbackend.database.entities.DBOutputResult
import com.mianeko.fuzzyinferencesystemsbackend.database.repositories.objectBuilders.db.DBOutputResultBuilder
import com.mianeko.fuzzyinferencesystemsbackend.objectBuilders.DTODb.OutputResultDTODbBuilder
import io.ebean.Database
import io.ebean.Query
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.mockito.kotlin.any
import org.mockito.kotlin.mock
import org.mockito.kotlin.verify
import org.mockito.kotlin.whenever

class OutputRepositoryImplTest {
    private val database: Database = mock()
    private val outputRepositoryImpl = OutputRepositoryImpl(database)

    @Test
    fun findAll() {
        val mockQuery: Query<DBOutputResult> = mock()
        val sql = "select * " +
                "from get_output(?);"

        whenever(database.findNative(DBOutputResult::class.java, sql)).thenReturn(mockQuery)
        whenever(mockQuery.setParameter(any<Int>(), any())).thenReturn(mockQuery)
        whenever(mockQuery.findList()).thenReturn(
            listOf(
                DBOutputResultBuilder.nDBOutputResult().build(),
                DBOutputResultBuilder.nDBOutputResult().withVariableName("aaaa").withValue(1.0).build()
            )
        )

        val expected = listOf(
            OutputResultDTODbBuilder.nOutputResultDTODb().build(),
            OutputResultDTODbBuilder.nOutputResultDTODb().withVariableName("aaaa").withValue(1.0).build()
        )

        val got = outputRepositoryImpl.findAll(1)

        assertEquals(expected, got)
        verify(database).findNative(DBOutputResult::class.java, sql)
        verify(mockQuery).setParameter(1, 1)
    }
}