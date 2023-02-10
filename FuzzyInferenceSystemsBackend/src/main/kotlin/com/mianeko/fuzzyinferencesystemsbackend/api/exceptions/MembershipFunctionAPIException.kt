package com.mianeko.fuzzyinferencesystemsbackend.api.exceptions

import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.ResponseStatus

open class MembershipFunctionAPIException: RuntimeException()

@ResponseStatus(code = HttpStatus.NOT_FOUND, reason = "Membership function not found")
class MembershipFunctionNotFoundException : MembershipFunctionAPIException()

@ResponseStatus(code = HttpStatus.BAD_REQUEST, reason = "Could not save membership function: incorrect request body")
class IncorrectFunctionBody : MembershipFunctionAPIException()

@ResponseStatus(code = HttpStatus.BAD_REQUEST, reason = "Invalid membership function type")
class InvalidFunctionTypeException : MembershipFunctionAPIException()

@ResponseStatus(code = HttpStatus.BAD_REQUEST, reason = "Invalid linguistic hedge")
class InvalidHedgeException : MembershipFunctionAPIException()

