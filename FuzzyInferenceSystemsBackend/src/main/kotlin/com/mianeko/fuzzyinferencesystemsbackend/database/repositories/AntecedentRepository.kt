package com.mianeko.fuzzyinferencesystemsbackend.database.repositories

import com.mianeko.fuzzyinferencesystemsbackend.DTODb.AntecedentDTODb
import com.mianeko.fuzzyinferencesystemsbackend.DTODb.AntecedentTemplateDTODb
import com.mianeko.fuzzyinferencesystemsbackend.database.entities.DBAntecedent
import com.mianeko.fuzzyinferencesystemsbackend.exceptions.AntecedentSaveException
import com.mianeko.fuzzyinferencesystemsbackend.exceptions.SystemNotExistException
import com.mianeko.fuzzyinferencesystemsbackend.lookupEntities.AntecedentLookup
import com.mianeko.fuzzyinferencesystemsbackend.lookupEntities.PageSettings
import io.ebean.Database
import org.springframework.stereotype.Repository

interface AntecedentRepository : DatabaseRepository<AntecedentDTODb, AntecedentTemplateDTODb, AntecedentLookup>

@Repository
class AntecedentRepositoryImpl(
    val db: Database
) : AntecedentRepository {
    override fun idExists(id: Int) =
        db.find(DBAntecedent::class.java, id) != null

    override fun save(model: AntecedentTemplateDTODb): AntecedentDTODb {
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
                db.save(model.toModelDb())
            else
                db.update(model.toModelDb())
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

    override fun findOne(lookup: AntecedentLookup): AntecedentDTODb? {
        val sql = "select a.* " +
                "from antecedent a join membership_function mf on a.m_id = mf.m_id " +
                "join variable v on v.v_id = mf.v_id " +
                "where v.s_id = ? and a.a_id = ?;"
        return lookup.antecedentId?.let { id ->
            db.findNative(DBAntecedent::class.java, sql)
                .setParameter(1, lookup.systemId)
                .setParameter(2, id)
                .findOne()
                ?.let { AntecedentDTODb.fromModelDb(it) }
        }
    }

    override fun findAll(lookup: AntecedentLookup, pageSettings: PageSettings): List<AntecedentDTODb> {
        val query =
            if (lookup.ruleId == null) {
                val sql = "select a.* " +
                        "from antecedent a join membership_function mf on a.m_id = mf.m_id " +
                        "join variable v on v.v_id = mf.v_id " +
                        "where v.s_id = ? "
                db.findNative(DBAntecedent::class.java, sql)
                    .setParameter(1, lookup.systemId)
            } else {
                val sql = "select a.* " +
                        "from antecedent a " +
                        "join rule_antecedents ra on ra.a_id = a.a_id " +
                        "join rule r on r.r_id = ra.r_id " +
                        "where r.s_id = ? and r.r_id = ? "
                db.findNative(DBAntecedent::class.java, sql)
                    .setParameter(1, lookup.systemId)
                    .setParameter(2, lookup.ruleId)
            }
        return query
            .setMaxRows(pageSettings.size)
            .setFirstRow(pageSettings.page * pageSettings.size)
            .findList()
            .map { AntecedentDTODb.fromModelDb(it) }
    }
}