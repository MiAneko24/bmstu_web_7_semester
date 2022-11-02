package com.mianeko.fuzzyinferencesystemsbackend.api.exceptions

import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.ResponseStatus

open class VariableAPIException: RuntimeException()

@ResponseStatus(code = HttpStatus.NOT_FOUND, reason = "Variable not found")
class VariableNotFoundException : VariableAPIException()

@ResponseStatus(code = HttpStatus.BAD_REQUEST, reason = "Could not save variable: incorrect request body")
class IncorrectVariableBody : VariableAPIException()

@ResponseStatus(code = HttpStatus.BAD_REQUEST, reason = "MaxValue must be more than minValue of variable, " +
        "value must be in range [minValue; maxValue]")
class IncorrectVariableRange : VariableAPIException()
