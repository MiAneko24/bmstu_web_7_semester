package com.mianeko.fuzzyinferencesystemsbackend.services

import com.mianeko.fuzzyinferencesystemsbackend.DTO.MembershipFunctionDTO
import com.mianeko.fuzzyinferencesystemsbackend.database.repositories.MembershipFunctionRepository
import com.mianeko.fuzzyinferencesystemsbackend.database.repositories.SystemRepository
import com.mianeko.fuzzyinferencesystemsbackend.database.repositories.VariableRepository
import com.mianeko.fuzzyinferencesystemsbackend.exceptions.FunctionNotExistException
import com.mianeko.fuzzyinferencesystemsbackend.exceptions.SystemNotExistException
import com.mianeko.fuzzyinferencesystemsbackend.exceptions.VariableNotExistException
import com.mianeko.fuzzyinferencesystemsbackend.lookupEntities.MembershipFunctionLookup
import com.mianeko.fuzzyinferencesystemsbackend.lookupEntities.PageSettings
import com.mianeko.fuzzyinferencesystemsbackend.services.models.MembershipFunctionTemplateDTO
import org.springdoc.core.converters.models.Pageable
import org.springframework.stereotype.Service

interface MembershipFunctionService : CrudService<MembershipFunctionDTO, MembershipFunctionTemplateDTO, MembershipFunctionLookup>

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
        model: MembershipFunctionTemplateDTO
    ): MembershipFunctionDTO {
        checkPath(model.systemId, model.variableId)

        val membershipFunction = membershipFunctionRepository
            .save(model.toModel(generateId()))
        return membershipFunction.toDTO(model.systemId)
    }

    override fun get(lookupEntity: MembershipFunctionLookup): MembershipFunctionDTO {
        checkPath(lookupEntity.systemId, lookupEntity.variableId)

        return lookupEntity.membershipFunctionId
            ?.let {
                membershipFunctionRepository.findOne(lookupEntity)?.toDTO(lookupEntity.systemId)
            }
            ?: throw FunctionNotExistException(lookupEntity.membershipFunctionId)
    }

    override fun getAll(
        lookupEntity: MembershipFunctionLookup,
        pageSettings: PageSettings
    ): List<MembershipFunctionDTO> {
        checkPath(lookupEntity.systemId, lookupEntity.variableId)

        return membershipFunctionRepository
            .findAll(lookupEntity, pageSettings)
            .map { it.toDTO(lookupEntity.systemId) }
    }

    override fun update(model: MembershipFunctionTemplateDTO): MembershipFunctionDTO {
        checkPath(model.systemId, model.variableId)
        if (model.id == null || !membershipFunctionRepository.idExists(model.id))
            throw FunctionNotExistException(model.id)

        return membershipFunctionRepository
            .save(model.toModel(model.id))
            .toDTO(model.systemId)
    }

    override fun delete(lookupEntity: MembershipFunctionLookup) {
        membershipFunctionRepository.delete(lookupEntity)
    }

}