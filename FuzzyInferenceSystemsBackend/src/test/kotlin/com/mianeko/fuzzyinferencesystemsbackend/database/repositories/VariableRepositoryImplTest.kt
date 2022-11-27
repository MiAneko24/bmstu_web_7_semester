package com.mianeko.fuzzyinferencesystemsbackend.database.repositories

import com.mianeko.fuzzyinferencesystemsbackend.DTODb.VariableTemplateDTODb
import com.mianeko.fuzzyinferencesystemsbackend.database.entities.DBVariable
import com.mianeko.fuzzyinferencesystemsbackend.database.repositories.objectBuilders.db.DBVariableBuilder
import com.mianeko.fuzzyinferencesystemsbackend.lookupEntities.PageSettings
import com.mianeko.fuzzyinferencesystemsbackend.objectBuilders.DTODb.VariableDTODbBuilder
import com.mianeko.fuzzyinferencesystemsbackend.objectBuilders.DTODb.VariableTemplateDTODbBuilder
import com.mianeko.fuzzyinferencesystemsbackend.objectBuilders.VariableLookupBuilder
import io.ebean.Database
import io.ebean.ExpressionList
import io.ebean.Query
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import org.mockito.kotlin.any
import org.mockito.kotlin.mock
import org.mockito.kotlin.verify
import org.mockito.kotlin.whenever

internal class VariableRepositoryImplTest {
    private val database: Database = mock()
    private val variableRepositoryImpl: VariableRepositoryImpl = VariableRepositoryImpl(database)
    private val queryMock: Query<DBVariable> = mock()
    private val defaultVariable = DBVariableBuilder.nDBVariable().build()
    private val defaultVariable2 = DBVariableBuilder.nDBVariable().withId(2).build()
    private val expressionListMock: ExpressionList<DBVariable> = mock()

    init {
        whenever(database.find(DBVariable::class.java, 1)).thenReturn(defaultVariable)

        whenever(database.find(DBVariable::class.java)).thenReturn(queryMock)
        whenever(queryMock.where()).thenReturn(expressionListMock)
        whenever(expressionListMock.eq(any(), any())).thenReturn(expressionListMock)
        whenever(expressionListMock.findOne()).thenReturn(defaultVariable)

        whenever(expressionListMock.setMaxRows(any())).thenReturn(expressionListMock)
        whenever(expressionListMock.setFirstRow(any())).thenReturn(expressionListMock)
        whenever(expressionListMock.findList()).thenReturn(listOf(defaultVariable, defaultVariable2))
    }


    @Test
    fun idExists() {
        val variableId = 1

        val expected = true
        val got = variableRepositoryImpl.idExists(variableId)

        Assertions.assertEquals(expected, got)
        verify(database).find(DBVariable::class.java, variableId)
    }

    @Test
    fun save() {
        val variableTemplate: VariableTemplateDTODb = VariableTemplateDTODbBuilder
            .nVariableTemplateDTODb()
            .withName("aaaa")
            .build()

        val expected = VariableDTODbBuilder
            .nVariableDTODb()
            .withName("aaaa")
            .build()

        val updVariable = DBVariableBuilder
            .nDBVariable()
            .withName("aaaa")
            .build()

        var variable: DBVariable? = null
        whenever(database.update(variableTemplate.toModelDb())).then {
            variable = updVariable
            return@then Unit
        }

        val saveQueryMock: Query<DBVariable> = mock()
        val saveExprMock: ExpressionList<DBVariable> = mock()

        whenever(database.find(DBVariable::class.java)).thenReturn(saveQueryMock)
        whenever(saveQueryMock.where()).thenReturn(saveExprMock)
        whenever(saveExprMock.eq(any(), any())).thenReturn(saveExprMock)
        whenever(saveExprMock.findOne()).thenReturn(updVariable)

        whenever(
            database.find(DBVariable::class.java, variableTemplate.id)
        ).thenReturn(variable)

        val got = variableRepositoryImpl.save(variableTemplate)

        Assertions.assertEquals(expected, got)
        verify(database).find(DBVariable::class.java, variableTemplate.id)
        verify(database).update(variableTemplate.toModelDb())
    }

    @Test
    fun delete() {
        val deletingId = 1
        val deletedLookup = VariableLookupBuilder
            .nVariableLookup()
            .withVariableId(deletingId)
            .build()
        whenever(database.delete(DBVariable::class.java, deletingId)).thenReturn(1)

        variableRepositoryImpl.delete(deletedLookup)

        verify(database).delete(DBVariable::class.java, deletingId)
    }

    @Test
    fun findOne() {
        val idToFind = 1
        val findLookup = VariableLookupBuilder
            .nVariableLookup()
            .withVariableId(idToFind)
            .build()
        val expected = VariableDTODbBuilder
            .nVariableDTODb()
            .build()

        val got = variableRepositoryImpl.findOne(findLookup)

        Assertions.assertEquals(expected, got)
    }

    @Test
    fun findAll() {
        val expected = listOf(
            VariableDTODbBuilder.nVariableDTODb().build(),
            VariableDTODbBuilder.nVariableDTODb().withId(2).build())
        val pageSettings = PageSettings(1, 10)
        val lookup = VariableLookupBuilder.nVariableLookup().build()

        val got = variableRepositoryImpl.findAll(lookup, pageSettings)

        Assertions.assertEquals(expected, got)
    }
}