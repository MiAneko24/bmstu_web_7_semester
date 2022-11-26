package com.mianeko.fuzzyinferencesystemsbackend.api

import com.mianeko.fuzzyinferencesystemsbackend.DTONet.ConsequentTemplateDTONet
import com.mianeko.fuzzyinferencesystemsbackend.api.exceptions.ConsequentNotFoundException
import com.mianeko.fuzzyinferencesystemsbackend.api.exceptions.IncorrectConsequentBody
import com.mianeko.fuzzyinferencesystemsbackend.api.exceptions.RuleNotFoundException
import com.mianeko.fuzzyinferencesystemsbackend.api.exceptions.SystemNotFoundException
import com.mianeko.fuzzyinferencesystemsbackend.api.models.ConsequentNet
import com.mianeko.fuzzyinferencesystemsbackend.api.models.ConsequentTemplateNet
import com.mianeko.fuzzyinferencesystemsbackend.exceptions.ConsequentNotExistException
import com.mianeko.fuzzyinferencesystemsbackend.exceptions.ConsequentSaveException
import com.mianeko.fuzzyinferencesystemsbackend.exceptions.RuleNotExistException
import com.mianeko.fuzzyinferencesystemsbackend.exceptions.SystemNotExistException
import com.mianeko.fuzzyinferencesystemsbackend.lookupEntities.ConsequentLookup
import com.mianeko.fuzzyinferencesystemsbackend.lookupEntities.PageSettings
import com.mianeko.fuzzyinferencesystemsbackend.services.AntecedentService
import com.mianeko.fuzzyinferencesystemsbackend.services.ConsequentService
import com.mianeko.fuzzyinferencesystemsbackend.services.RuleService
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
@RequestMapping("/systems/{systemId}/rules")
class ConsequentsApiHandler(
    private val ruleService: RuleService,
    private val consequentService: ConsequentService,
    private val antecedentService: AntecedentService,
    @Value("\${server.servlet.application-display-name}") val serverName: String
) {
    private val log: Logger = LoggerFactory.getLogger(this::class.java)

    @Operation(summary = "Get consequents by rule ID")
    @ApiResponses(value = [
        ApiResponse(responseCode = "200", description = "Successful Request",
            content = [(Content(
                mediaType = "application/json",
                array = ArraySchema(schema = Schema(implementation = ConsequentNet::class))
            ))]),
        ApiResponse(responseCode = "404", description = "Not Found",
            content = [(Content(schema = Schema(hidden = true)))]),
        ApiResponse(responseCode = "500", description = "Internal Server Error",
            content = [(Content(schema = Schema(hidden = true)))])]
    )
    @GetMapping("/{ruleId}/consequents")
    fun getConsequents(
        @PathVariable systemId: Int,
        @PathVariable ruleId: Int,
        @RequestParam(value = "page", defaultValue = "0", required = false) page: Int,
        @RequestParam(value = "size", defaultValue = Int.MAX_VALUE.toString(), required = false) size: Int
    ): List<ConsequentNet> {
        log.info("$serverName| Get consequents request")
        try {
            val lookup = ConsequentLookup(
                systemId = systemId,
                ruleId = ruleId,
                consequentId = null
            )

            return consequentService
                .getAll(lookup, PageSettings(page, size))
                .map { it.toModelNet() }
        } catch (e: SystemNotExistException) {
            throw SystemNotFoundException()
        } catch (e: RuleNotExistException) {
            throw RuleNotFoundException()
        } catch (e: ConsequentNotExistException) {
            throw ConsequentNotFoundException()
        }
    }

    @Operation(summary = "Add new consequent to system inference rule")
    @ApiResponses(value = [
        ApiResponse(responseCode = "201", description = "Successful Request",
            content = [(Content(
                mediaType = "application/json",
                schema = Schema(implementation = ConsequentNet::class)
            ))]),
        ApiResponse(responseCode = "400", description = "Bad Request",
            content = [(Content(schema = Schema(hidden = true)))]),
        ApiResponse(responseCode = "404", description = "Not Found",
            content = [(Content(schema = Schema(hidden = true)))]),
        ApiResponse(responseCode = "500", description = "Internal Server Error",
            content = [(Content(schema = Schema(hidden = true)))])]
    )
    @PostMapping("/{ruleId}/consequents")
    @ResponseStatus(HttpStatus.CREATED)
    fun addConsequent(
        @PathVariable systemId: Int,
        @PathVariable ruleId: Int,
        @RequestBody consequentTemplateNet: ConsequentTemplateNet
    ): ConsequentNet {
        log.info("$serverName| Post consequent request")
        try {
            return consequentService
                .create(ConsequentTemplateDTONet.fromModelNet(consequentTemplateNet, systemId, ruleId, null))
                .toModelNet()
        } catch (e: SystemNotExistException) {
            throw SystemNotFoundException()
        } catch (e: ConsequentSaveException) {
            throw IncorrectConsequentBody()
        } catch (e: RuleNotExistException) {
            throw RuleNotFoundException()
        } catch (e: ConsequentNotExistException) {
            throw ConsequentNotFoundException()
        }
    }

    @Operation(summary = "Get consequent by ID")
    @ApiResponses(value = [
        ApiResponse(responseCode = "200", description = "Successful Request",
            content = [(Content(
                mediaType = "application/json",
                schema = Schema(implementation = ConsequentNet::class)
            ))]),
        ApiResponse(responseCode = "404", description = "Not Found",
            content = [(Content(schema = Schema(hidden = true)))]),
        ApiResponse(responseCode = "500", description = "Internal Server Error",
            content = [(Content(schema = Schema(hidden = true)))])]
    )
    @GetMapping("/{ruleId}/consequents/{consequentId}")
    fun getConsequent(
        @PathVariable systemId: Int,
        @PathVariable ruleId: Int,
        @PathVariable consequentId: Int
    ): ConsequentNet {
        log.info("$serverName| Get consequent with id $consequentId request")
        try {
            val lookup = ConsequentLookup(
                systemId = systemId,
                ruleId = ruleId,
                consequentId = consequentId
            )

            return consequentService.get(lookup).toModelNet()
        } catch (e: SystemNotExistException) {
            throw SystemNotFoundException()
        } catch (e: RuleNotExistException) {
            throw RuleNotFoundException()
        } catch (e: ConsequentNotExistException) {
            throw ConsequentNotFoundException()
        }
    }

    @Operation(summary = "Update consequent by ID")
    @ApiResponses(value = [
        ApiResponse(responseCode = "200", description = "Successful Request",
            content = [(Content(
                mediaType = "application/json",
                schema = Schema(implementation = ConsequentNet::class)
            ))]),
        ApiResponse(responseCode = "400", description = "Bad Request",
            content = [(Content(schema = Schema(hidden = true)))]),
        ApiResponse(responseCode = "404", description = "Not Found",
            content = [(Content(schema = Schema(hidden = true)))]),
        ApiResponse(responseCode = "500", description = "Internal Server Error",
            content = [(Content(schema = Schema(hidden = true)))])]
    )
    @PutMapping("/{ruleId}/consequents/{consequentId}")
    fun putConsequent(
        @PathVariable systemId: Int,
        @PathVariable ruleId: Int,
        @PathVariable consequentId: Int,
        @RequestBody consequentNet: ConsequentTemplateNet
    ): ConsequentNet {
        log.info("$serverName| Put consequent request")
        try {
            return consequentService.update(
                ConsequentTemplateDTONet.fromModelNet(consequentNet, systemId, ruleId, consequentId)).toModelNet()
        } catch (e: SystemNotExistException) {
            throw SystemNotFoundException()
        } catch (e: ConsequentSaveException) {
            throw IncorrectConsequentBody()
        } catch (e: RuleNotExistException) {
            throw RuleNotFoundException()
        } catch (e: ConsequentNotExistException) {
            throw ConsequentNotFoundException()
        }
    }

    @Operation(summary = "Delete consequent by ID")
    @ApiResponses(value = [
        ApiResponse(responseCode = "200", description = "Successful Request",
            content = [(Content(schema = Schema(hidden = true)))]),
        ApiResponse(responseCode = "404", description = "Not Found",
            content = [(Content(schema = Schema(hidden = true)))]),
        ApiResponse(responseCode = "500", description = "Internal Server Error",
            content = [(Content(schema = Schema(hidden = true)))])]
    )
    @DeleteMapping("/{ruleId}/consequents/{consequentId}")
    fun deleteConsequent(
        @PathVariable systemId: Int,
        @PathVariable ruleId: Int,
        @PathVariable consequentId: Int
    ) {
        log.info("$serverName| Delete consequent request")
        val lookup = ConsequentLookup(
            systemId = systemId,
            ruleId = ruleId,
            consequentId = consequentId
        )

        consequentService.delete(lookup)
    }
}