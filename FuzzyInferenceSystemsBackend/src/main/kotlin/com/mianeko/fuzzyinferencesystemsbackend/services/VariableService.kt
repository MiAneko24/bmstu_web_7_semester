package com.mianeko.fuzzyinferencesystemsbackend.services

import com.mianeko.fuzzyinferencesystemsbackend.DTODb.VariableTemplateDTODb
import com.mianeko.fuzzyinferencesystemsbackend.DTONet.VariableDTONet
import com.mianeko.fuzzyinferencesystemsbackend.DTONet.VariableTemplateDTONet
import com.mianeko.fuzzyinferencesystemsbackend.database.repositories.SystemRepository
import com.mianeko.fuzzyinferencesystemsbackend.database.repositories.VariableRepository
import com.mianeko.fuzzyinferencesystemsbackend.exceptions.SystemNotExistException
import com.mianeko.fuzzyinferencesystemsbackend.exceptions.VariableNotExistException
import com.mianeko.fuzzyinferencesystemsbackend.lookupEntities.PageSettings
import com.mianeko.fuzzyinferencesystemsbackend.lookupEntities.VariableLookup
import org.springframework.stereotype.Service

interface VariableService : CrudService<VariableDTONet, VariableTemplateDTONet, VariableLookup>

@Service
class VariableServiceImpl(
    private val variableRepository: VariableRepository,
    private val systemRepository: SystemRepository
) : VariableService {
    private fun checkPath(systemId: Int) {
        if (!systemRepository.idExists(systemId))
            throw SystemNotExistException(systemId)
    }

    override fun create(model: VariableTemplateDTONet): VariableDTONet {
        checkPath(model.systemId)

        return VariableDTONet.fromModel(
            variableRepository.save(
                VariableTemplateDTODb.fromModel(
                    model.toModel(generateId())
                )
            ).toModel()
        )
    }

    override fun get(lookupEntity: VariableLookup): VariableDTONet {
        checkPath(lookupEntity.systemId)

        return variableRepository
                .findOne(lookupEntity)
                ?.let { VariableDTONet.fromModel(it.toModel()) }
            ?: throw VariableNotExistException(lookupEntity.variableId)
    }

    override fun getAll(lookupEntity: VariableLookup, pageSettings: PageSettings): List<VariableDTONet> {
        checkPath(lookupEntity.systemId)

        return variableRepository
            .findAll(lookupEntity, pageSettings)
            .map { VariableDTONet.fromModel(it.toModel()) }
    }

    override fun update(model: VariableTemplateDTONet): VariableDTONet {
        checkPath(model.systemId)

        if (model.id == null || !variableRepository.idExists(model.id))
            throw VariableNotExistException(model.id)

        return VariableDTONet.fromModel(
            variableRepository.save(
                VariableTemplateDTODb.fromModel(
                    model.toModel(model.id)
                )
            ).toModel()
        )
    }

    override fun delete(lookupEntity: VariableLookup) {
        variableRepository.delete(lookupEntity)
    }
}