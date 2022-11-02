package com.mianeko.fuzzyinferencesystemsbackend.services

import com.mianeko.fuzzyinferencesystemsbackend.DTO.VariableDTO
import com.mianeko.fuzzyinferencesystemsbackend.database.repositories.SystemRepository
import com.mianeko.fuzzyinferencesystemsbackend.database.repositories.VariableRepository
import com.mianeko.fuzzyinferencesystemsbackend.exceptions.SystemNotExistException
import com.mianeko.fuzzyinferencesystemsbackend.exceptions.VariableNotExistException
import com.mianeko.fuzzyinferencesystemsbackend.lookupEntities.PageSettings
import com.mianeko.fuzzyinferencesystemsbackend.lookupEntities.VariableLookup
import com.mianeko.fuzzyinferencesystemsbackend.services.models.VariableTemplateDTO
import org.springdoc.core.converters.models.Pageable
import org.springframework.stereotype.Service

interface VariableService : CrudService<VariableDTO, VariableTemplateDTO, VariableLookup>

@Service
class VariableServiceImpl(
    private val variableRepository: VariableRepository,
    private val systemRepository: SystemRepository
) : VariableService {
    private fun checkPath(systemId: Int) {
        if (!systemRepository.idExists(systemId))
            throw SystemNotExistException(systemId)
    }

    override fun create(model: VariableTemplateDTO): VariableDTO {
        checkPath(model.systemId)

        return variableRepository.save(
            model.toModel(generateId()),
        ).toDTO()
    }

    override fun get(lookupEntity: VariableLookup): VariableDTO {
        checkPath(lookupEntity.systemId)

        return variableRepository
                .findOne(lookupEntity)
                ?.toDTO()
            ?: throw VariableNotExistException(lookupEntity.variableId)
    }

    override fun getAll(lookupEntity: VariableLookup, pageSettings: PageSettings): List<VariableDTO> {
        checkPath(lookupEntity.systemId)

        return variableRepository
            .findAll(lookupEntity, pageSettings)
            .map { it.toDTO() }
    }

    override fun update(model: VariableTemplateDTO): VariableDTO {
        checkPath(model.systemId)

        if (model.id == null || !variableRepository.idExists(model.id))
            throw VariableNotExistException(model.id)

        return variableRepository
            .save(model.toModel(model.id))
            .toDTO()
    }

    override fun delete(lookupEntity: VariableLookup) {
        variableRepository.delete(lookupEntity)
    }
}