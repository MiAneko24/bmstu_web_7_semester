package com.mianeko.fuzzyinferencesystemsbackend.services

import com.mianeko.fuzzyinferencesystemsbackend.DTODb.SystemTemplateDTODb
import com.mianeko.fuzzyinferencesystemsbackend.DTONet.SystemDTONet
import com.mianeko.fuzzyinferencesystemsbackend.DTONet.SystemTemplateDTONet
import com.mianeko.fuzzyinferencesystemsbackend.database.repositories.SystemRepository
import com.mianeko.fuzzyinferencesystemsbackend.exceptions.SystemNotExistException
import com.mianeko.fuzzyinferencesystemsbackend.lookupEntities.PageSettings
import com.mianeko.fuzzyinferencesystemsbackend.lookupEntities.SystemLookup
import org.springframework.stereotype.Service

interface SystemService : CrudService<SystemDTONet, SystemTemplateDTONet, SystemLookup>

@Service
class SystemServiceImpl(
    private val systemRepository: SystemRepository
) : SystemService {
    override fun create(model: SystemTemplateDTONet): SystemDTONet {
        return SystemDTONet.fromModel(
            systemRepository.save(
                SystemTemplateDTODb.fromModel(
                    model.toModel(generateId())
                )
            ).toModel()
        )
    }

    override fun get(lookupEntity: SystemLookup): SystemDTONet {
        return systemRepository
                .findOne(lookupEntity)
                ?.let { SystemDTONet.fromModel(it.toModel()) }
            ?: throw SystemNotExistException(lookupEntity.systemId)
    }

    override fun getAll(lookupEntity: SystemLookup, pageSettings: PageSettings): List<SystemDTONet> {
        return systemRepository
            .findAll(lookupEntity, pageSettings)
            .map { SystemDTONet.fromModel(it.toModel()) }
    }

    override fun update(model: SystemTemplateDTONet): SystemDTONet {
        if (model.id == null || !systemRepository.idExists(model.id))
            throw SystemNotExistException(model.id)

        return SystemDTONet.fromModel(
            systemRepository.save(
                SystemTemplateDTODb.fromModel(
                    model.toModel(model.id)
                )
            ).toModel()
        )
    }

    override fun delete(lookupEntity: SystemLookup) {
        systemRepository.delete(lookupEntity)
    }
}