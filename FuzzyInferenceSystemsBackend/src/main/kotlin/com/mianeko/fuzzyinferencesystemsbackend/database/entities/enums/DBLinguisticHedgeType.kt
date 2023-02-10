package com.mianeko.fuzzyinferencesystemsbackend.database.entities.enums

import com.fasterxml.jackson.annotation.JsonValue
import com.mianeko.fuzzyinferencesystemsbackend.exceptions.LinguisticHedgeException

enum class DBLinguisticHedgeType(@JsonValue val text: String?) {
    Very("Very"),
    MoreOrLess("More or less"),
    Plus("Plus"),
    Not("Not"),
    NotVery("Not very"),
    Nothing(null);

    companion object {

        fun fromString(s: String?) =
            when (s) {
                "Very" -> Very
                "More or less" -> MoreOrLess
                "Plus" -> Plus
                "Not" -> Not
                "Not very" -> NotVery
                null -> Nothing
                "null" -> Nothing
                else -> throw LinguisticHedgeException(s)
            }
    }
}