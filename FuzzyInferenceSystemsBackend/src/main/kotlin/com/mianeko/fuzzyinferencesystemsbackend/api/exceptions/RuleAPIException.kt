package com.mianeko.fuzzyinferencesystemsbackend.api.exceptions

import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.ResponseStatus

open class RuleAPIException: RuntimeException()

@ResponseStatus(code = HttpStatus.NOT_FOUND, reason = "Rule not found")
class RuleNotFoundException : RuleAPIException()

@ResponseStatus(code = HttpStatus.BAD_REQUEST, reason = "Could not save rule: incorrect request body")
class IncorrectRuleBody : RuleAPIException()

@ResponseStatus(code = HttpStatus.BAD_REQUEST, reason = "Invalid antecedent connection type")
class InvalidAntecedentConnectionException : RuleAPIException()
