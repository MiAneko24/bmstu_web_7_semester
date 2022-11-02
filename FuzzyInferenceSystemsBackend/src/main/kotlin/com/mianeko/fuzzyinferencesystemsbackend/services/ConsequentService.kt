package com.mianeko.fuzzyinferencesystemsbackend.services

import com.mianeko.fuzzyinferencesystemsbackend.DTO.ConsequentDTO
import com.mianeko.fuzzyinferencesystemsbackend.database.repositories.ConsequentRepository
import com.mianeko.fuzzyinferencesystemsbackend.database.repositories.RuleRepository
import com.mianeko.fuzzyinferencesystemsbackend.database.repositories.SystemRepository
import com.mianeko.fuzzyinferencesystemsbackend.exceptions.ConsequentNotExistException
import com.mianeko.fuzzyinferencesystemsbackend.exceptions.RuleNotExistException
import com.mianeko.fuzzyinferencesystemsbackend.exceptions.SystemNotExistException
import com.mianeko.fuzzyinferencesystemsbackend.lookupEntities.ConsequentLookup
import com.mianeko.fuzzyinferencesystemsbackend.lookupEntities.PageSettings
import com.mianeko.fuzzyinferencesystemsbackend.services.models.ConsequentTemplateDTO
import org.springdoc.core.converters.models.Pageable
import org.springframework.stereotype.Service

interface ConsequentService : CrudService<ConsequentDTO, ConsequentTemplateDTO, ConsequentLookup>

@Service
class ConsequentServiceImpl(
    private val consequentRepository: ConsequentRepository,
    private val systemRepository: SystemRepository,
    private val ruleRepository: RuleRepository
) : ConsequentService {
    private fun checkPath(systemId: Int, ruleId: Int) {
        if (!systemRepository.idExists(systemId))
            throw SystemNotExistException(systemId)

        if (!ruleRepository.idExists(ruleId))
            throw RuleNotExistException(ruleId)
    }

    override fun create(model: ConsequentTemplateDTO): ConsequentDTO {
        checkPath(model.systemId, model.ruleId)

        return consequentRepository.save(
            model.toModel(generateId())
        ).toDTO()
    }

    override fun get(lookupEntity: ConsequentLookup): ConsequentDTO {
        checkPath(lookupEntity.systemId, lookupEntity.ruleId)

        return consequentRepository
                .findOne(lookupEntity)
                ?.toDTO()
            ?: throw ConsequentNotExistException(lookupEntity.consequentId)
    }

    override fun getAll(lookupEntity: ConsequentLookup, pageSettings: PageSettings): List<ConsequentDTO> {
        checkPath(lookupEntity.systemId, lookupEntity.ruleId)

        return consequentRepository.findAll(lookupEntity, pageSettings).map { it.toDTO() }
    }

    override fun update(model: ConsequentTemplateDTO): ConsequentDTO {
        checkPath(model.systemId, model.ruleId)

        if (model.id == null || !consequentRepository.idExists(model.id))
            throw ConsequentNotExistException(model.id)

        return consequentRepository.save(
            model.toModel(model.id)
        ).toDTO()
    }

    override fun delete(lookupEntity: ConsequentLookup) {
        consequentRepository.delete(lookupEntity)
    }

}