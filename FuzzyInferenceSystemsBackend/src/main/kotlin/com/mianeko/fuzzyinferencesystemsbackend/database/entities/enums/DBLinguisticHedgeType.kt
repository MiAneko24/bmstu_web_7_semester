package com.mianeko.fuzzyinferencesystemsbackend.database.entities.enums

import com.fasterxml.jackson.annotation.JsonValue
import com.mianeko.fuzzyinferencesystemsbackend.exceptions.LinguisticHedgeException
import com.mianeko.fuzzyinferencesystemsbackend.services.models.enums.LinguisticHedgeType

enum class DBLinguisticHedgeType(@JsonValue val text: String?) {
    Very("Very"),
    MoreOrLess("More or less"),
    Plus("Plus"),
    Not("Not"),
    NotVery("Not very"),
    Nothing(null);

    fun toLinguisticHedgeType() =
        LinguisticHedgeType.fromString(text ?: "")

    companion object {
        fun fromLinguisticHedgeType(linguisticHedgeType: LinguisticHedgeType) =
            when (linguisticHedgeType) {
                LinguisticHedgeType.Very -> Very
                LinguisticHedgeType.MoreOrLess -> MoreOrLess
                LinguisticHedgeType.Plus -> Plus
                LinguisticHedgeType.Not -> Not
                LinguisticHedgeType.NotVery -> NotVery
                LinguisticHedgeType.Nothing -> Nothing
            }

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