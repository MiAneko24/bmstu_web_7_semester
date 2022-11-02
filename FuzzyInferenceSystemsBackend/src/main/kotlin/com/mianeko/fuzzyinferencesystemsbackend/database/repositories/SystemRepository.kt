package com.mianeko.fuzzyinferencesystemsbackend.database.repositories

import com.fasterxml.jackson.databind.ObjectMapper
import com.mianeko.fuzzyinferencesystemsbackend.database.entities.DBSystem
import com.mianeko.fuzzyinferencesystemsbackend.database.entities.DBInsertableSystem
import com.mianeko.fuzzyinferencesystemsbackend.exceptions.SystemSaveException
import com.mianeko.fuzzyinferencesystemsbackend.lookupEntities.PageSettings
import com.mianeko.fuzzyinferencesystemsbackend.lookupEntities.SystemLookup
import com.mianeko.fuzzyinferencesystemsbackend.services.models.InferenceSystem
import com.mianeko.fuzzyinferencesystemsbackend.services.models.SystemTemplate
import io.ebean.Database
import org.springframework.stereotype.Repository

interface SystemRepository : DatabaseRepository<InferenceSystem, SystemTemplate, SystemLookup>

@Repository
class SystemRepositoryImpl(
    val db: Database,
) : SystemRepository {
    override fun idExists(id: Int) =
        db.find(DBSystem::class.java, id) != null

    override fun save(model: SystemTemplate): InferenceSystem {
        val lookup = SystemLookup(model.id)

        val dbModel = findOne(lookup)

        try {
            if (dbModel == null)
                db.save(DBInsertableSystem.fromModel(model))
            else
                db.update(DBInsertableSystem.fromModel(model))
        } catch (e: Exception) {
            throw SystemSaveException(model.id)
        }

        return findOne(lookup)
            ?: throw SystemSaveException(model.id)
    }

    override fun delete(lookup: SystemLookup) {
        lookup.systemId?.let {
            db.delete(DBSystem::class.java, it)
        }
    }

    override fun findOne(lookup: SystemLookup): InferenceSystem? =
        lookup.systemId?.let {
            db.find(DBSystem::class.java, it)
                ?.toModel()
        }

    override fun findAll(lookup: SystemLookup, pageSettings: PageSettings): List<InferenceSystem> =
        db.find(DBSystem::class.java)
            .setMaxRows(pageSettings.size)
            .setFirstRow(pageSettings.page * pageSettings.size)
            .findList()
            .map{ it.toModel() }
}