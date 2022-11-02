package com.mianeko.fuzzyinferencesystemsbackend.exceptions

open class MembershipFunctionException(
    override val message: String?
): IllegalArgumentException(message)

data class LinguisticHedgeException(
    val errorHedge: String?,
    override val message: String? = "Incorrect linguistic hedge $errorHedge"
): MembershipFunctionException(message)

data class FunctionTypeException(
    val errorType: String,
    override val message: String? = "Incorrect membership function type $errorType"
): MembershipFunctionException(message)

data class FunctionNotExistException(
    val errorId: Int?,
    override val message: String? = "Membership function with id $errorId does not exist"
): MembershipFunctionException(message)

data class FunctionSaveException(
    val id: Int,
    override val message: String? = "Could not save membership function with id $id"
): MembershipFunctionException(message)