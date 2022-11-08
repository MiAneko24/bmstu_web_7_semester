package com.mianeko.fuzzyinferencesystemsbackend.services

import com.mianeko.fuzzyinferencesystemsbackend.DTODb.AntecedentTemplateDTODb
import com.mianeko.fuzzyinferencesystemsbackend.DTONet.AntecedentDTONet
import com.mianeko.fuzzyinferencesystemsbackend.DTONet.AntecedentTemplateDTONet
import com.mianeko.fuzzyinferencesystemsbackend.database.repositories.AntecedentRepository
import com.mianeko.fuzzyinferencesystemsbackend.database.repositories.RuleRepository
import com.mianeko.fuzzyinferencesystemsbackend.database.repositories.SystemRepository
import com.mianeko.fuzzyinferencesystemsbackend.exceptions.AntecedentNotExistException
import com.mianeko.fuzzyinferencesystemsbackend.exceptions.RuleNotExistException
import com.mianeko.fuzzyinferencesystemsbackend.exceptions.SystemNotExistException
import com.mianeko.fuzzyinferencesystemsbackend.lookupEntities.AntecedentLookup
import com.mianeko.fuzzyinferencesystemsbackend.lookupEntities.PageSettings
import org.springframework.stereotype.Service

interface AntecedentService : CrudService<AntecedentDTONet, AntecedentTemplateDTONet, AntecedentLookup>

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

    override fun create(model: AntecedentTemplateDTONet): AntecedentDTONet {
        checkPath(model.systemId, null)

        return AntecedentDTONet.fromModel(
            antecedentRepository.save(
                AntecedentTemplateDTODb.fromModel(
                    model.toModel(
                        generateId()
                    )
                )
            ).toModel()
        )
    }

    override fun get(lookupEntity: AntecedentLookup): AntecedentDTONet {
        checkPath(lookupEntity.systemId, lookupEntity.ruleId)

        return antecedentRepository
                .findOne(lookupEntity)
                ?.let{
                    AntecedentDTONet.fromModel(it.toModel())
                }
            ?: throw AntecedentNotExistException(lookupEntity.antecedentId)
    }

    override fun getAll(lookupEntity: AntecedentLookup, pageSettings: PageSettings): List<AntecedentDTONet> {
        checkPath(lookupEntity.systemId, lookupEntity.ruleId)

        return antecedentRepository.findAll(lookupEntity, pageSettings).map{ AntecedentDTONet.fromModel(it.toModel()) }
    }

    override fun update(model: AntecedentTemplateDTONet): AntecedentDTONet {
        checkPath(model.systemId, null)

        if (model.id == null || !antecedentRepository.idExists(model.id))
            throw AntecedentNotExistException(model.id)

        return AntecedentDTONet.fromModel(
            antecedentRepository.save(
                AntecedentTemplateDTODb.fromModel(
                    model.toModel(model.id)
                )
            ).toModel()
        )
    }

    override fun delete(lookupEntity: AntecedentLookup) {
        antecedentRepository.delete(lookupEntity)
    }
}