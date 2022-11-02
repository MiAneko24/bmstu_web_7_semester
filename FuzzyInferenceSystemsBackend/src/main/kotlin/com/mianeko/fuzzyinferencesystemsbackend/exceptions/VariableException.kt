package com.mianeko.fuzzyinferencesystemsbackend.exceptions

open class VariableException(
    override val message: String?
): IllegalArgumentException(message)

data class VariableSaveException(
    val id: Int,
    override val message: String? = "Could not save variable with id $id"
): VariableException(message)

data class VariableNotExistException(
    val id: Int?,
    override val message: String? = "Variable with id $id does not exist"
): VariableException(message)