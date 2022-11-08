package com.mianeko.fuzzyinferencesystemsbackend.api

import com.mianeko.fuzzyinferencesystemsbackend.DTONet.MembershipFunctionTemplateDTONet
import com.mianeko.fuzzyinferencesystemsbackend.api.exceptions.*
import com.mianeko.fuzzyinferencesystemsbackend.api.models.MembershipFunctionNet
import com.mianeko.fuzzyinferencesystemsbackend.api.models.MembershipFunctionTemplateNet
import com.mianeko.fuzzyinferencesystemsbackend.api.models.PartialMembershipFunctionNet
import com.mianeko.fuzzyinferencesystemsbackend.exceptions.*
import com.mianeko.fuzzyinferencesystemsbackend.lookupEntities.MembershipFunctionLookup
import com.mianeko.fuzzyinferencesystemsbackend.lookupEntities.PageSettings
import com.mianeko.fuzzyinferencesystemsbackend.services.MembershipFunctionService
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.media.ArraySchema
import io.swagger.v3.oas.annotations.media.Content
import io.swagger.v3.oas.annotations.media.Schema
import io.swagger.v3.oas.annotations.responses.ApiResponse
import io.swagger.v3.oas.annotations.responses.ApiResponses
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Value
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/systems/{systemId}/membershipFunctions")
class MembershipFunctionsApiHandler(
    private val membershipFunctionService: MembershipFunctionService,
    @Value("\${server.servlet.application-display-name}") val serverName: String
) {
    private val log: Logger = LoggerFactory.getLogger(this::class.java)

    @Operation(summary = "Get membership functions by system ID")
    @ApiResponses(value = [
        ApiResponse(responseCode = "200", description = "Successful Request",
            content = [(Content(
                mediaType = "application/json",
                array = ArraySchema(schema = Schema(implementation = PartialMembershipFunctionNet::class))
            ))]),
        ApiResponse(responseCode = "404", description = "Not Found",
            content = [(Content(schema = Schema(hidden = true)))]),
        ApiResponse(responseCode = "500", description = "Internal Server Error",
            content = [(Content(schema = Schema(hidden = true)))])]
    )
    @GetMapping
    fun getMembershipFunctions(
        @PathVariable systemId: Int,
        @RequestParam(value = "page", defaultValue = "0", required = false) page: Int,
        @RequestParam(value = "size", defaultValue = Int.MAX_VALUE.toString(), required = false) size: Int
    ): List<PartialMembershipFunctionNet> {
        log.info("$serverName| Get membership functions request")
        try {
            val lookup = MembershipFunctionLookup(
                systemId = systemId,
                variableId = null,
                membershipFunctionId = null
            )

            return membershipFunctionService.getAll(
                lookup,
                PageSettings(page, size)
            )
                .map { it.toPartialModelNet() }
        } catch (e: SystemNotExistException) {
            throw SystemNotFoundException()
        }
    }

    @Operation(summary = "Add membership function to system")
    @ApiResponses(value = [
        ApiResponse(responseCode = "201", description = "Successful Request",
            content = [(Content(
                mediaType = "application/json",
                schema = Schema(implementation = PartialMembershipFunctionNet::class)
            ))]),
        ApiResponse(responseCode = "400", description = "Bad Request"),
        ApiResponse(responseCode = "404", description = "Not Found"),
        ApiResponse(responseCode = "500", description = "Internal Server Error")]
    )
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    fun addMembershipFunction(
        @PathVariable systemId: Int,
        @RequestBody membershipFunction: MembershipFunctionTemplateNet
    ): PartialMembershipFunctionNet {
        log.info("$serverName| Post membership functions request")
        try {
            return membershipFunctionService.create(
                    MembershipFunctionTemplateDTONet.fromModelNet(membershipFunction, systemId, null))
                .toPartialModelNet()
        } catch (e: SystemNotExistException) {
            throw SystemNotFoundException()
        } catch (e: FunctionSaveException) {
            throw IncorrectFunctionBody()
        } catch (e: FunctionTypeException) {
            throw InvalidFunctionTypeException()
        } catch (e: LinguisticHedgeException) {
            throw InvalidHedgeException()
        } catch (e: FunctionNotExistException) {
            throw MembershipFunctionNotFoundException()
        } catch (e: VariableNotExistException) {
            throw VariableNotFoundException()
        }
    }

    @Operation(summary = "Get membership function by ID")
    @ApiResponses(value = [
        ApiResponse(responseCode = "200", description = "Successful Request",
            content = [(Content(
                mediaType = "application/json",
                schema = Schema(implementation = MembershipFunctionNet::class)
            ))]),
        ApiResponse(responseCode = "404", description = "Not Found",
            content = [(Content(schema = Schema(hidden = true)))]),
        ApiResponse(responseCode = "500", description = "Internal Server Error",
            content = [(Content(schema = Schema(hidden = true)))])]
    )
    @GetMapping("/{membershipFunctionId}")
    fun getMembershipFunction(
        @PathVariable systemId: Int,
        @PathVariable membershipFunctionId: Int
    ): MembershipFunctionNet {
        log.info("$serverName| Get membership function with id $membershipFunctionId request")
        try {
            val lookup = MembershipFunctionLookup(
                systemId = systemId,
                variableId = null,
                membershipFunctionId = membershipFunctionId
            )

            return membershipFunctionService.get(lookup).toModelNet()
        } catch (e: SystemNotExistException) {
            throw SystemNotFoundException()
        } catch (e: FunctionNotExistException) {
            throw MembershipFunctionNotFoundException()
        }
    }

    @Operation(summary = "Update membership function by ID")
    @ApiResponses(value = [
        ApiResponse(responseCode = "200", description = "Successful Request",
            content = [(Content(
                mediaType = "application/json",
                schema = Schema(implementation = MembershipFunctionNet::class)
            ))]),
        ApiResponse(responseCode = "400", description = "Bad Request",
            content = [(Content(schema = Schema(hidden = true)))]),
        ApiResponse(responseCode = "404", description = "Not Found",
            content = [(Content(schema = Schema(hidden = true)))]),
        ApiResponse(responseCode = "500", description = "Internal Server Error",
            content = [(Content(schema = Schema(hidden = true)))])]
    )
    @PutMapping("/{membershipFunctionId}")
    fun putMembershipFunction(
        @PathVariable systemId: Int,
        @PathVariable membershipFunctionId: Int,
        @RequestBody membershipFunctionNet: MembershipFunctionTemplateNet
    ): MembershipFunctionNet {
        log.info("$serverName| Put membership function request")
        try {
            return membershipFunctionService.update(
                    MembershipFunctionTemplateDTONet.fromModelNet(membershipFunctionNet, systemId, membershipFunctionId)
                ).toModelNet()
        } catch (e: SystemNotExistException) {
            throw SystemNotFoundException()
        } catch (e: FunctionSaveException) {
            throw IncorrectFunctionBody()
        } catch (e: FunctionTypeException) {
            throw InvalidFunctionTypeException()
        } catch (e: LinguisticHedgeException) {
            throw InvalidHedgeException()
        } catch (e: FunctionNotExistException) {
            throw MembershipFunctionNotFoundException()
        } catch (e: VariableNotExistException) {
            throw VariableNotFoundException()
        }
    }

    @Operation(summary = "Delete membership function by ID")
    @ApiResponses(value = [
        ApiResponse(responseCode = "200", description = "Successful Request",
            content = [(Content(schema = Schema(hidden = true)))]),
        ApiResponse(responseCode = "404", description = "Not Found",
            content = [(Content(schema = Schema(hidden = true)))]),
        ApiResponse(responseCode = "500", description = "Internal Server Error",
            content = [(Content(schema = Schema(hidden = true)))])]
    )
    @DeleteMapping("/{membershipFunctionId}")
    fun deleteMembershipFunction(
        @PathVariable systemId: Int,
        @PathVariable membershipFunctionId: Int,
    ) {
        log.info("$serverName| Delete membership function request")
        val lookup = MembershipFunctionLookup(
            systemId = systemId,
            variableId = null,
            membershipFunctionId = membershipFunctionId
        )

        return membershipFunctionService
            .delete(lookup)
    }
}
