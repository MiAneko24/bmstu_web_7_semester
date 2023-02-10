package com.mianeko.fuzzyinferencesystemsbackend.services.models.enums

enum class AntecedentConnection(private val text: String) {
    Or("or"),
    And("and");

    override fun toString(): String {
        return text
    }
}