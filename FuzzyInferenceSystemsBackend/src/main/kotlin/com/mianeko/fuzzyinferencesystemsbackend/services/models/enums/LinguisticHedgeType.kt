package com.mianeko.fuzzyinferencesystemsbackend.services.models.enums

enum class LinguisticHedgeType(private val text: String) {
    Very("Very"),
    MoreOrLess("More or less"),
    Plus("Plus"),
    Not("Not"),
    NotVery("Not very"),
    Nothing("");
}