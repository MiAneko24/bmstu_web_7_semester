package com.mianeko.fuzzyinferencesystemsbackend.api.exceptions

import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.ResponseStatus

open class ConsequentAPIException: RuntimeException()

@ResponseStatus(code = HttpStatus.NOT_FOUND, reason = "Consequent not found")
class ConsequentNotFoundException : ConsequentAPIException()

@ResponseStatus(code = HttpStatus.BAD_REQUEST, reason = "Could not save consequent: invalid request body")
class IncorrectConsequentBody : ConsequentAPIException()