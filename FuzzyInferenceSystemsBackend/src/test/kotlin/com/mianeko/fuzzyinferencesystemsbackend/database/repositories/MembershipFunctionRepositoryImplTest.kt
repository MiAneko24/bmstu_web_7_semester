package com.mianeko.fuzzyinferencesystemsbackend.database.repositories

import com.mianeko.fuzzyinferencesystemsbackend.DTODb.MembershipFunctionTemplateDTODb
import com.mianeko.fuzzyinferencesystemsbackend.DTODb.enums.LinguisticHedgeTypeDTODb
import com.mianeko.fuzzyinferencesystemsbackend.DTODb.enums.MembershipFunctionTypeDTODb
import com.mianeko.fuzzyinferencesystemsbackend.database.entities.DBMembershipFunction
import com.mianeko.fuzzyinferencesystemsbackend.database.entities.enums.DBLinguisticHedgeType
import com.mianeko.fuzzyinferencesystemsbackend.database.entities.enums.DBMembershipFunctionType
import com.mianeko.fuzzyinferencesystemsbackend.database.repositories.objectBuilders.db.DBMembershipFunctionBuilder
import com.mianeko.fuzzyinferencesystemsbackend.lookupEntities.PageSettings
import com.mianeko.fuzzyinferencesystemsbackend.objectBuilders.DTODb.MembershipFunctionDTODbBuilder
import com.mianeko.fuzzyinferencesystemsbackend.objectBuilders.DTODb.MembershipFunctionTemplateDTODbBuilder
import com.mianeko.fuzzyinferencesystemsbackend.objectBuilders.MembershipFunctionLookupBuilder
import io.ebean.Database
import io.ebean.Query
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import org.mockito.kotlin.any
import org.mockito.kotlin.mock
import org.mockito.kotlin.verify
import org.mockito.kotlin.whenever

internal class MembershipFunctionRepositoryImplTest {
    private val database: Database = mock()
    private val membershipFunctionRepositoryImpl = MembershipFunctionRepositoryImpl(database)
    private val queryMock: Query<DBMembershipFunction> = mock()
    private val defaultMembershipFunction = DBMembershipFunctionBuilder.nDBMembershipFunction().build()
    private val defaultMembershipFunction2 = DBMembershipFunctionBuilder.nDBMembershipFunction().withId(2).build()

    private val sqlOne = "select mf.*\n" +
            "from membership_function mf join variable v on mf.v_id = v.v_id \n" +
            "where v.s_id = ? and mf.m_id = ?\n"
    private val sqlAll = "select mf.* " +
            "from membership_function mf join variable v on mf.v_id = v.v_id " +
            "where v.s_id = ? " +
            "union " +
            "select mf2.* " +
            "from membership_function mf2 join consequent c on c.m_id = mf2.m_id join \"rule\" r on r.r_id = c.r_id " +
            "where mf2.v_id is null and r.s_id = ? "

    init {
        whenever(database.find(DBMembershipFunction::class.java, 1)).thenReturn(defaultMembershipFunction)

        whenever(database.findNative(DBMembershipFunction::class.java, sqlOne)).thenReturn(queryMock)
        whenever(queryMock.setParameter(any<Int>(), any())).thenReturn(queryMock)
        whenever(queryMock.findOne()).thenReturn(defaultMembershipFunction)

        whenever(database.findNative(DBMembershipFunction::class.java, sqlAll)).thenReturn(queryMock)
        whenever(queryMock.setFirstRow(any())).thenReturn(queryMock)
        whenever(queryMock.setMaxRows(any())).thenReturn(queryMock)
        whenever(queryMock.findList()).thenReturn(listOf(defaultMembershipFunction, defaultMembershipFunction2))
    }

    @Test
    fun idExists() {
        val membershipFunctionId = 1

        val expected = true
        val got = membershipFunctionRepositoryImpl.idExists(membershipFunctionId)

        Assertions.assertEquals(expected, got)
        verify(database).find(DBMembershipFunction::class.java, membershipFunctionId)
    }

    @Test
    fun save() {
        val mfTemplate: MembershipFunctionTemplateDTODb = MembershipFunctionTemplateDTODbBuilder
            .nMembershipFunctionTemplateDTODb()
            .withFunctionType(
                MembershipFunctionTypeDTODb.Triangle,
                1.0,
                5.0,
                7.0,
                null,
                null,
                LinguisticHedgeTypeDTODb.Nothing
            )
            .build()

        val expected = MembershipFunctionDTODbBuilder
            .nMembershipFunctionDTODb()
            .withFunctionType(
                MembershipFunctionTypeDTODb.Triangle,
                1.0,
                5.0,
                7.0,
                null,
                null,
                LinguisticHedgeTypeDTODb.Nothing
            )
            .build()

        val updMf = DBMembershipFunctionBuilder
            .nDBMembershipFunction()
            .withFunctionType(
                DBMembershipFunctionType.Triangle,
                1.0,
                5.0,
                7.0,
                null,
                null,
                DBLinguisticHedgeType.Nothing
            )
            .build()

        var mf: DBMembershipFunction? = null
        whenever(database.update(mfTemplate.toModelDb())).then {
            mf = updMf
            return@then Unit
        }

        val saveQueryMock: Query<DBMembershipFunction> = mock()

        whenever(database.findNative(DBMembershipFunction::class.java, sqlOne)).thenReturn(saveQueryMock)
        whenever(saveQueryMock.setParameter(any<Int>(), any())).thenReturn(saveQueryMock)
        whenever(saveQueryMock.findOne()).thenReturn(updMf)

        whenever(
            database.find(DBMembershipFunction::class.java, mfTemplate.id)
        ).thenReturn(mf)

        val got = membershipFunctionRepositoryImpl.save(mfTemplate)

        Assertions.assertEquals(expected, got)
        verify(database).find(DBMembershipFunction::class.java, mfTemplate.id)
        verify(database).update(mfTemplate.toModelDb())

    }

    @Test
    fun delete() {
        val deletingId = 1
        val deletedLookup = MembershipFunctionLookupBuilder
            .nMembershipFunctionLookup()
            .withMembershipFunctionId(deletingId)
            .build()
        whenever(database.delete(DBMembershipFunction::class.java, deletingId)).thenReturn(1)

        membershipFunctionRepositoryImpl.delete(deletedLookup)

        verify(database).delete(DBMembershipFunction::class.java, deletingId)
    }

    @Test
    fun findOne() {
        val idToFind = 1
        val findLookup = MembershipFunctionLookupBuilder
            .nMembershipFunctionLookup()
            .withMembershipFunctionId(idToFind)
            .build()
        val expected = MembershipFunctionDTODbBuilder
            .nMembershipFunctionDTODb()
            .build()

        val got = membershipFunctionRepositoryImpl.findOne(findLookup)

        Assertions.assertEquals(expected, got)
    }

    @Test
    fun findAll() {
        val expected = listOf(
            MembershipFunctionDTODbBuilder.nMembershipFunctionDTODb().build(),
            MembershipFunctionDTODbBuilder.nMembershipFunctionDTODb().withId(2).build())
        val pageSettings = PageSettings(1, 10)
        val lookup = MembershipFunctionLookupBuilder.nMembershipFunctionLookup().build()

        val got = membershipFunctionRepositoryImpl.findAll(lookup, pageSettings)

        Assertions.assertEquals(expected, got)
    }
}