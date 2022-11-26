package com.mianeko.fuzzyinferencesystemsbackend.DTODb.enums

import com.mianeko.fuzzyinferencesystemsbackend.database.entities.enums.DBLinguisticHedgeType
import com.mianeko.fuzzyinferencesystemsbackend.exceptions.LinguisticHedgeException
import com.mianeko.fuzzyinferencesystemsbackend.services.models.enums.LinguisticHedgeType

enum class LinguisticHedgeTypeDTODb(private val text: String) {
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

    fun toLinguisticHedgeTypeDb() =
        when(this) {
            Very -> DBLinguisticHedgeType.Very
            MoreOrLess -> DBLinguisticHedgeType.MoreOrLess
            Plus -> DBLinguisticHedgeType.Plus
            Not -> DBLinguisticHedgeType.Not
            NotVery -> DBLinguisticHedgeType.NotVery
            Nothing -> DBLinguisticHedgeType.Nothing
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

        fun fromLinguisticHedgeTypeDb(l: DBLinguisticHedgeType) =
            when (l) {
                DBLinguisticHedgeType.Very -> Very
                DBLinguisticHedgeType.MoreOrLess -> MoreOrLess
                DBLinguisticHedgeType.Plus -> Plus
                DBLinguisticHedgeType.Not -> Not
                DBLinguisticHedgeType.NotVery -> NotVery
                DBLinguisticHedgeType.Nothing -> Nothing
            }
    }

}