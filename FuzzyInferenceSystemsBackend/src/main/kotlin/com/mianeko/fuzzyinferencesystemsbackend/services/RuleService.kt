package com.mianeko.fuzzyinferencesystemsbackend.services

import com.mianeko.fuzzyinferencesystemsbackend.DTODb.RuleTemplateDTODb
import com.mianeko.fuzzyinferencesystemsbackend.DTONet.RuleDTONet
import com.mianeko.fuzzyinferencesystemsbackend.DTONet.RuleTemplateDTONet
import com.mianeko.fuzzyinferencesystemsbackend.database.repositories.AntecedentRepository
import com.mianeko.fuzzyinferencesystemsbackend.database.repositories.RuleRepository
import com.mianeko.fuzzyinferencesystemsbackend.database.repositories.SystemRepository
import com.mianeko.fuzzyinferencesystemsbackend.exceptions.AntecedentNotExistException
import com.mianeko.fuzzyinferencesystemsbackend.exceptions.RuleNotExistException
import com.mianeko.fuzzyinferencesystemsbackend.exceptions.SystemNotExistException
import com.mianeko.fuzzyinferencesystemsbackend.lookupEntities.AntecedentLookup
import com.mianeko.fuzzyinferencesystemsbackend.lookupEntities.PageSettings
import com.mianeko.fuzzyinferencesystemsbackend.lookupEntities.RuleLookup
import org.springframework.stereotype.Service

interface RuleService : CrudService<RuleDTONet, RuleTemplateDTONet, RuleLookup>

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

    override fun get(lookupEntity: RuleLookup): RuleDTONet {
        checkPath(lookupEntity.systemId)

        return ruleRepository
            .findOne(lookupEntity)
            ?.let {
                RuleDTONet.fromModel(it.toModel())
            }
            ?: throw RuleNotExistException(lookupEntity.ruleId)
    }

    override fun getAll(lookupEntity: RuleLookup, pageSettings: PageSettings): List<RuleDTONet> {
        checkPath(lookupEntity.systemId)

        return ruleRepository
            .findAll(lookupEntity, pageSettings)
            .map { RuleDTONet.fromModel(it.toModel()) }
    }

    override fun create(model: RuleTemplateDTONet): RuleDTONet {
        checkPath(model.systemId)

        return RuleDTONet.fromModel(
            ruleRepository.save(
                RuleTemplateDTODb.fromModel(
                    model.toModel(generateId(), emptyList())
                )
            ).toModel()
        )
    }

    override fun update(model: RuleTemplateDTONet): RuleDTONet {
        checkPath(model.systemId)

        if (model.id == null || !ruleRepository.idExists(model.id))
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

            antecedentRepository.findOne(lookup)?.toModel()
            ?: throw AntecedentNotExistException(it)
        } ?: rule.antecedents.map { it.toModel() }

        return RuleDTONet.fromModel(ruleRepository.save(
            RuleTemplateDTODb.fromModel(
                model.toModel(
                    model.id,
                    antecedents.map { it.toTemplate(model.systemId) }
                )
            )
        ).toModel()
        )
    }

    override fun delete(lookupEntity: RuleLookup) {
        ruleRepository.delete(lookupEntity)
    }
}