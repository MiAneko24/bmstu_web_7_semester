package com.mianeko.fuzzyinferencesystemsbackend.database.repositories

import com.fasterxml.jackson.databind.ObjectMapper
import com.mianeko.fuzzyinferencesystemsbackend.database.entities.DBVariable
import com.mianeko.fuzzyinferencesystemsbackend.database.entities.DBInsertableVariable
import com.mianeko.fuzzyinferencesystemsbackend.lookupEntities.VariableLookup
import com.mianeko.fuzzyinferencesystemsbackend.exceptions.SystemNotExistException
import com.mianeko.fuzzyinferencesystemsbackend.exceptions.VariableSaveException
import com.mianeko.fuzzyinferencesystemsbackend.lookupEntities.PageSettings
import com.mianeko.fuzzyinferencesystemsbackend.services.models.Variable
import com.mianeko.fuzzyinferencesystemsbackend.services.models.VariableTemplate
import io.ebean.Database
import org.springframework.stereotype.Repository

interface VariableRepository : DatabaseRepository<Variable, VariableTemplate, VariableLookup>

@Repository
class VariableRepositoryImpl(
    val db: Database,
) : VariableRepository {
    override fun idExists(id: Int) =
        db.find(DBVariable::class.java, id) != null

    override fun save(model: VariableTemplate): Variable {
        val lookup = VariableLookup(
            systemId = model.systemId,
            variableId = model.id
        )

        val dbModel = findOne(lookup)
        if (idExists(model.id) && dbModel == null)
            throw SystemNotExistException(model.systemId)

        try {
            if (dbModel == null)
                db.save(DBInsertableVariable.fromModel(model))
            else
                db.update(DBInsertableVariable.fromModel(model))
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

    override fun findOne(lookup: VariableLookup): Variable? =
        lookup.variableId?.let {
            db.find(DBVariable::class.java)
                .where()
                .eq("s_id", lookup.systemId)
                .eq("v_id", it)
                .findOne()
                ?.toModel()
        }

    override fun findAll(lookup: VariableLookup, pageSettings: PageSettings): List<Variable> =
        db.find(DBVariable::class.java)
            .where()
            .eq("s_id", lookup.systemId)
            .setMaxRows(pageSettings.size)
            .setFirstRow(pageSettings.page * pageSettings.size)
            .findList()
            .map{ it.toModel() }
}