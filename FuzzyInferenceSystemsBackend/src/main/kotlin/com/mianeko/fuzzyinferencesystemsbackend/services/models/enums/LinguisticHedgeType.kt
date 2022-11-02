package com.mianeko.fuzzyinferencesystemsbackend.services.models.enums

import com.mianeko.fuzzyinferencesystemsbackend.exceptions.LinguisticHedgeException

enum class LinguisticHedgeType(private val text: String) {
    Very("Very"),
    MoreOrLess("More or less"),
    Plus("Plus"),
    Not("Not"),
    NotVery("Not very"),
    Nothing("");

    override fun toString(): String {
        return text
    }

    companion object {
        fun fromString(s: String) =
            when (s) {
                "Very" -> Very
                "More or less" -> MoreOrLess
                "Plus" -> Plus
                "Not" -> Not
                "Not very" -> NotVery
                "" -> Nothing
                else -> throw LinguisticHedgeException(s)
            }
    }

}