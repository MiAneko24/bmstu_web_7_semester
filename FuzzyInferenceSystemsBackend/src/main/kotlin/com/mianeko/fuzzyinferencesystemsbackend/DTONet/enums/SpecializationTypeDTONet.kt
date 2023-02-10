package com.mianeko.fuzzyinferencesystemsbackend.DTODb.enums

import com.mianeko.fuzzyinferencesystemsbackend.api.models.enums.SpecializationTypeNet
import com.mianeko.fuzzyinferencesystemsbackend.exceptions.SystemSpecializationException
import com.mianeko.fuzzyinferencesystemsbackend.services.models.enums.SpecializationType

enum class SpecializationTypeDTONet(private val text: String) {
    Physics("physics"),
    Chemistry("chemistry"),
    Informatics("informatics");

    override fun toString(): String {
        return text
    }

    fun toSpecializationType() =
        when (this) {
            Physics -> SpecializationType.Physics
            Chemistry -> SpecializationType.Chemistry
            Informatics -> SpecializationType.Informatics
        }

    fun toSpecializationTypeNet() =
        when (this) {
            Physics -> SpecializationTypeNet.physics
            Chemistry -> SpecializationTypeNet.chemistry
            Informatics -> SpecializationTypeNet.informatics
        }

    companion object {
        fun fromString(s: String): SpecializationTypeDTONet {
            val st: SpecializationTypeDTONet
            st = when (s) {
                "physics" -> Physics
                "chemistry" -> Chemistry
                "informatics" -> Informatics
                else -> throw SystemSpecializationException(s)
            }
            return st
        }

        fun fromSpecializationType(s: SpecializationType) =
            when (s) {
                SpecializationType.Physics -> Physics
                SpecializationType.Chemistry -> Chemistry
                SpecializationType.Informatics -> Informatics
            }

        fun fromSpecializationTypeNet(s: SpecializationTypeNet) =
            when (s) {
                SpecializationTypeNet.physics -> Physics
                SpecializationTypeNet.chemistry -> Chemistry
                SpecializationTypeNet.informatics -> Informatics
            }
    }
}
