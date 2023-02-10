package com.mianeko.fuzzyinferencesystemsbackend.lookupEntities

data class ConsequentLookup(
    val systemId: Int,
    val ruleId: Int,
    val consequentId: Int?
)
