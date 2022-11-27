package com.mianeko.fuzzyinferencesystemsbackend.database.repositories

import com.mianeko.fuzzyinferencesystemsbackend.DTODb.AntecedentDTODb
import com.mianeko.fuzzyinferencesystemsbackend.DTODb.AntecedentTemplateDTODb
import com.mianeko.fuzzyinferencesystemsbackend.DTODb.MembershipFunctionDTODb
import com.mianeko.fuzzyinferencesystemsbackend.database.entities.DBAntecedent
import com.mianeko.fuzzyinferencesystemsbackend.database.repositories.objectBuilders.db.DBAntecedentBuilder
import com.mianeko.fuzzyinferencesystemsbackend.database.repositories.objectBuilders.db.DBMembershipFunctionBuilder
import com.mianeko.fuzzyinferencesystemsbackend.database.repositories.objectBuilders.db.DBSystemBuilder
import com.mianeko.fuzzyinferencesystemsbackend.database.repositories.objectBuilders.db.DBVariableBuilder
import com.mianeko.fuzzyinferencesystemsbackend.lookupEntities.PageSettings
import com.mianeko.fuzzyinferencesystemsbackend.objectBuilders.AntecedentLookupBuilder
import com.mianeko.fuzzyinferencesystemsbackend.objectBuilders.DTODb.AntecedentDTODbBuilder
import com.mianeko.fuzzyinferencesystemsbackend.objectBuilders.DTODb.MembershipFunctionDTODbBuilder
import com.mianeko.fuzzyinferencesystemsbackend.objectBuilders.templates.AntecedentTemplateBuilder
import io.ebean.Database
import io.ebean.Query
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.mockito.kotlin.any
import org.mockito.kotlin.mock
import org.mockito.kotlin.verify
import org.mockito.kotlin.whenever


class AntecedentRepositoryImplTest {
    private val database: Database = mock()
    private val queryMock: Query<DBAntecedent> = mock()
    private val queryMock2: Query<DBAntecedent> = mock()
    private val antecedentRepositoryImpl = AntecedentRepositoryImpl(database)
    private val defaultSystem2 =
        DBSystemBuilder
            .nDBSystem()
            .withId(2)
            .build()
    private val defaultVariable2 =
        DBVariableBuilder
            .nDBVariable()
            .withId(3)
            .withName("Second one")
            .withSystem(defaultSystem2)
            .build()

    private val defaultMembershipFunction2 =
        DBMembershipFunctionBuilder
            .nDBMembershipFunction()
            .withId(2)
            .withTerm("Second func")
            .withVariable(defaultVariable2)
            .build()
    private val defaultAntecedent1 =
        DBAntecedentBuilder
            .nDBAntecedent()
            .build()
    private val defaultAntecedent2 =
        DBAntecedentBuilder
            .nDBAntecedent()
            .withId(2)
            .withMembershipFunction(defaultMembershipFunction2)
            .build()

    private val sqlOne = "select a.* " +
            "from antecedent a join membership_function mf on a.m_id = mf.m_id " +
            "join variable v on v.v_id = mf.v_id " +
            "where v.s_id = ? and a.a_id = ?;"
    private val sqlAllBySystem = "select a.* " +
            "from antecedent a join membership_function mf on a.m_id = mf.m_id " +
            "join variable v on v.v_id = mf.v_id " +
            "where v.s_id = ? "
    private val sqlAllByRule = "select a.* " +
            "from antecedent a " +
            "join rule_antecedents ra on ra.a_id = a.a_id " +
            "join rule r on r.r_id = ra.r_id " +
            "where r.s_id = ? and r.r_id = ? "

    init {
        whenever(database.findNative(DBAntecedent::class.java, sqlOne)).thenReturn(queryMock)
        whenever(queryMock.setParameter(any<Int>(), any())).thenReturn(queryMock)
        whenever(queryMock.findOne()).thenReturn(defaultAntecedent1)

        whenever(database.findNative(DBAntecedent::class.java, sqlAllBySystem)).thenReturn(queryMock2)
        whenever(queryMock2.setParameter(any<Int>(), any())).thenReturn(queryMock2)
        whenever(queryMock2.setMaxRows(10)).thenReturn(queryMock2)
        whenever(queryMock2.setFirstRow(10)).thenReturn(queryMock2)
        whenever(queryMock2.findList()).thenReturn(listOf(defaultAntecedent2))

        whenever(database.findNative(DBAntecedent::class.java, sqlAllByRule)).thenReturn(queryMock)
        whenever(queryMock.setMaxRows(any())).thenReturn(queryMock)
        whenever(queryMock.setFirstRow(any())).thenReturn(queryMock)
        whenever(queryMock.findList()).thenReturn(listOf(defaultAntecedent1))

        whenever(database.find(DBAntecedent::class.java, 1))
            .thenReturn(defaultAntecedent1)
    }


    @Test
    fun idExistsInDatabase() {
        val antecedentId = DBAntecedentBuilder.nDBAntecedent().build().id
        val expected = true
        val got = antecedentRepositoryImpl.idExists(antecedentId)

        assertEquals(expected, got)

        verify(database).find(DBAntecedent::class.java, antecedentId)
    }

    @Test
    fun idNotExistsInDatabase() {
        val antecedentId = 2
        val expected = false
        val got = antecedentRepositoryImpl.idExists(antecedentId)

        assertEquals(expected, got)

        verify(database).find(DBAntecedent::class.java, antecedentId)
    }

    @Test
    fun save() {
        val expected = AntecedentDTODbBuilder
            .nAntecedentDTODb()
            .withId(5)
            .withMembershipFunction(
                MembershipFunctionDTODbBuilder
                    .nMembershipFunctionDTODb()
                    .withId(5)
                    .build()
            )
            .build()

        val updatedAnt = DBAntecedentBuilder
            .nDBAntecedent()
            .withId(5)
            .withMembershipFunction(
                DBMembershipFunctionBuilder
                    .nDBMembershipFunction()
                    .withId(5)
                    .build()
            )
            .build()

        val antecedentTemplate = AntecedentTemplateBuilder
            .nAntecedentTemplate()
            .withId(5)
            .withMembershipFunctionId(5)
            .build()
        val insertableAntecedent = AntecedentTemplateDTODb.fromModel(antecedentTemplate).toModelDb()

        var antecedent: DBAntecedent? = null

        whenever(database.update(insertableAntecedent)).then {
            antecedent = updatedAnt
            return@then Unit
        }

        val saveQueryMock: Query<DBAntecedent> = mock()

        whenever(database.findNative(DBAntecedent::class.java, sqlOne)).thenReturn(saveQueryMock)
        whenever(saveQueryMock.setParameter(any<Int>(), any())).thenReturn(saveQueryMock)
        whenever(saveQueryMock.findOne()).thenReturn(updatedAnt)

        whenever(
            database.find(DBAntecedent::class.java, antecedentTemplate.id)
        ).thenReturn(antecedent)

        val got = antecedentRepositoryImpl.save(AntecedentTemplateDTODb.fromModel(antecedentTemplate))

        assertEquals(expected, got)
        verify(database).find(DBAntecedent::class.java, antecedentTemplate.id)
        verify(database).update(AntecedentTemplateDTODb.fromModel(antecedentTemplate).toModelDb())
    }

    @Test
    fun delete() {
        val deletingId = 1
        val deletedLookup = AntecedentLookupBuilder
            .nAntecedentLookup()
            .withAntecedentId(deletingId)
            .build()
        whenever(database.delete(DBAntecedent::class.java, deletingId)).thenReturn(1)

        antecedentRepositoryImpl.delete(deletedLookup)

        verify(database).delete(DBAntecedent::class.java, deletingId)
    }

    @Test
    fun findOneExisting() {
        val idToFind = 1
        val findLookup = AntecedentLookupBuilder
            .nAntecedentLookup()
            .withAntecedentId(idToFind)
            .build()
        val expected = AntecedentDTODbBuilder
            .nAntecedentDTODb()
            .build()
        val got = antecedentRepositoryImpl.findOne(findLookup)
        assertEquals(expected, got)
    }

    @Test
    fun findOneNotExisting() {
        val idToFind = 1
        val findLookup = AntecedentLookupBuilder
            .nAntecedentLookup()
            .withAntecedentId(idToFind)
            .build()
        val expected: AntecedentDTODb? = null
        whenever(
            database
                .findNative(DBAntecedent::class.java, sqlOne)
                .setParameter(1, idToFind)
                .setParameter(2, idToFind)
                .findOne()
        ).thenReturn(null)
        val got = antecedentRepositoryImpl.findOne(findLookup)
        assertEquals(expected, got)
    }

    @Test
    fun findAllBySystem() {
        val findLookup =
            AntecedentLookupBuilder
                .nAntecedentLookup()
                .withSystemId(2)
                .build()
        val pageSettings = PageSettings(1, 10)
        val expected = arrayListOf(AntecedentDTODbBuilder
            .nAntecedentDTODb()
            .withId(2)
            .withMembershipFunction(MembershipFunctionDTODb.fromModelDb(defaultMembershipFunction2))
            .build())
        val got = antecedentRepositoryImpl.findAll(findLookup, pageSettings)
        assertEquals(expected, got)
    }

    @Test
    fun findAllByRule() {
        val findLookup =
            AntecedentLookupBuilder
                .nAntecedentLookup()
                .withRuleId(1)
                .build()
        val pageSettings = PageSettings(1, 10)
        val expected = listOf(AntecedentDTODbBuilder.nAntecedentDTODb().build())
        val got = antecedentRepositoryImpl.findAll(findLookup, pageSettings)
        assertEquals(expected, got)
    }
}