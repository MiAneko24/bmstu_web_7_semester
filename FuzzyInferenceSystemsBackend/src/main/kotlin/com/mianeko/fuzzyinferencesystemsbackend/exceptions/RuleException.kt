package com.mianeko.fuzzyinferencesystemsbackend.exceptions

open class RuleException(
    override val message: String?
): IllegalArgumentException(message)

data class RuleSaveException(
    val id: Int,
    override val message: String? = "Could not save rule with id $id"
): RuleException(message)

data class RuleNotExistException(
    val id: Int?,
    override val message: String? = "System with id $id does not exist"
): RuleException(message)