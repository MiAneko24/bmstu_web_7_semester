package com.mianeko.fuzzyinferencesystemsbackend.DTODb.enums

import com.mianeko.fuzzyinferencesystemsbackend.api.models.enums.LinguisticHedgeTypeNet
import com.mianeko.fuzzyinferencesystemsbackend.exceptions.LinguisticHedgeException
import com.mianeko.fuzzyinferencesystemsbackend.services.models.enums.LinguisticHedgeType

enum class LinguisticHedgeTypeDTONet(private val text: String) {
    Very("Very"),
    MoreOrLess("More or less"),
    Plus("Plus"),
    Not("Not"),
    NotVery("Not very"),
    Nothing("");

    override fun toString(): String {
        return text
    }

    fun toLinguisticHedgeType() =
        when(this) {
            Very -> LinguisticHedgeType.Very
            MoreOrLess -> LinguisticHedgeType.MoreOrLess
            Plus -> LinguisticHedgeType.Plus
            Not -> LinguisticHedgeType.Not
            NotVery -> LinguisticHedgeType.NotVery
            Nothing -> LinguisticHedgeType.Nothing
        }

    fun toLinguisticHedgeTypeNet() =
        when(this) {
            Very -> LinguisticHedgeTypeNet.Very
            MoreOrLess -> LinguisticHedgeTypeNet.MoreOrLess
            Plus -> LinguisticHedgeTypeNet.Plus
            Not -> LinguisticHedgeTypeNet.Not
            NotVery -> LinguisticHedgeTypeNet.NotVery
            Nothing -> LinguisticHedgeTypeNet.Nothing
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

        fun fromLinguisticHedgeType(l: LinguisticHedgeType) =
            when (l) {
                LinguisticHedgeType.Very -> Very
                LinguisticHedgeType.MoreOrLess -> MoreOrLess
                LinguisticHedgeType.Plus -> Plus
                LinguisticHedgeType.Not -> Not
                LinguisticHedgeType.NotVery -> NotVery
                LinguisticHedgeType.Nothing -> Nothing
            }

        fun fromLinguisticHedgeTypeNet(l: LinguisticHedgeTypeNet) =
            when (l) {
                LinguisticHedgeTypeNet.Very -> Very
                LinguisticHedgeTypeNet.MoreOrLess -> MoreOrLess
                LinguisticHedgeTypeNet.Plus -> Plus
                LinguisticHedgeTypeNet.Not -> Not
                LinguisticHedgeTypeNet.NotVery -> NotVery
                LinguisticHedgeTypeNet.Nothing -> Nothing
            }
    }

}