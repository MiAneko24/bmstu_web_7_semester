package com.mianeko.fuzzyinferencesystemsbackend.exceptions

open class ConsequentException(
    override val message: String?
): IllegalArgumentException(message)

data class ConsequentSaveException(
    val id: Int,
    override val message: String? = "Could not save consequent with id $id"
): ConsequentException(message)

data class ConsequentNotExistException(
    val id: Int?,
    override val message: String? = "Consequent with id $id does not exist"
): ConsequentException(message)