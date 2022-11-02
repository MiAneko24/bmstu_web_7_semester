package com.mianeko.fuzzyinferencesystemsbackend.database.repositories

import com.fasterxml.jackson.databind.ObjectMapper
import com.mianeko.fuzzyinferencesystemsbackend.database.entities.DBAntecedent
import com.mianeko.fuzzyinferencesystemsbackend.database.entities.DBInsertableAntecedent
import com.mianeko.fuzzyinferencesystemsbackend.exceptions.*
import com.mianeko.fuzzyinferencesystemsbackend.lookupEntities.AntecedentLookup
import com.mianeko.fuzzyinferencesystemsbackend.lookupEntities.PageSettings
import com.mianeko.fuzzyinferencesystemsbackend.services.models.Antecedent
import com.mianeko.fuzzyinferencesystemsbackend.services.models.AntecedentTemplate
import io.ebean.Database
import org.springdoc.core.converters.models.Pageable
import org.springframework.boot.autoconfigure.data.web.SpringDataWebProperties
import org.springframework.stereotype.Repository
import java.lang.Exception

interface AntecedentRepository : DatabaseRepository<Antecedent, AntecedentTemplate, AntecedentLookup>

@Repository
class AntecedentRepositoryImpl(
    val db: Database,
    val mapper: ObjectMapper
) : AntecedentRepository {
    override fun idExists(id: Int) =
        db.find(DBAntecedent::class.java, id) != null

    override fun save(model: AntecedentTemplate): Antecedent {
        val lookup = AntecedentLookup(
            systemId = model.systemId,
            ruleId = null,
            antecedentId = model.id
        )

        val dbModel = findOne(lookup)
        if (idExists(model.id) && (dbModel == null))
            throw SystemNotExistException(model.systemId)

        try {
            if (dbModel == null)
                db.save(DBInsertableAntecedent.fromModel(model))
            else
                db.update(DBInsertableAntecedent.fromModel(model))
        } catch (e: Exception) {
            throw AntecedentSaveException(model.id)
        }

        return findOne(lookup)
            ?: throw AntecedentSaveException(model.id)
    }

    override fun delete(lookup: AntecedentLookup) {
        try {
            lookup.antecedentId?.let {
                db.delete(DBAntecedent::class.java, it)
            }
        } catch (_: Exception) {

        }
    }

    override fun findOne(lookup: AntecedentLookup): Antecedent? {
        val sql = "select a.* " +
                "from antecedent a join membership_function mf on a.m_id = mf.m_id " +
                "join variable v on v.v_id = mf.v_id " +
                "where v.s_id = ? and a.a_id = ?;"
        return lookup.antecedentId?.let {
            db.findNative(DBAntecedent::class.java, sql)
                .setParameter(1, lookup.systemId)
                .setParameter(2, it)
                .findOne()
                ?.toModel()
        }
    }

    override fun findAll(lookup: AntecedentLookup, pageSettings: PageSettings): List<Antecedent> {
        val query =
            if (lookup.ruleId == null) {
                val sql = "select a.* " +
                        "from antecedent a join membership_function mf on a.m_id = mf.m_id " +
                        "join variable v on v.v_id = mf.v_id " +
                        "where v.s_id = ?;"
                db.findNative(DBAntecedent::class.java, sql)
                    .setParameter(1, lookup.systemId)
            } else {
                val sql = "select a.* " +
                        "from antecedent a " +
                        "join rule_antecedents ra on ra.a_id = a.a_id " +
                        "join rule r on r.r_id = ra.r_id " +
                        "where r.s_id = ? and r.r_id = ?;"
                db.findNative(DBAntecedent::class.java, sql)
                    .setParameter(1, lookup.systemId)
                    .setParameter(2, lookup.ruleId)
            }
        return query
            .setMaxRows(pageSettings.size)
            .setFirstRow(pageSettings.page * pageSettings.size)
            .findList()
            .map { it.toModel() }
    }
}