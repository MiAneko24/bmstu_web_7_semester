package com.mianeko.fuzzyinferencesystemsbackend.api.models.enums

import com.fasterxml.jackson.annotation.JsonValue
import com.mianeko.fuzzyinferencesystemsbackend.exceptions.LinguisticHedgeException
import com.mianeko.fuzzyinferencesystemsbackend.services.models.enums.LinguisticHedgeType

enum class LinguisticHedgeTypeNet(@JsonValue val text: String) {
    Very("Very"),
    MoreOrLess("More or less"),
    Plus("Plus"),
    Not("Not"),
    NotVery("Not very"),
    Nothing("");

    fun toLinguisticHedgeType() =
        when (this) {
            Very -> LinguisticHedgeType.Very
            MoreOrLess -> LinguisticHedgeType.MoreOrLess
            Plus -> LinguisticHedgeType.Plus
            Not -> LinguisticHedgeType.Not
            NotVery -> LinguisticHedgeType.NotVery
            Nothing -> LinguisticHedgeType.Nothing
        }

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
                "" -> Nothing
                else -> throw LinguisticHedgeException(s)
            }
    }
}