package com.mianeko.fuzzyinferencesystemsbackend.database.repositories

import com.fasterxml.jackson.databind.ObjectMapper
import com.mianeko.fuzzyinferencesystemsbackend.database.entities.DBRule
import com.mianeko.fuzzyinferencesystemsbackend.database.entities.DBInsertableRule
import com.mianeko.fuzzyinferencesystemsbackend.exceptions.RuleSaveException
import com.mianeko.fuzzyinferencesystemsbackend.lookupEntities.RuleLookup
import com.mianeko.fuzzyinferencesystemsbackend.exceptions.SystemNotExistException
import com.mianeko.fuzzyinferencesystemsbackend.lookupEntities.PageSettings
import com.mianeko.fuzzyinferencesystemsbackend.services.models.Rule
import com.mianeko.fuzzyinferencesystemsbackend.services.models.RuleTemplate
import io.ebean.Database
import org.springframework.stereotype.Repository

interface RuleRepository : DatabaseRepository<Rule, RuleTemplate, RuleLookup>

@Repository
class RuleRepositoryImpl(
    val db: Database,
) : RuleRepository {
    override fun idExists(id: Int) =
        db.find(DBRule::class.java, id) != null

    override fun save(model: RuleTemplate): Rule {
        val lookup = RuleLookup(
            systemId = model.systemId,
            ruleId = model.id
        )

        val dbModel = findOne(lookup)
        if (idExists(model.id) && dbModel == null)
            throw SystemNotExistException(model.systemId)

        try {
            if (dbModel == null)
                db.save(DBInsertableRule.fromModel(model))
            else
                db.update(DBInsertableRule.fromModel(model))
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

    override fun findOne(lookup: RuleLookup): Rule? =
        lookup.ruleId?.let {
            db.find(DBRule::class.java)
                .where()
                .eq("s_id", lookup.systemId)
                .eq("r_id", it)
                .findOne()
                ?.toModel()
        }

    override fun findAll(lookup: RuleLookup, pageSettings: PageSettings): List<Rule> =
        db.find(DBRule::class.java)
            .where()
            .eq("s_id", lookup.systemId)
            .setMaxRows(pageSettings.size)
            .setFirstRow(pageSettings.page * pageSettings.size)
            .findList()
            .map{ it.toModel() }
}