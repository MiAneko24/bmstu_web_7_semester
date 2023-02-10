package com.mianeko.fuzzyinferencesystemsbackend.exceptions


open class SystemException(
    override val message: String?
):  IllegalArgumentException(message)

data class SystemTypeException(
    val errorType: String,
    override val message: String? = "Invalid fuzzy system type $errorType"
): SystemException(message)

data class SystemSpecializationException(
    val errorSpecialization: String?,
    override val message: String? = "Invalid system specialization $errorSpecialization"
): SystemException(message)

data class SystemNotExistException(
    val systemId: Int?,
    override val message: String? = "System with id $systemId does not exist"
): SystemException(message)

data class SystemSaveException(
    val id: Int,
    override val message: String? = "Could not save system with id $id"
): SystemException(message)
