package com.mianeko.fuzzyinferencesystemsbackend.api.exceptions

import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.ResponseStatus

open class AntecedentAPIException: RuntimeException()

@ResponseStatus(code = HttpStatus.BAD_REQUEST, reason = "Could not save antecedent: incorrect request body")
class IncorrectAntecedentBody : AntecedentAPIException()

@ResponseStatus(code = HttpStatus.NOT_FOUND, reason = "Antecedent not found")
class AntecedentNotFoundException : AntecedentAPIException()