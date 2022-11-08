package com.mianeko.fuzzyinferencesystemsbackend.database.repositories

import com.mianeko.fuzzyinferencesystemsbackend.DTODb.ConsequentDTODb
import com.mianeko.fuzzyinferencesystemsbackend.DTODb.ConsequentTemplateDTODb
import com.mianeko.fuzzyinferencesystemsbackend.database.entities.DBConsequent
import com.mianeko.fuzzyinferencesystemsbackend.exceptions.ConsequentSaveException
import com.mianeko.fuzzyinferencesystemsbackend.exceptions.SystemNotExistException
import com.mianeko.fuzzyinferencesystemsbackend.lookupEntities.ConsequentLookup
import com.mianeko.fuzzyinferencesystemsbackend.lookupEntities.PageSettings
import io.ebean.Database
import org.springframework.stereotype.Repository

interface ConsequentRepository : DatabaseRepository<ConsequentDTODb, ConsequentTemplateDTODb, ConsequentLookup>

@Repository
class ConsequentRepositoryImpl(
    val db: Database
) : ConsequentRepository {
    override fun idExists(id: Int) =
        db.find(DBConsequent::class.java, id) != null

    override fun save(model: ConsequentTemplateDTODb): ConsequentDTODb {
        val lookup = ConsequentLookup(
            systemId = model.systemId,
            ruleId = model.ruleId,
            consequentId = model.id
        )

        val dbModel = findOne(lookup)
        if (idExists(model.id) && dbModel == null)
            throw SystemNotExistException(model.systemId)

        try {
            if (dbModel == null)
                db.save(model.toModelDb())
            else
                db.update(model.toModelDb())
        } catch (e: Exception) {
            throw ConsequentSaveException(model.id)
        }

        return findOne(lookup)
            ?: throw ConsequentSaveException(model.id)
    }

    override fun delete(lookup: ConsequentLookup) {
        lookup.consequentId?.let {
            db.delete(DBConsequent::class.java, it)
        }
    }

    override fun findOne(lookup: ConsequentLookup): ConsequentDTODb? {
        val sql = "select c.* " +
                "from consequent c join rule r on r.r_id = c.r_id " +
                "where r.s_id = ? and c.c_id = ? and r.r_id = ?;"
        return lookup.consequentId?.let { id ->
            db.findNative(DBConsequent::class.java, sql)
                .setParameter(1, lookup.systemId)
                .setParameter(2, id)
                .setParameter(3, lookup.ruleId)
                .findOne()
                ?.let { ConsequentDTODb.fromModelDb(it) }
        }
    }

    override fun findAll(lookup: ConsequentLookup, pageSettings: PageSettings): List<ConsequentDTODb> {
        val sql = "select c.* " +
                "from consequent c join rule r on r.r_id = c.r_id " +
                "where r.s_id = ? and r.r_id = ? "
        return db.findNative(DBConsequent::class.java, sql)
            .setParameter(1, lookup.systemId)
            .setParameter(2, lookup.ruleId)
            .setMaxRows(pageSettings.size)
            .setFirstRow(pageSettings.page * pageSettings.size)
            .findList()
            .map { ConsequentDTODb.fromModelDb(it) }
    }
}