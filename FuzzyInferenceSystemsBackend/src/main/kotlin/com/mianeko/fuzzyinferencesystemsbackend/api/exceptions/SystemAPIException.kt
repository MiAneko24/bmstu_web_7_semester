package com.mianeko.fuzzyinferencesystemsbackend.api.exceptions

import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.ResponseStatus

open class SystemAPIException: RuntimeException()

@ResponseStatus(code = HttpStatus.BAD_REQUEST, reason = "Could not save system: incorrect request body")
class IncorrectSystemBody : SystemAPIException()

@ResponseStatus(code = HttpStatus.NOT_FOUND, reason = "System not found")
class SystemNotFoundException : SystemAPIException()

