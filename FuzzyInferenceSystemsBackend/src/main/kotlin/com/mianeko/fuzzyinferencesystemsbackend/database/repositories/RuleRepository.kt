package com.mianeko.fuzzyinferencesystemsbackend.database.repositories

import com.mianeko.fuzzyinferencesystemsbackend.DTODb.RuleDTODb
import com.mianeko.fuzzyinferencesystemsbackend.DTODb.RuleTemplateDTODb
import com.mianeko.fuzzyinferencesystemsbackend.database.entities.DBRule
import com.mianeko.fuzzyinferencesystemsbackend.exceptions.RuleSaveException
import com.mianeko.fuzzyinferencesystemsbackend.exceptions.SystemNotExistException
import com.mianeko.fuzzyinferencesystemsbackend.lookupEntities.PageSettings
import com.mianeko.fuzzyinferencesystemsbackend.lookupEntities.RuleLookup
import io.ebean.Database
import org.springframework.stereotype.Repository

interface RuleRepository : DatabaseRepository<RuleDTODb, RuleTemplateDTODb, RuleLookup>

@Repository
class RuleRepositoryImpl(
    val db: Database,
) : RuleRepository {
    override fun idExists(id: Int) =
        db.find(DBRule::class.java, id) != null

    override fun save(model: RuleTemplateDTODb): RuleDTODb {
        val lookup = RuleLookup(
            systemId = model.systemId,
            ruleId = model.id
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
            throw RuleSaveException(model.id)
        }

        return findOne(lookup)
            ?: throw RuleSaveException(model.id)
    }

    override fun delete(lookup: RuleLookup) {
        lookup.ruleId?.let {
            db.delete(DBRule::class.java, it)
        }
    }

    override fun findOne(lookup: RuleLookup): RuleDTODb? =
        lookup.ruleId?.let { id ->
            db.find(DBRule::class.java)
                .where()
                .eq("s_id", lookup.systemId)
                .eq("r_id", id)
                .findOne()
                ?.let { RuleDTODb.fromModelDb(it) }
        }

    override fun findAll(lookup: RuleLookup, pageSettings: PageSettings): List<RuleDTODb> =
        db.find(DBRule::class.java)
            .where()
            .eq("s_id", lookup.systemId)
            .setMaxRows(pageSettings.size)
            .setFirstRow(pageSettings.page * pageSettings.size)
            .findList()
            .map{ RuleDTODb.fromModelDb(it) }
}