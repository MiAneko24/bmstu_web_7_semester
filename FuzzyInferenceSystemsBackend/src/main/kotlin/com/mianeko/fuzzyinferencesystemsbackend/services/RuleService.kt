package com.mianeko.fuzzyinferencesystemsbackend.services

import com.mianeko.fuzzyinferencesystemsbackend.DTO.RuleDTO
import com.mianeko.fuzzyinferencesystemsbackend.database.repositories.AntecedentRepository
import com.mianeko.fuzzyinferencesystemsbackend.database.repositories.RuleRepository
import com.mianeko.fuzzyinferencesystemsbackend.database.repositories.SystemRepository
import com.mianeko.fuzzyinferencesystemsbackend.exceptions.AntecedentNotExistException
import com.mianeko.fuzzyinferencesystemsbackend.exceptions.RuleNotExistException
import com.mianeko.fuzzyinferencesystemsbackend.exceptions.SystemNotExistException
import com.mianeko.fuzzyinferencesystemsbackend.lookupEntities.AntecedentLookup
import com.mianeko.fuzzyinferencesystemsbackend.lookupEntities.PageSettings
import com.mianeko.fuzzyinferencesystemsbackend.lookupEntities.RuleLookup
import com.mianeko.fuzzyinferencesystemsbackend.services.models.RuleTemplateDTO
import org.springdoc.core.converters.models.Pageable
import org.springframework.stereotype.Service

interface RuleService : CrudService<RuleDTO, RuleTemplateDTO, RuleLookup>

@Service
class RuleServiceImpl(
    private val ruleRepository: RuleRepository,
    private val antecedentRepository: AntecedentRepository,
    private val systemRepository: SystemRepository
) : RuleService {
    private fun checkPath(systemId: Int) {
        if (!systemRepository.idExists(systemId))
            throw SystemNotExistException(systemId)
    }

    override fun get(lookupEntity: RuleLookup): RuleDTO {
        checkPath(lookupEntity.systemId)

        return ruleRepository
            .findOne(lookupEntity)
            ?.toDTO()
            ?: throw RuleNotExistException(lookupEntity.ruleId)
    }

    override fun getAll(lookupEntity: RuleLookup, pageSettings: PageSettings): List<RuleDTO> {
        checkPath(lookupEntity.systemId)

        return ruleRepository
            .findAll(lookupEntity, pageSettings)
            .map { it.toDTO() }
    }

    override fun create(model: RuleTemplateDTO): RuleDTO {
        checkPath(model.systemId)

        return ruleRepository.save(
            model.toModel(
                generateId(),
                emptyList()
            ),
        ).toDTO()
    }

    override fun update(model: RuleTemplateDTO): RuleDTO {
        checkPath(model.systemId)

        if (model.id == null)
            throw RuleNotExistException(model.id)

        val rule = ruleRepository.findOne(
            RuleLookup(
                systemId = model.systemId,
                ruleId = model.id
            )
        ) ?: throw RuleNotExistException(model.id)

        val antecedents = model.antecedents?.map {
            val lookup = AntecedentLookup(
                systemId = model.systemId,
                ruleId = null,
                antecedentId = it
            )

            antecedentRepository.findOne(lookup)
            ?: throw AntecedentNotExistException(it)
        } ?: rule.antecedents

        return ruleRepository.save(
            model.toModel(
                model.id,
                antecedents.map { it.toTemplate(model.systemId) }
            ),
        ).toDTO()
    }

    override fun delete(lookupEntity: RuleLookup) {
        ruleRepository.delete(lookupEntity)
    }
}