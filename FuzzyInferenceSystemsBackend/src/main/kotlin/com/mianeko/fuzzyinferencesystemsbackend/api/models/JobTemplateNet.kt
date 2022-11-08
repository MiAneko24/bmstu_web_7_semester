package com.mianeko.fuzzyinferencesystemsbackend.api.models

import java.util.*

data class JobTemplateNet(
    val inputVariables: List<DataElementNet>
)

data class DataElementNet(
    val key: Int,
    val value: Double
)

data class JobNet(
    val id: UUID,
    val inputVariables: List<DataElementNet>
)
