package com.mianeko.fuzzyinferencesystemsbackend.services

import com.mianeko.fuzzyinferencesystemsbackend.DTODb.ConsequentTemplateDTODb
import com.mianeko.fuzzyinferencesystemsbackend.DTONet.ConsequentDTONet
import com.mianeko.fuzzyinferencesystemsbackend.DTONet.ConsequentTemplateDTONet
import com.mianeko.fuzzyinferencesystemsbackend.database.repositories.ConsequentRepository
import com.mianeko.fuzzyinferencesystemsbackend.database.repositories.RuleRepository
import com.mianeko.fuzzyinferencesystemsbackend.database.repositories.SystemRepository
import com.mianeko.fuzzyinferencesystemsbackend.exceptions.ConsequentNotExistException
import com.mianeko.fuzzyinferencesystemsbackend.exceptions.RuleNotExistException
import com.mianeko.fuzzyinferencesystemsbackend.exceptions.SystemNotExistException
import com.mianeko.fuzzyinferencesystemsbackend.lookupEntities.ConsequentLookup
import com.mianeko.fuzzyinferencesystemsbackend.lookupEntities.PageSettings
import org.springframework.stereotype.Service

interface ConsequentService : CrudService<ConsequentDTONet, ConsequentTemplateDTONet, ConsequentLookup>

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

    override fun create(model: ConsequentTemplateDTONet): ConsequentDTONet {
        checkPath(model.systemId, model.ruleId)

        return ConsequentDTONet.fromModel(
            consequentRepository.save(
                ConsequentTemplateDTODb.fromModel(
                    model.toModel(
                        generateId()
                    )
                )
            ).toModel()
        )
    }

    override fun get(lookupEntity: ConsequentLookup): ConsequentDTONet {
        checkPath(lookupEntity.systemId, lookupEntity.ruleId)

        return consequentRepository
                .findOne(lookupEntity)
                ?.let{ ConsequentDTONet.fromModel(it.toModel()) }
            ?: throw ConsequentNotExistException(lookupEntity.consequentId)
    }

    override fun getAll(lookupEntity: ConsequentLookup, pageSettings: PageSettings): List<ConsequentDTONet> {
        checkPath(lookupEntity.systemId, lookupEntity.ruleId)

        return consequentRepository.findAll(lookupEntity, pageSettings).map { ConsequentDTONet.fromModel(it.toModel()) }
    }

    override fun update(model: ConsequentTemplateDTONet): ConsequentDTONet {
        checkPath(model.systemId, model.ruleId)

        if (model.id == null || !consequentRepository.idExists(model.id))
            throw ConsequentNotExistException(model.id)

        return ConsequentDTONet.fromModel(
            consequentRepository.save(
                ConsequentTemplateDTODb.fromModel(
                    model.toModel(model.id)
                )
            ).toModel()
        )
    }

    override fun delete(lookupEntity: ConsequentLookup) {
        consequentRepository.delete(lookupEntity)
    }

}