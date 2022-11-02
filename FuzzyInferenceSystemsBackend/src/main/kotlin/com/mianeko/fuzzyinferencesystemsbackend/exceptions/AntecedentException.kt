package com.mianeko.fuzzyinferencesystemsbackend.exceptions

open class AntecedentException(
    override val message: String?
): IllegalArgumentException(message)

data class AntecedentWithNoVariableException(
    override val message: String? = "Antecedent can not have reference to function without variable"
): AntecedentException(message)

data class AntecedentConnectionException(
    val errorConnection: String,
    override val message: String? = "Invalid antecedent connection type $errorConnection"
): AntecedentException(message)

data class AntecedentSaveException(
    val id: Int,
    override val message: String? = "Could not save antecedent with id $id"
): AntecedentException(message)

data class AntecedentNotExistException(
    val id: Int?,
    override val message: String? = "Antecedent with id $id does not exist"
): AntecedentException(message)