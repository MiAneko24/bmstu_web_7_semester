package com.mianeko.fuzzyinferencesystemsbackend.database.repositories

import com.mianeko.fuzzyinferencesystemsbackend.DTODb.VariableDTODb
import com.mianeko.fuzzyinferencesystemsbackend.DTODb.VariableTemplateDTODb
import com.mianeko.fuzzyinferencesystemsbackend.database.entities.DBVariable
import com.mianeko.fuzzyinferencesystemsbackend.exceptions.SystemNotExistException
import com.mianeko.fuzzyinferencesystemsbackend.exceptions.VariableSaveException
import com.mianeko.fuzzyinferencesystemsbackend.lookupEntities.PageSettings
import com.mianeko.fuzzyinferencesystemsbackend.lookupEntities.VariableLookup
import io.ebean.Database
import org.springframework.stereotype.Repository

interface VariableRepository : DatabaseRepository<VariableDTODb, VariableTemplateDTODb, VariableLookup>

@Repository
class VariableRepositoryImpl(
    val db: Database,
) : VariableRepository {
    override fun idExists(id: Int) =
        db.find(DBVariable::class.java, id) != null

    override fun save(model: VariableTemplateDTODb): VariableDTODb {
        val lookup = VariableLookup(
            systemId = model.systemId,
            variableId = model.id
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
            throw VariableSaveException(model.id)
        }

        return findOne(lookup)
            ?: throw VariableSaveException(model.id)
    }

    override fun delete(lookup: VariableLookup) {
        lookup.variableId?.let {
            db.delete(DBVariable::class.java, it)
        }
    }

    override fun findOne(lookup: VariableLookup): VariableDTODb? =
        lookup.variableId?.let { id ->
            db.find(DBVariable::class.java)
                .where()
                .eq("s_id", lookup.systemId)
                .eq("v_id", id)
                .findOne()
                ?.let { VariableDTODb.fromModelDb(it) }
        }

    override fun findAll(
        lookup: VariableLookup,
        pageSettings: PageSettings
    ): List<VariableDTODb> =
        db.find(DBVariable::class.java)
            .where()
            .eq("s_id", lookup.systemId)
            .setMaxRows(pageSettings.size)
            .setFirstRow(pageSettings.page * pageSettings.size)
            .findList()
            .map{ VariableDTODb.fromModelDb(it) }
}