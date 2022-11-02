package com.mianeko.fuzzyinferencesystemsbackend.api

import com.mianeko.fuzzyinferencesystemsbackend.api.exceptions.*
import com.mianeko.fuzzyinferencesystemsbackend.api.models.*
import com.mianeko.fuzzyinferencesystemsbackend.exceptions.*
import com.mianeko.fuzzyinferencesystemsbackend.lookupEntities.AntecedentLookup
import com.mianeko.fuzzyinferencesystemsbackend.lookupEntities.ConsequentLookup
import com.mianeko.fuzzyinferencesystemsbackend.lookupEntities.PageSettings
import com.mianeko.fuzzyinferencesystemsbackend.lookupEntities.RuleLookup
import com.mianeko.fuzzyinferencesystemsbackend.services.AntecedentService
import com.mianeko.fuzzyinferencesystemsbackend.services.ConsequentService
import com.mianeko.fuzzyinferencesystemsbackend.services.RuleService
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.media.ArraySchema
import io.swagger.v3.oas.annotations.media.Content
import io.swagger.v3.oas.annotations.media.Schema
import io.swagger.v3.oas.annotations.responses.ApiResponse
import io.swagger.v3.oas.annotations.responses.ApiResponses
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/systems/{systemId}/rules")
class RulesApiHandler(
    private val ruleService: RuleService,
    private val consequentService: ConsequentService,
    private val antecedentService: AntecedentService
) {

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
        try {
            val lookup = RuleLookup(
                systemId = systemId,
                ruleId = null
            )

            return ruleService
                .getAll(lookup, PageSettings(page, size))
                .filter { it.systemId == systemId }
                .map { PartialRuleNet.fromDTO(it) }
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
        try {
            return PartialRuleNet.fromDTO(ruleService.create(ruleTemplateNet.toDTO(systemId, null)))
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
            val lookup = RuleLookup(
                systemId = systemId,
                ruleId = ruleId
            )

            return RuleNet.fromDTO(ruleService.get(lookup))
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
        try {
            return RuleNet.fromDTO(ruleService.update(ruleNet.toDTO(systemId, ruleId)))
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
        try {
            val lookup = RuleLookup(
                systemId = systemId,
                ruleId = ruleId
            )

            val rule = ruleService.get(lookup)
            return RuleNet.fromDTO(ruleService.update(rulePatchNet.applyTo(rule)))
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
    fun deleteRule(@PathVariable systemId: Int, @PathVariable ruleId: Int): Unit {
        val lookup = RuleLookup(
            systemId = systemId,
            ruleId = ruleId
        )

        return ruleService.delete(lookup)
    }

    // RULES ANTECEDENTS API

    @Operation(summary = "Get antecedents by rule ID")
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
    @GetMapping("/{ruleId}/antecedents")
    fun getAntecedents(
        @PathVariable systemId: Int,
        @PathVariable ruleId: Int,
        @RequestParam(value = "page", defaultValue = "0", required = false) page: Int,
        @RequestParam(value = "size", defaultValue = Int.MAX_VALUE.toString(), required = false) size: Int,
    ): List<AntecedentNet> {
        try {
            val antecedentLookup = AntecedentLookup(
                systemId = systemId,
                ruleId = ruleId,
                antecedentId = null
            )

            return antecedentService
                .getAll(antecedentLookup, PageSettings(page, size))
                .map { AntecedentNet.fromDTO(it) }
        } catch (e: SystemNotExistException) {
            throw SystemNotFoundException()
        } catch (e: RuleNotExistException) {
            throw RuleNotFoundException()
        }
    }

    // CONSEQUENTS API
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
        try {
            val lookup = ConsequentLookup(
                systemId = systemId,
                ruleId = ruleId,
                consequentId = null
            )

            return consequentService
                .getAll(lookup, PageSettings(page, size))
                .map { ConsequentNet.fromDTO(it) }
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
        try {
            return ConsequentNet.fromDTO(
                consequentService.create(consequentTemplateNet.toDTO(systemId, ruleId, null))
            )
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
        try {
            val lookup = ConsequentLookup(
                systemId = systemId,
                ruleId = ruleId,
                consequentId = consequentId
            )

            return ConsequentNet.fromDTO(
                consequentService.get(lookup)
            )
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
        try {
            return ConsequentNet.fromDTO(
                consequentService.update(
                    consequentNet.toDTO(systemId, ruleId, consequentId)))
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
        val lookup = ConsequentLookup(
            systemId = systemId,
            ruleId = ruleId,
            consequentId = consequentId
        )

        consequentService.delete(lookup)
    }
}

