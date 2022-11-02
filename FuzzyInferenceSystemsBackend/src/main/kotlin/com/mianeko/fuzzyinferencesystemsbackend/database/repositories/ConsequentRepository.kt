package com.mianeko.fuzzyinferencesystemsbackend.database.repositories

import com.fasterxml.jackson.databind.ObjectMapper
import com.mianeko.fuzzyinferencesystemsbackend.database.entities.DBConsequent
import com.mianeko.fuzzyinferencesystemsbackend.database.entities.DBInsertableConsequent
import com.mianeko.fuzzyinferencesystemsbackend.exceptions.*
import com.mianeko.fuzzyinferencesystemsbackend.lookupEntities.ConsequentLookup
import com.mianeko.fuzzyinferencesystemsbackend.lookupEntities.PageSettings
import com.mianeko.fuzzyinferencesystemsbackend.services.models.Consequent
import com.mianeko.fuzzyinferencesystemsbackend.services.models.ConsequentTemplate
import io.ebean.Database
import org.springdoc.core.converters.models.Pageable
import org.springframework.boot.autoconfigure.data.web.SpringDataWebProperties
import org.springframework.stereotype.Repository

interface ConsequentRepository : DatabaseRepository<Consequent, ConsequentTemplate, ConsequentLookup>

@Repository
class ConsequentRepositoryImpl(
    val db: Database,
    val mapper: ObjectMapper
) : ConsequentRepository {
    override fun idExists(id: Int) =
        db.find(DBConsequent::class.java, id) != null

    override fun save(model: ConsequentTemplate): Consequent {
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
                db.save(DBInsertableConsequent.fromModel(model))
            else
                db.update(DBInsertableConsequent.fromModel(model))
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

    override fun findOne(lookup: ConsequentLookup): Consequent? {
        val sql = "select c.* " +
                "from consequent c join rule r on r.r_id = c.r_id " +
                "where r.s_id = ? and c.c_id = ? and r.r_id = ?;"
        return lookup.consequentId?.let {
            db.findNative(DBConsequent::class.java, sql)
                .setParameter(1, lookup.systemId)
                .setParameter(2, it)
                .setParameter(3, lookup.ruleId)
                .findOne()
                ?.toModel()
        }
    }

    override fun findAll(lookup: ConsequentLookup, pageSettings: PageSettings): List<Consequent> {
        val sql = "select c.* " +
                "from consequent c join rule r on r.r_id = c.r_id " +
                "where r.s_id = ? and r.r_id = ?;"
        return db.findNative(DBConsequent::class.java, sql)
            .setParameter(1, lookup.systemId)
            .setParameter(2, lookup.ruleId)
            .setMaxRows(pageSettings.size)
            .setFirstRow(pageSettings.page * pageSettings.size)
            .findList()
            .map { it.toModel() }
    }
}