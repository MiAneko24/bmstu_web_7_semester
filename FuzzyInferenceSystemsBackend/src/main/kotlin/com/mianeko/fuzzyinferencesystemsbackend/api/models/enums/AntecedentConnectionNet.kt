package com.mianeko.fuzzyinferencesystemsbackend.api.models.enums

import com.fasterxml.jackson.annotation.JsonValue

enum class AntecedentConnectionNet(@JsonValue val text: String) {
    or("or"),
    and("and");
}