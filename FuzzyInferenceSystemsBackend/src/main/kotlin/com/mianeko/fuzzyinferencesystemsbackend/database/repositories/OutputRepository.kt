package com.mianeko.fuzzyinferencesystemsbackend.database.repositories

import com.mianeko.fuzzyinferencesystemsbackend.DTODb.OutputResultDTODb
import com.mianeko.fuzzyinferencesystemsbackend.database.entities.DBOutputResult
import io.ebean.Database
import org.springframework.stereotype.Repository

interface OutputRepository {
    fun findAll(systemId: Int): List<OutputResultDTODb>
}

@Repository
class OutputRepositoryImpl(
    val db: Database
): OutputRepository {
    override fun findAll(systemId: Int): List<OutputResultDTODb> {
        val sql = "select * " +
                "from get_output(?);"
        return db.findNative(DBOutputResult::class.java, sql)
                .setParameter(1, systemId)
                .findList()
                .map { OutputResultDTODb.fromModelDb(it) }
    }
}