package com.mianeko.fuzzyinferencesystemsbackend.services.models

import java.util.*

data class Job(
    val id: UUID,
    val inputVariables: Map<Int, Double>,
    val systemId: Int
)
