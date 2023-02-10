package com.mianeko.fuzzyinferencesystemsbackend.api.models.enums

import com.fasterxml.jackson.annotation.JsonValue


enum class FuzzySystemTypeNet(@JsonValue val text: String) {
    Mamdani("Mamdani"),
    Sugeno("Sugeno");
}