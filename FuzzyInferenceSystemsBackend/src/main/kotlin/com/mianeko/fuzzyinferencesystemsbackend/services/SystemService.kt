package com.mianeko.fuzzyinferencesystemsbackend.services

import com.mianeko.fuzzyinferencesystemsbackend.DTO.SystemDTO
import com.mianeko.fuzzyinferencesystemsbackend.database.repositories.SystemRepository
import com.mianeko.fuzzyinferencesystemsbackend.exceptions.SystemNotExistException
import com.mianeko.fuzzyinferencesystemsbackend.lookupEntities.PageSettings
import com.mianeko.fuzzyinferencesystemsbackend.lookupEntities.SystemLookup
import com.mianeko.fuzzyinferencesystemsbackend.services.models.SystemTemplateDTO
import org.springdoc.core.converters.models.Pageable
import org.springframework.stereotype.Service

interface SystemService : CrudService<SystemDTO, SystemTemplateDTO, SystemLookup>

@Service
class SystemServiceImpl(
    private val systemRepository: SystemRepository
) : SystemService {
    override fun create(model: SystemTemplateDTO): SystemDTO {
        return systemRepository
            .save(model.toModel(generateId()))
            .toDTO()
    }

    override fun get(lookupEntity: SystemLookup): SystemDTO {
        return systemRepository
                .findOne(lookupEntity)
                ?.toDTO()
            ?: throw SystemNotExistException(lookupEntity.systemId)
    }

    override fun getAll(lookupEntity: SystemLookup, pageSettings: PageSettings): List<SystemDTO> {
        return systemRepository
            .findAll(lookupEntity, pageSettings)
            .map { it.toDTO() }
    }

    override fun update(model: SystemTemplateDTO): SystemDTO {
        if (model.id == null || !systemRepository.idExists(model.id))
            throw SystemNotExistException(model.id)

        return systemRepository
            .save(model.toModel(model.id))
            .toDTO()
    }

    override fun delete(lookupEntity: SystemLookup) {
        systemRepository.delete(lookupEntity)
    }
}