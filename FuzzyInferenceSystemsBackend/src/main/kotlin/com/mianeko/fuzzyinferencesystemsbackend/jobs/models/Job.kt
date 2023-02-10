package com.mianeko.fuzzyinferencesystemsbackend.jobs.models

import java.util.*

data class Job(
    val id: UUID,
    val inputVariables: Map<Int, Double>,
    val systemId: Int
)
