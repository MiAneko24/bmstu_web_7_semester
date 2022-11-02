package com.mianeko.fuzzyinferencesystemsbackend.api

import com.mianeko.fuzzyinferencesystemsbackend.api.exceptions.IncorrectVariableBody
import com.mianeko.fuzzyinferencesystemsbackend.api.exceptions.IncorrectVariableRange
import com.mianeko.fuzzyinferencesystemsbackend.api.exceptions.SystemNotFoundException
import com.mianeko.fuzzyinferencesystemsbackend.api.exceptions.VariableNotFoundException
import com.mianeko.fuzzyinferencesystemsbackend.api.models.*
import com.mianeko.fuzzyinferencesystemsbackend.exceptions.SystemNotExistException
import com.mianeko.fuzzyinferencesystemsbackend.exceptions.VariableNotExistException
import com.mianeko.fuzzyinferencesystemsbackend.exceptions.VariableSaveException
import com.mianeko.fuzzyinferencesystemsbackend.lookupEntities.MembershipFunctionLookup
import com.mianeko.fuzzyinferencesystemsbackend.lookupEntities.PageSettings
import com.mianeko.fuzzyinferencesystemsbackend.lookupEntities.VariableLookup
import com.mianeko.fuzzyinferencesystemsbackend.services.MembershipFunctionService
import com.mianeko.fuzzyinferencesystemsbackend.services.VariableService
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.media.ArraySchema
import io.swagger.v3.oas.annotations.media.Content
import io.swagger.v3.oas.annotations.media.Schema
import io.swagger.v3.oas.annotations.responses.ApiResponse
import io.swagger.v3.oas.annotations.responses.ApiResponses
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/systems/{systemId}/variables")
class VariablesApiHandler(
    private val variableService: VariableService,
    private val membershipFunctionService: MembershipFunctionService
) {
    @Operation(summary = "Get variables by system ID")
    @ApiResponses(value = [
        ApiResponse(responseCode = "200", description = "Successful Request",
            content = [(Content(
                mediaType = "application/json",
                array = ArraySchema(schema = Schema(implementation = PartialVariableNet::class))
            ))]),
        ApiResponse(responseCode = "404", description = "Not Found",
            content = [(Content(schema = Schema(hidden = true)))]),
        ApiResponse(responseCode = "500", description = "Internal Server Error",
            content = [(Content(schema = Schema(hidden = true)))])]
    )
    @GetMapping
    fun getVariables(
        @PathVariable systemId: Int,
        @RequestParam(value = "page", defaultValue = "0", required = false) page: Int,
        @RequestParam(value = "size", defaultValue = Int.MAX_VALUE.toString(), required = false) size: Int
    ): List<PartialVariableNet> {
        try {
            val lookup = VariableLookup(
                systemId = systemId,
                variableId = null
            )
            return variableService
                .getAll(lookup, PageSettings(page, size))
                .map { PartialVariableNet.fromDTO(it) }
        } catch (e: SystemNotExistException) {
            throw SystemNotFoundException()
        }
    }

    @Operation(summary = "Add new variable to system")
    @ApiResponses(value = [
        ApiResponse(responseCode = "201", description = "Successful Request",
            content = [(Content(
                mediaType = "application/json",
                schema = Schema(implementation = PartialVariableNet::class)
            ))]),
        ApiResponse(responseCode = "400", description = "Bad Request",
            content = [(Content(schema = Schema(hidden = true)))]),
        ApiResponse(responseCode = "404", description = "Not Found",
            content = [(Content(schema = Schema(hidden = true)))]),
        ApiResponse(responseCode = "500", description = "Internal Server Error",
            content = [(Content(schema = Schema(hidden = true)))])]
    )
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    fun addVariable(
        @PathVariable systemId: Int,
        @RequestBody variableTemplateNet: VariableTemplateNet
    ): PartialVariableNet {
        try {
            if (variableTemplateNet.maxValue <= variableTemplateNet.minValue ||
                (variableTemplateNet.value != null &&
                        (variableTemplateNet.minValue > variableTemplateNet.value ||
                        variableTemplateNet.maxValue < variableTemplateNet.value)))
                throw IncorrectVariableRange()

            return PartialVariableNet.fromDTO(
                variableService.create(
                    variableTemplateNet.toDTO(systemId, null)
                )
            )
        } catch (e: SystemNotExistException) {
            throw SystemNotFoundException()
        } catch (e: VariableSaveException) {
            throw IncorrectVariableBody()
        }
    }

    @Operation(summary = "Get variable by ID")
    @ApiResponses(value = [
        ApiResponse(responseCode = "200", description = "Successful Request",
            content = [(Content(
                mediaType = "application/json",
                schema = Schema(implementation = VariableNet::class)
            ))]),
        ApiResponse(responseCode = "404", description = "Not Found",
            content = [(Content(schema = Schema(hidden = true)))]),
        ApiResponse(responseCode = "500", description = "Internal Server Error",
            content = [(Content(schema = Schema(hidden = true)))])]
    )
    @GetMapping("/{variableId}")
    fun getVariable(
        @PathVariable systemId: Int,
        @PathVariable variableId: Int
    ): VariableNet {
        try {
            val lookup = VariableLookup(
                systemId = systemId,
                variableId = variableId
            )

            return VariableNet.fromDTO(variableService.get(lookup))
        } catch (e: SystemNotExistException) {
            throw SystemNotFoundException()
        } catch (e: VariableNotExistException) {
            throw VariableNotFoundException()
        }
    }

    @Operation(summary = "Update variable by ID")
    @ApiResponses(value = [
        ApiResponse(responseCode = "200", description = "Successful Request",
            content = [(Content(
                mediaType = "application/json",
                schema = Schema(implementation = VariableNet::class)
            ))]),
        ApiResponse(responseCode = "400", description = "Bad Request",
            content = [(Content(schema = Schema(hidden = true)))]),
        ApiResponse(responseCode = "404", description = "Not Found",
            content = [(Content(schema = Schema(hidden = true)))]),
        ApiResponse(responseCode = "500", description = "Internal Server Error",
            content = [(Content(schema = Schema(hidden = true)))])]
    )
    @PutMapping("/{variableId}")
    fun putVariable(
        @PathVariable systemId: Int,
        @PathVariable variableId: Int,
        @RequestBody variableNet: VariableTemplateNet
    ): VariableNet {
        try {
            if (variableNet.maxValue <= variableNet.minValue ||
                (variableNet.value != null &&
                        (variableNet.minValue > variableNet.value ||
                                variableNet.maxValue < variableNet.value)))
                throw IncorrectVariableRange()

            return VariableNet.fromDTO(
                variableService.update(
                    variableNet.toDTO(systemId, variableId)
                )
            )
        } catch (e: SystemNotExistException) {
            throw SystemNotFoundException()
        } catch (e: VariableNotExistException) {
            throw VariableNotFoundException()
        }
    }

    @Operation(summary = "Delete variable by ID")
    @ApiResponses(value = [
        ApiResponse(responseCode = "200", description = "Successful Request",
            content = [(Content(schema = Schema(hidden = true)))]),
        ApiResponse(responseCode = "404", description = "Not Found",
            content = [(Content(schema = Schema(hidden = true)))]),
        ApiResponse(responseCode = "500", description = "Internal Server Error",
            content = [(Content(schema = Schema(hidden = true)))])]
    )
    @DeleteMapping("/{variableId}")
    fun deleteVariable(
        @PathVariable systemId: Int,
        @PathVariable variableId: Int
    ) {
        val lookup = VariableLookup(
            systemId = systemId,
            variableId = variableId
        )

        variableService.delete(lookup)
    }

    @Operation(summary = "Get membership functions by variable ID")
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
    @GetMapping("/{variableId}/membershipFunctions")
    fun getVariableFunctions(
        @PathVariable systemId: Int,
        @PathVariable variableId: Int,
        @RequestParam(value = "page", defaultValue = "0", required = false) page: Int,
        @RequestParam(value = "size", defaultValue = Int.MAX_VALUE.toString(), required = false) size: Int
    ): List<PartialMembershipFunctionNet> {
        try {
            val lookup = MembershipFunctionLookup(
                systemId = systemId,
                variableId = variableId,
                membershipFunctionId = null
            )

            return membershipFunctionService
                .getAll(lookup, PageSettings(page, size))
                .filter { it.variable?.id == variableId }
                .map { PartialMembershipFunctionNet.fromDTO(it) }
        } catch (e: SystemNotExistException) {
            throw SystemNotFoundException()
        } catch (e: VariableNotExistException) {
            throw VariableNotFoundException()
        }
    }
}
