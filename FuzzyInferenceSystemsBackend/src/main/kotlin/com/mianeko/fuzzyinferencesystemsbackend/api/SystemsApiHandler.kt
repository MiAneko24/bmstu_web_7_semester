package com.mianeko.fuzzyinferencesystemsbackend.api

import com.mianeko.fuzzyinferencesystemsbackend.DTONet.SystemTemplateDTONet
import com.mianeko.fuzzyinferencesystemsbackend.api.exceptions.IncorrectSystemBody
import com.mianeko.fuzzyinferencesystemsbackend.api.exceptions.SystemNotFoundException
import com.mianeko.fuzzyinferencesystemsbackend.api.models.PartialSystemNet
import com.mianeko.fuzzyinferencesystemsbackend.api.models.SystemNet
import com.mianeko.fuzzyinferencesystemsbackend.api.models.SystemTemplateNet
import com.mianeko.fuzzyinferencesystemsbackend.exceptions.SystemNotExistException
import com.mianeko.fuzzyinferencesystemsbackend.exceptions.SystemSaveException
import com.mianeko.fuzzyinferencesystemsbackend.lookupEntities.PageSettings
import com.mianeko.fuzzyinferencesystemsbackend.lookupEntities.SystemLookup
import com.mianeko.fuzzyinferencesystemsbackend.services.SystemService
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
@RequestMapping("/systems")
class SystemsApiHandler(
    private val systemService: SystemService,
    @Value("\${server.servlet.application-display-name}") val serverName: String
) {
    private val log: Logger = LoggerFactory.getLogger(this::class.java)

    @Operation(summary = "Get list of systems")
    @ApiResponses(value = [
        ApiResponse(responseCode = "200", description = "Successful Request",
            content = [(Content(
                mediaType = "application/json",
                array = ArraySchema(schema = Schema(implementation = PartialSystemNet::class))
            ))]),
        ApiResponse(responseCode = "404", description = "Not Found",
            content = [(Content(schema = Schema(hidden = true)))]),
        ApiResponse(responseCode = "500", description = "Internal Server Error",
            content = [(Content(schema = Schema(hidden = true)))])]
    )
    @GetMapping
    fun getSystems(
        @RequestParam(value = "page", defaultValue = "0", required = false) page: Int,
        @RequestParam(value = "size", defaultValue = Int.MAX_VALUE.toString(), required = false) size: Int
    ): List<PartialSystemNet> {
        log.info("$serverName| Get systems request")
        val lookup = SystemLookup(
            systemId = null
        )

        return systemService
            .getAll(lookup, PageSettings(page, size))
            .map { it.toPartialModelNet() }
    }

    @Operation(summary = "Add new fuzzy inference system")
    @ApiResponses(value = [
        ApiResponse(responseCode = "201", description = "Successful Request",
            content = [(Content(
                mediaType = "application/json",
                schema = Schema(implementation = PartialSystemNet::class)
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
    fun addSystem(@RequestBody systemTemplateNet: SystemTemplateNet): PartialSystemNet {
        log.info("$serverName| Post system request")
        try {
            return systemService.create(
                    SystemTemplateDTONet.fromModelNet(systemTemplateNet, null)
                ).toPartialModelNet()
        } catch (e: SystemSaveException) {
            throw IncorrectSystemBody()
        }
    }

    @Operation(summary = "Get system by ID")
    @ApiResponses(value = [
        ApiResponse(responseCode = "200", description = "Successful Request",
            content = [(Content(
                mediaType = "application/json",
                schema = Schema(implementation = SystemNet::class)
            ))]),
        ApiResponse(responseCode = "404", description = "Not Found",
            content = [(Content(schema = Schema(hidden = true)))]),
        ApiResponse(responseCode = "500", description = "Internal Server Error",
            content = [(Content(schema = Schema(hidden = true)))])]
    )
    @GetMapping("/{id}")
    fun getSystem(@PathVariable id: Int): SystemNet {
        log.info("$serverName| Get system with id = $id request")

        try {
            val lookup = SystemLookup(
                systemId = id
            )

            return systemService.get(lookup).toModelNet()
        } catch (e: SystemNotExistException) {
            throw SystemNotFoundException()
        }
    }

    @Operation(summary = "Update system by ID")
    @ApiResponses(value = [
        ApiResponse(responseCode = "200", description = "Successful Request",
            content = [(Content(
                mediaType = "application/json",
                schema = Schema(implementation = SystemNet::class)
            ))]),
        ApiResponse(responseCode = "400", description = "Bad Request",
            content = [(Content(schema = Schema(hidden = true)))]),
        ApiResponse(responseCode = "404", description = "Not Found",
            content = [(Content(schema = Schema(hidden = true)))]),
        ApiResponse(responseCode = "500", description = "Internal Server Error",
            content = [(Content(schema = Schema(hidden = true)))])]
    )
    @PutMapping("/{id}")
    fun putSystem(
        @PathVariable id: Int,
        @RequestBody systemNet: SystemTemplateNet
    ): SystemNet {
        log.info("$serverName| Put system request")
        try {
            return systemService.update(SystemTemplateDTONet.fromModelNet(systemNet, id)).toModelNet()
        } catch (e: SystemNotExistException) {
            throw SystemNotFoundException()
        } catch (e: SystemSaveException) {
            throw IncorrectSystemBody()
        }
    }

    @Operation(summary = "Delete system by ID")
    @ApiResponses(value = [
        ApiResponse(responseCode = "200", description = "Successful Request",
            content = [(Content(schema = Schema(hidden = true)))]),
        ApiResponse(responseCode = "404", description = "Not Found",
            content = [(Content(schema = Schema(hidden = true)))]),
        ApiResponse(responseCode = "500", description = "Internal Server Error",
            content = [(Content(schema = Schema(hidden = true)))])]
    )
    @DeleteMapping("/{id}")
    fun deleteSystem(@PathVariable id: Int) {
        log.info("$serverName| Delete system request")

        val lookup = SystemLookup(
            systemId = id
        )

        systemService.delete(lookup)
    }
}
