package com.mianeko.fuzzyinferencesystemsbackend.lookupEntities

data class AntecedentLookup(
    val systemId: Int,
    val ruleId: Int?,
    val antecedentId: Int?
)
