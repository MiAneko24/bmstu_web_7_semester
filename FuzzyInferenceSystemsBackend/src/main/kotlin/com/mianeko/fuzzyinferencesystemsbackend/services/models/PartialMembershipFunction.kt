package com.mianeko.fuzzyinferencesystemsbackend.services.models

data class PartialMembershipFunction(
    val id: Int,
    val term: String?,
    val variableId: Int?
)