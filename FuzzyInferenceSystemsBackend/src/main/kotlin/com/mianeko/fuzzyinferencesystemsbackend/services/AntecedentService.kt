package com.mianeko.fuzzyinferencesystemsbackend.services

import com.mianeko.fuzzyinferencesystemsbackend.DTO.AntecedentDTO
import com.mianeko.fuzzyinferencesystemsbackend.database.repositories.AntecedentRepository
import com.mianeko.fuzzyinferencesystemsbackend.database.repositories.RuleRepository
import com.mianeko.fuzzyinferencesystemsbackend.database.repositories.SystemRepository
import com.mianeko.fuzzyinferencesystemsbackend.exceptions.AntecedentNotExistException
import com.mianeko.fuzzyinferencesystemsbackend.exceptions.RuleNotExistException
import com.mianeko.fuzzyinferencesystemsbackend.exceptions.SystemNotExistException
import com.mianeko.fuzzyinferencesystemsbackend.lookupEntities.AntecedentLookup
import com.mianeko.fuzzyinferencesystemsbackend.lookupEntities.PageSettings
import com.mianeko.fuzzyinferencesystemsbackend.services.models.AntecedentTemplateDTO
import org.springdoc.core.converters.models.Pageable
import org.springframework.stereotype.Service

interface AntecedentService : CrudService<AntecedentDTO, AntecedentTemplateDTO, AntecedentLookup>

@Service
class AntecedentServiceImpl(
    private val antecedentRepository: AntecedentRepository,
    private val systemRepository: SystemRepository,
    private val ruleRepository: RuleRepository
) : AntecedentService {
    private fun checkPath(systemId: Int, ruleId: Int?) {
        if (!systemRepository.idExists(systemId))
            throw SystemNotExistException(systemId)

        if (ruleId != null && !ruleRepository.idExists(ruleId))
            throw RuleNotExistException(ruleId)
    }

    override fun create(model: AntecedentTemplateDTO): AntecedentDTO {
        checkPath(model.systemId, null)

        return antecedentRepository.save(
            model.toModel(generateId())
        ).toDTO()
    }

    override fun get(lookupEntity: AntecedentLookup): AntecedentDTO {
        checkPath(lookupEntity.systemId, lookupEntity.ruleId)

        return antecedentRepository
                .findOne(lookupEntity)
                ?.toDTO()
            ?: throw AntecedentNotExistException(lookupEntity.antecedentId)
    }

    override fun getAll(lookupEntity: AntecedentLookup, pageSettings: PageSettings): List<AntecedentDTO> {
        checkPath(lookupEntity.systemId, lookupEntity.ruleId)

        return antecedentRepository.findAll(lookupEntity, pageSettings).map{ it.toDTO() }
    }

    override fun update(model: AntecedentTemplateDTO): AntecedentDTO {
        checkPath(model.systemId, null)

        if (model.id == null || !antecedentRepository.idExists(model.id))
            throw AntecedentNotExistException(model.id)

        return antecedentRepository.save(
            model.toModel(model.id)
        ).toDTO()
    }

    override fun delete(lookupEntity: AntecedentLookup) {
        antecedentRepository.delete(lookupEntity)
    }
}