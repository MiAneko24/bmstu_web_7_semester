package com.mianeko.fuzzyinferencesystemsbackend.api

import com.mianeko.fuzzyinferencesystemsbackend.DTONet.JobTemplateDTONet
import com.mianeko.fuzzyinferencesystemsbackend.api.exceptions.*
import com.mianeko.fuzzyinferencesystemsbackend.api.models.JobNet
import com.mianeko.fuzzyinferencesystemsbackend.api.models.JobTemplateNet
import com.mianeko.fuzzyinferencesystemsbackend.api.models.OutputResultNet
import com.mianeko.fuzzyinferencesystemsbackend.exceptions.*
import com.mianeko.fuzzyinferencesystemsbackend.services.JobsService
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.media.ArraySchema
import io.swagger.v3.oas.annotations.media.Content
import io.swagger.v3.oas.annotations.media.Schema
import io.swagger.v3.oas.annotations.responses.ApiResponse
import io.swagger.v3.oas.annotations.responses.ApiResponses
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Value
import org.springframework.web.bind.annotation.*
import java.util.*

@RestController
@RequestMapping("/systems/{systemId}/jobs")
class JobsApiHandler(
    private val jobsService: JobsService,
    @Value("\${server.servlet.application-display-name}") val serverName: String
) {
    private val log: Logger = LoggerFactory.getLogger(this::class.java)

    @Operation(summary = "Get result of inference system's work with defined by job input values")
    @ApiResponses(value = [
        ApiResponse(responseCode = "200", description = "Successful Request",
            content = [(Content(
                mediaType = "application/json",
                array = ArraySchema(schema = Schema(implementation = OutputResultNet::class))
            ))]),
        ApiResponse(responseCode = "404", description = "Not Found",
            content = [(Content(schema = Schema(hidden = true)))]),
        ApiResponse(responseCode = "500", description = "Internal Server Error",
            content = [(Content(schema = Schema(hidden = true)))])]
    )
    @GetMapping("/{id}")
    fun getOutput(
        @PathVariable systemId: Int,
        @PathVariable id: UUID
    ): List<OutputResultNet> {
        log.info("$serverName| Get system output request")
        try {
            return jobsService.executeJob(systemId, id)
                .map { it.toModelNet() }
        } catch (e: SystemNotExistException) {
            throw SystemNotFoundException()
        } catch (e: JobNotExistException) {
            throw JobNotFoundException()
        } catch (e: VariableNotExistException) {
            throw VariableNotFoundException()
        } catch (e: VariableSaveException) {
            throw InvalidVariableValue()
        }
    }

    @Operation(summary = "Add new job for inference system")
    @ApiResponses(value = [
        ApiResponse(responseCode = "200", description = "Successful Request",
            content = [(Content(
                mediaType = "application/json",
                schema = Schema(implementation = JobNet::class)
            ))]),
        ApiResponse(responseCode = "404", description = "Not Found",
            content = [(Content(schema = Schema(hidden = true)))]),
        ApiResponse(responseCode = "500", description = "Internal Server Error",
            content = [(Content(schema = Schema(hidden = true)))])]
    )
    @PostMapping()
    fun addJob(
        @PathVariable systemId: Int,
        @RequestBody jobTemplateNet: JobTemplateNet
    ): JobNet {
        log.info("$serverName| Add job for system output request")

        try {
            return jobsService.addJob(
                JobTemplateDTONet.fromModelNet(jobTemplateNet, systemId)
            ).toModelNet()
        } catch (e: SystemNotExistException) {
            throw SystemNotFoundException()
        } catch (e: JobSaveException) {
            throw InvalidJobBody()
        } catch (e: VariableNotExistException) {
            throw VariableNotFoundException()
        } catch (e: JobTooMuchInputParametersException) {
            throw IncorrectJobVariablesException()
        } catch (e: JobNotEnoughInputParametersException) {
            throw JobFewVariablesException()
        }
    }


    @Operation(summary = "Delete job for inference system")
    @ApiResponses(value = [
        ApiResponse(responseCode = "200", description = "Successful Request",
            content = [(Content(
                mediaType = "application/json",
                schema = Schema(implementation = JobNet::class)
            ))])])
    @DeleteMapping("/{id}")
    fun deleteJob(
        @PathVariable systemId: Int,
        @PathVariable id: UUID
    )  {
        log.info("$serverName| Delete job for system output request")
        jobsService.delete(id)
    }


}