package com.mianeko.fuzzyinferencesystemsbackend.services

import com.mianeko.fuzzyinferencesystemsbackend.DTODb.MembershipFunctionTemplateDTODb
import com.mianeko.fuzzyinferencesystemsbackend.DTONet.MembershipFunctionDTONet
import com.mianeko.fuzzyinferencesystemsbackend.DTONet.MembershipFunctionTemplateDTONet
import com.mianeko.fuzzyinferencesystemsbackend.database.repositories.MembershipFunctionRepository
import com.mianeko.fuzzyinferencesystemsbackend.database.repositories.SystemRepository
import com.mianeko.fuzzyinferencesystemsbackend.database.repositories.VariableRepository
import com.mianeko.fuzzyinferencesystemsbackend.exceptions.FunctionNotExistException
import com.mianeko.fuzzyinferencesystemsbackend.exceptions.SystemNotExistException
import com.mianeko.fuzzyinferencesystemsbackend.exceptions.VariableNotExistException
import com.mianeko.fuzzyinferencesystemsbackend.lookupEntities.MembershipFunctionLookup
import com.mianeko.fuzzyinferencesystemsbackend.lookupEntities.PageSettings
import org.springframework.stereotype.Service

interface MembershipFunctionService : CrudService<MembershipFunctionDTONet, MembershipFunctionTemplateDTONet, MembershipFunctionLookup>

@Service
class MembershipFunctionServiceImpl(
    private val membershipFunctionRepository: MembershipFunctionRepository,
    private val systemRepository: SystemRepository,
    private val variableRepository: VariableRepository
) : MembershipFunctionService {
    private fun checkPath(systemId: Int, variableId: Int?) {
        if (!systemRepository.idExists(systemId))
            throw SystemNotExistException(systemId)

        if (variableId != null && !variableRepository.idExists(variableId))
            throw VariableNotExistException(variableId)
    }

    override fun create(
        model: MembershipFunctionTemplateDTONet
    ): MembershipFunctionDTONet {
        checkPath(model.systemId, model.variableId)

        val membershipFunction = membershipFunctionRepository.save(
            MembershipFunctionTemplateDTODb.fromModel(
                model.toModel(generateId())
            )
        ).toModel()
        return MembershipFunctionDTONet.fromModel(membershipFunction, model.systemId)
    }

    override fun get(lookupEntity: MembershipFunctionLookup): MembershipFunctionDTONet {
        checkPath(lookupEntity.systemId, lookupEntity.variableId)

        return lookupEntity.membershipFunctionId
            ?.let {
                membershipFunctionRepository.findOne(lookupEntity)?.let {
                    MembershipFunctionDTONet.fromModel(it.toModel(), lookupEntity.systemId)
                }
            }
            ?: throw FunctionNotExistException(lookupEntity.membershipFunctionId)
    }

    override fun getAll(
        lookupEntity: MembershipFunctionLookup,
        pageSettings: PageSettings
    ): List<MembershipFunctionDTONet> {
        checkPath(lookupEntity.systemId, lookupEntity.variableId)

        return membershipFunctionRepository
            .findAll(lookupEntity, pageSettings)
            .map { MembershipFunctionDTONet.fromModel(it.toModel(), lookupEntity.systemId) }
    }

    override fun update(model: MembershipFunctionTemplateDTONet): MembershipFunctionDTONet {
        checkPath(model.systemId, model.variableId)
        if (model.id == null || !membershipFunctionRepository.idExists(model.id))
            throw FunctionNotExistException(model.id)

        return MembershipFunctionDTONet.fromModel(
            membershipFunctionRepository.save(
                MembershipFunctionTemplateDTODb.fromModel(
                    model.toModel(model.id)
                )
            ).toModel(), model.systemId)
    }

    override fun delete(lookupEntity: MembershipFunctionLookup) {
        membershipFunctionRepository.delete(lookupEntity)
    }

}