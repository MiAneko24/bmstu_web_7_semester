package com.mianeko.fuzzyinferencesystemsbackend.database.repositories

import com.mianeko.fuzzyinferencesystemsbackend.DTODb.SystemDTODb
import com.mianeko.fuzzyinferencesystemsbackend.DTODb.SystemTemplateDTODb
import com.mianeko.fuzzyinferencesystemsbackend.database.entities.DBSystem
import com.mianeko.fuzzyinferencesystemsbackend.exceptions.SystemSaveException
import com.mianeko.fuzzyinferencesystemsbackend.lookupEntities.PageSettings
import com.mianeko.fuzzyinferencesystemsbackend.lookupEntities.SystemLookup
import io.ebean.Database
import org.springframework.stereotype.Repository

interface SystemRepository : DatabaseRepository<SystemDTODb, SystemTemplateDTODb, SystemLookup>

@Repository
class SystemRepositoryImpl(
    val db: Database,
) : SystemRepository {
    override fun idExists(id: Int) =
        db.find(DBSystem::class.java, id) != null

    override fun save(model: SystemTemplateDTODb): SystemDTODb {
        val lookup = SystemLookup(model.id)

        val dbModel = findOne(lookup)

        try {
            if (dbModel == null)
                db.save(model.toModelDb())
            else
                db.update(model.toModelDb())
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

    override fun findOne(lookup: SystemLookup): SystemDTODb? =
        lookup.systemId?.let { id ->
            db.find(DBSystem::class.java, id)
                ?.let { SystemDTODb.fromModelDb(it) }
        }

    override fun findAll(
        lookup: SystemLookup,
        pageSettings: PageSettings
    ): List<SystemDTODb> =
        db.find(DBSystem::class.java)
            .setMaxRows(pageSettings.size)
            .setFirstRow(pageSettings.page * pageSettings.size)
            .findList()
            .map{ SystemDTODb.fromModelDb(it) }
}