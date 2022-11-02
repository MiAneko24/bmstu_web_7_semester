package com.mianeko.fuzzyinferencesystemsbackend.api

import com.mianeko.fuzzyinferencesystemsbackend.api.exceptions.*
import com.mianeko.fuzzyinferencesystemsbackend.api.models.*
import com.mianeko.fuzzyinferencesystemsbackend.exceptions.*
import com.mianeko.fuzzyinferencesystemsbackend.lookupEntities.MembershipFunctionLookup
import com.mianeko.fuzzyinferencesystemsbackend.lookupEntities.PageSettings
import com.mianeko.fuzzyinferencesystemsbackend.services.MembershipFunctionService
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.media.ArraySchema
import io.swagger.v3.oas.annotations.media.Content
import io.swagger.v3.oas.annotations.media.ExampleObject
import io.swagger.v3.oas.annotations.media.Schema
import io.swagger.v3.oas.annotations.responses.ApiResponse
import io.swagger.v3.oas.annotations.responses.ApiResponses
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.*
import javax.websocket.MessageHandler.Partial

@RestController
@RequestMapping("/systems/{systemId}/membershipFunctions")
class MembershipFunctionsApiHandler(
    private val membershipFunctionService: MembershipFunctionService
) {
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
                .map { PartialMembershipFunctionNet.fromDTO(it) }
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
        try {
            return PartialMembershipFunctionNet.fromDTO(
                membershipFunctionService.create(
                    membershipFunction.toDTO(systemId, null)
                )
            )
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
        try {
            val lookup = MembershipFunctionLookup(
                systemId = systemId,
                variableId = null,
                membershipFunctionId = membershipFunctionId
            )

            return MembershipFunctionNet.fromDTO(membershipFunctionService.get(lookup))
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
        try {
            return MembershipFunctionNet.fromDTO(
                membershipFunctionService.update(
                    membershipFunctionNet.toDTO(systemId, membershipFunctionId)
                )
            )
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
        val lookup = MembershipFunctionLookup(
            systemId = systemId,
            variableId = null,
            membershipFunctionId = membershipFunctionId
        )

        return membershipFunctionService
            .delete(lookup)
    }
}
