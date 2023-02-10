package com.mianeko.fuzzyinferencesystemsbackend.api.exceptions

import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.ResponseStatus

open class JobAPIException: RuntimeException()

@ResponseStatus(code = HttpStatus.NOT_FOUND, reason = "Job not found")
class JobNotFoundException : JobAPIException()

@ResponseStatus(code = HttpStatus.BAD_REQUEST, reason = "Not enough input parameters for system job")
class JobFewVariablesException : JobAPIException()

@ResponseStatus(code = HttpStatus.BAD_REQUEST, reason = "Invalid parameters present for system job")
class IncorrectJobVariablesException : JobAPIException()

@ResponseStatus(code = HttpStatus.BAD_REQUEST, reason = "Invalid job body")
class InvalidJobBody : JobAPIException()