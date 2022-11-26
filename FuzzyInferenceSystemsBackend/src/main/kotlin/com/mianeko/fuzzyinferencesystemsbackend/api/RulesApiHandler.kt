package com.mianeko.fuzzyinferencesystemsbackend.api

import com.mianeko.fuzzyinferencesystemsbackend.DTONet.RuleTemplateDTONet
import com.mianeko.fuzzyinferencesystemsbackend.api.exceptions.*
import com.mianeko.fuzzyinferencesystemsbackend.api.models.PartialRuleNet
import com.mianeko.fuzzyinferencesystemsbackend.api.models.RuleNet
import com.mianeko.fuzzyinferencesystemsbackend.api.models.RulePatchNet
import com.mianeko.fuzzyinferencesystemsbackend.api.models.RuleTemplateNet
import com.mianeko.fuzzyinferencesystemsbackend.exceptions.*
import com.mianeko.fuzzyinferencesystemsbackend.lookupEntities.PageSettings
import com.mianeko.fuzzyinferencesystemsbackend.lookupEntities.RuleLookup
import com.mianeko.fuzzyinferencesystemsbackend.services.AntecedentService
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
class RulesApiHandler(
    private val ruleService: RuleService,
    private val antecedentService: AntecedentService,
    @Value("\${server.servlet.application-display-name}") val serverName: String
) {
    private val log: Logger = LoggerFactory.getLogger(this::class.java)

//    RULES API
    @Operation(summary = "Get rules by system ID")
    @ApiResponses(value = [
        ApiResponse(responseCode = "200", description = "Successful Request",
            content = [(Content(
                mediaType = "application/json",
                array = ArraySchema(schema = Schema(implementation = PartialRuleNet::class))
            ))]),
        ApiResponse(responseCode = "404", description = "Not Found",
            content = [(Content(schema = Schema(hidden = true)))]),
        ApiResponse(responseCode = "500", description = "Internal Server Error",
            content = [(Content(schema = Schema(hidden = true)))])]
    )
    @GetMapping
    fun getRules(
        @PathVariable systemId: Int,
        @RequestParam(value = "page", defaultValue = "0", required = false) page: Int,
        @RequestParam(value = "size", defaultValue = Int.MAX_VALUE.toString(), required = false) size: Int
    ): List<PartialRuleNet> {
        log.info("$serverName| Get rules request")
        try {
            val lookup = RuleLookup(
                systemId = systemId,
                ruleId = null
            )

            return ruleService
                .getAll(lookup, PageSettings(page, size))
                .filter { it.systemId == systemId }
                .map { it.toPartialModelNet() }
        } catch (e: SystemNotExistException) {
            throw SystemNotFoundException()
        }
    }

    @Operation(summary = "Add new rule to system")
    @ApiResponses(value = [
        ApiResponse(responseCode = "201", description = "Successful Request",
            content = [(Content(
                mediaType = "application/json",
                schema = Schema(implementation = PartialRuleNet::class)
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
    fun addRule(
        @PathVariable systemId: Int,
        @RequestBody ruleTemplateNet: RuleTemplateNet
    ): PartialRuleNet {
        log.info("$serverName| Post rule request")
        try {
            return ruleService
                .create(RuleTemplateDTONet.fromModelNet(ruleTemplateNet, systemId, null))
                .toPartialModelNet()
        } catch (e: SystemNotExistException) {
            throw SystemNotFoundException()
        } catch (e: RuleSaveException) {
            throw IncorrectRuleBody()
        } catch (e: AntecedentConnectionException) {
            throw InvalidAntecedentConnectionException()
        }
    }

    @Operation(summary = "Get rule by ID")
    @ApiResponses(value = [
        ApiResponse(responseCode = "200", description = "Successful Request",
            content = [(Content(
                mediaType = "application/json",
                schema = Schema(implementation = RuleNet::class)
            ))]),
        ApiResponse(responseCode = "404", description = "Not Found",
            content = [(Content(schema = Schema(hidden = true)))]),
        ApiResponse(responseCode = "500", description = "Internal Server Error",
            content = [(Content(schema = Schema(hidden = true)))])]
    )
    @GetMapping("/{ruleId}")
    fun getRule(@PathVariable systemId: Int, @PathVariable ruleId: Int): RuleNet {
        try {
            log.info("$serverName| Get rule with id $ruleId request")
            val lookup = RuleLookup(
                systemId = systemId,
                ruleId = ruleId
            )

            return ruleService.get(lookup).toModelNet()
        } catch (e: SystemNotExistException) {
            throw SystemNotFoundException()
        } catch (e: RuleSaveException) {
            throw IncorrectRuleBody()
        } catch (e: AntecedentConnectionException) {
            throw InvalidAntecedentConnectionException()
        } catch (e: RuleNotExistException) {
            throw RuleNotFoundException()
        }
    }

    @Operation(summary = "Update rule by ID")
    @ApiResponses(value = [
        ApiResponse(responseCode = "200", description = "Successful Request",
            content = [(Content(
                mediaType = "application/json",
                schema = Schema(implementation = RuleNet::class)
            ))]),
        ApiResponse(responseCode = "400", description = "Bad Request",
            content = [(Content(schema = Schema(hidden = true)))]),
        ApiResponse(responseCode = "404", description = "Not Found",
            content = [(Content(schema = Schema(hidden = true)))]),
        ApiResponse(responseCode = "500", description = "Internal Server Error",
            content = [(Content(schema = Schema(hidden = true)))])]
    )
    @PutMapping("/{ruleId}")
    fun putRule(
        @PathVariable systemId: Int,
        @PathVariable ruleId: Int,
        @RequestBody ruleNet: RuleTemplateNet
    ): RuleNet {
        log.info("$serverName| Put rule request")
        try {
            return ruleService
                .update(RuleTemplateDTONet.fromModelNet(ruleNet, systemId, ruleId))
                .toModelNet()
        } catch (e: SystemNotExistException) {
            throw SystemNotFoundException()
        } catch (e: RuleSaveException) {
            throw IncorrectRuleBody()
        } catch (e: AntecedentConnectionException) {
            throw InvalidAntecedentConnectionException()
        } catch (e: RuleNotExistException) {
            throw RuleNotFoundException()
        }
    }

    @Operation(summary = "Change rule antecedents")
    @ApiResponses(value = [
        ApiResponse(responseCode = "200", description = "Successful Request",
            content = [(Content(
                mediaType = "application/json",
                schema = Schema(implementation = RuleNet::class)
            ))]),
        ApiResponse(responseCode = "400", description = "Bad Request",
            content = [(Content(schema = Schema(hidden = true)))]),
        ApiResponse(responseCode = "404", description = "Not Found",
            content = [(Content(schema = Schema(hidden = true)))]),
        ApiResponse(responseCode = "500", description = "Internal Server Error",
            content = [(Content(schema = Schema(hidden = true)))])]
    )
    @PatchMapping("/{ruleId}")
    fun patchRule(
        @PathVariable systemId: Int,
        @PathVariable ruleId: Int,
        @RequestBody rulePatchNet: RulePatchNet
    ): RuleNet {
        log.info("$serverName| Patch rule request")
        try {
            val lookup = RuleLookup(
                systemId = systemId,
                ruleId = ruleId
            )

            val rule = ruleService.get(lookup)
            return ruleService.update(rulePatchNet.applyTo(rule)).toModelNet()
        } catch (e: SystemNotExistException) {
            throw SystemNotFoundException()
        } catch (e: RuleSaveException) {
            throw IncorrectRuleBody()
        } catch (e: AntecedentConnectionException) {
            throw InvalidAntecedentConnectionException()
        } catch (e: RuleNotExistException) {
            throw RuleNotFoundException()
        } catch (e: AntecedentNotExistException) {
            throw AntecedentNotFoundException()
        }
    }

    @Operation(summary = "Delete rule by ID")
    @ApiResponses(value = [
        ApiResponse(responseCode = "200", description = "Successful Request",
            content = [(Content(schema = Schema(hidden = true)))]),
        ApiResponse(responseCode = "404", description = "Not Found",
            content = [(Content(schema = Schema(hidden = true)))]),
        ApiResponse(responseCode = "500", description = "Internal Server Error",
            content = [(Content(schema = Schema(hidden = true)))])]
    )
    @DeleteMapping("/{ruleId}")
    fun deleteRule(
        @PathVariable systemId: Int,
        @PathVariable ruleId: Int
    ): Unit {
        log.info("$serverName| Delete rule request")
        val lookup = RuleLookup(
            systemId = systemId,
            ruleId = ruleId
        )

        return ruleService.delete(lookup)
    }

    // RULES ANTECEDENTS API

//    @Operation(summary = "Get antecedents by rule ID")
//    @ApiResponses(value = [
//        ApiResponse(responseCode = "200", description = "Successful Request",
//            content = [(Content(
//                mediaType = "application/json",
//                array = ArraySchema(schema = Schema(implementation = AntecedentNet::class))
//            ))]),
//        ApiResponse(responseCode = "404", description = "Not Found",
//            content = [(Content(schema = Schema(hidden = true)))]),
//        ApiResponse(responseCode = "500", description = "Internal Server Error",
//            content = [(Content(schema = Schema(hidden = true)))])]
//    )
//    @GetMapping("/{ruleId}/antecedents")
//    fun getAntecedents(
//        @PathVariable systemId: Int,
//        @PathVariable ruleId: Int,
//        @RequestParam(value = "page", defaultValue = "0", required = false) page: Int,
//        @RequestParam(value = "size", defaultValue = Int.MAX_VALUE.toString(), required = false) size: Int,
//    ): List<AntecedentNet> {
//        log.info("$serverName| Get rule antecedents request")
//        try {
//            val antecedentLookup = AntecedentLookup(
//                systemId = systemId,
//                ruleId = ruleId,
//                antecedentId = null
//            )
//
//            return antecedentService
//                .getAll(antecedentLookup, PageSettings(page, size))
//                .map { it.toModelNet() }
//        } catch (e: SystemNotExistException) {
//            throw SystemNotFoundException()
//        } catch (e: RuleNotExistException) {
//            throw RuleNotFoundException()
//        }
//    }

    // CONSEQUENTS API
}

