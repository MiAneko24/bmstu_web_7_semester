package com.mianeko.fuzzyinferencesystemsbackend.api

import com.mianeko.fuzzyinferencesystemsbackend.api.exceptions.AntecedentNotFoundException
import com.mianeko.fuzzyinferencesystemsbackend.api.exceptions.IncorrectAntecedentBody
import com.mianeko.fuzzyinferencesystemsbackend.api.exceptions.InvalidAntecedentConnectionException
import com.mianeko.fuzzyinferencesystemsbackend.api.exceptions.SystemNotFoundException
import com.mianeko.fuzzyinferencesystemsbackend.api.models.AntecedentNet
import com.mianeko.fuzzyinferencesystemsbackend.api.models.AntecedentTemplateNet
import com.mianeko.fuzzyinferencesystemsbackend.exceptions.*
import com.mianeko.fuzzyinferencesystemsbackend.lookupEntities.AntecedentLookup
import com.mianeko.fuzzyinferencesystemsbackend.lookupEntities.PageSettings
import com.mianeko.fuzzyinferencesystemsbackend.services.AntecedentService
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.Parameter
import io.swagger.v3.oas.annotations.media.ArraySchema
import io.swagger.v3.oas.annotations.media.Content
import io.swagger.v3.oas.annotations.media.Schema
import io.swagger.v3.oas.annotations.responses.ApiResponse
import io.swagger.v3.oas.annotations.responses.ApiResponses
import org.springdoc.core.converters.models.Pageable
import org.springdoc.core.converters.models.PageableAsQueryParam
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/systems/{systemId}/antecedents")
class AntecedentsApiHandler(
    private val antecedentService: AntecedentService
) {
    @Operation(summary = "Get antecedents by system ID")
    @ApiResponses(value = [
        ApiResponse(responseCode = "200", description = "Successful Request",
            content = [(Content(
                mediaType = "application/json",
                array = ArraySchema(schema = Schema(implementation = AntecedentNet::class))
            ))]),
        ApiResponse(responseCode = "404", description = "Not Found",
            content = [(Content(schema = Schema(hidden = true)))]),
        ApiResponse(responseCode = "500", description = "Internal Server Error",
            content = [(Content(schema = Schema(hidden = true)))])]
    )
    @GetMapping
    fun getAntecedents(
        @PathVariable systemId: Int,
        @RequestParam(value = "page", defaultValue = "0", required = false) page: Int,
        @RequestParam(value = "size", defaultValue = Int.MAX_VALUE.toString(), required = false) size: Int,
    ): List<AntecedentNet> {
        val antecedentLookup = AntecedentLookup(
            systemId = systemId,
            ruleId = null,
            antecedentId = null
        )

        try {
            return antecedentService
                .getAll(antecedentLookup, PageSettings(page, size))
                .map { AntecedentNet.fromDTO(it) }
        } catch (e: SystemNotExistException) {
            throw SystemNotFoundException()
        }
    }

    @Operation(summary = "Add new antecedent to system")
    @ApiResponses(value = [
        ApiResponse(responseCode = "201", description = "Successful Request",
            content = [(Content(
                mediaType = "application/json",
                schema = Schema(implementation = AntecedentNet::class)
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
    fun addAntecedent(
        @PathVariable systemId: Int,
        @RequestBody antecedentTemplateNet: AntecedentTemplateNet
    ): AntecedentNet {
        try {
            return AntecedentNet.fromDTO(
                antecedentService.create(antecedentTemplateNet.toDTO(systemId, null))
            )
        } catch (e: AntecedentConnectionException) {
            throw InvalidAntecedentConnectionException()
        } catch (e: SystemNotExistException) {
            throw SystemNotFoundException()
        } catch (e: AntecedentSaveException) {
            throw IncorrectAntecedentBody()
        }
    }

    @Operation(summary = "Get antecedent by ID")
    @ApiResponses(value = [
        ApiResponse(responseCode = "200", description = "Successful Request",
            content = [(Content(
                mediaType = "application/json",
                schema = Schema(implementation = AntecedentNet::class)
            ))]),
        ApiResponse(responseCode = "404", description = "Not Found",
            content = [(Content(schema = Schema(hidden = true)))]),
        ApiResponse(responseCode = "500", description = "Internal Server Error",
            content = [(Content(schema = Schema(hidden = true)))])]
    )
    @GetMapping("/{antecedentId}")
    fun getAntecedent(
        @PathVariable systemId: Int,
        @PathVariable antecedentId: Int
    ): AntecedentNet {
        try {
            val antecedentLookup = AntecedentLookup(
                systemId = systemId,
                ruleId = null,
                antecedentId = antecedentId
            )

            return AntecedentNet.fromDTO(
                antecedentService.get(antecedentLookup)
            )
        } catch (e: SystemNotExistException) {
            throw SystemNotFoundException()
        } catch (e: AntecedentNotExistException) {
            throw AntecedentNotFoundException()
        }
    }

    @Operation(summary = "Update antecedent by ID")
    @ApiResponses(value = [
        ApiResponse(responseCode = "200", description = "Successful Request",
            content = [(Content(
                mediaType = "application/json",
                schema = Schema(implementation = AntecedentNet::class)
            ))]),
        ApiResponse(responseCode = "400", description = "Bad Request",
            content = [(Content(schema = Schema(hidden = true)))]),
        ApiResponse(responseCode = "404", description = "Not Found",
            content = [(Content(schema = Schema(hidden = true)))]),
        ApiResponse(responseCode = "500", description = "Internal Server Error",
            content = [(Content(schema = Schema(hidden = true)))])]
    )
    @PutMapping("/{antecedentId}")
    fun putAntecedent(
        @PathVariable systemId: Int,
        @PathVariable antecedentId: Int,
        @RequestBody antecedentNet: AntecedentTemplateNet
    ): AntecedentNet {
        try {
            return AntecedentNet.fromDTO(
                antecedentService.update(
                    antecedentNet.toDTO(systemId, antecedentId)))
        } catch (e: AntecedentConnectionException) {
            throw InvalidAntecedentConnectionException()
        } catch (e: SystemNotExistException) {
            throw SystemNotFoundException()
        } catch (e: AntecedentSaveException) {
            throw IncorrectAntecedentBody()
        }
    }

    @Operation(summary = "Delete antecedent by ID")
    @ApiResponses(value = [
        ApiResponse(responseCode = "200", description = "Successful Request",
            content = [(Content(schema = Schema(hidden = true)))]),
        ApiResponse(responseCode = "404", description = "Not Found",
            content = [(Content(schema = Schema(hidden = true)))]),
        ApiResponse(responseCode = "500", description = "Internal Server Error",
            content = [(Content(schema = Schema(hidden = true)))])]
    )
    @DeleteMapping("/{antecedentId}")
    fun deleteAntecedent(
        @PathVariable systemId: Int,
        @PathVariable antecedentId: Int
    ) {
        val antecedentLookup = AntecedentLookup(
            systemId = systemId,
            ruleId = null,
            antecedentId = antecedentId
        )

        return antecedentService.delete(antecedentLookup)
    }
}