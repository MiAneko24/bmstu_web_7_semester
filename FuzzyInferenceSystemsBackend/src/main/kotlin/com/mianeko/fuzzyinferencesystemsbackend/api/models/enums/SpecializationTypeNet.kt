package com.mianeko.fuzzyinferencesystemsbackend.api.models.enums

import com.fasterxml.jackson.annotation.JsonValue

enum class SpecializationTypeNet(@JsonValue val text: String) {
    physics("physics"),
    chemistry("chemistry"),
    informatics("informatics");
}