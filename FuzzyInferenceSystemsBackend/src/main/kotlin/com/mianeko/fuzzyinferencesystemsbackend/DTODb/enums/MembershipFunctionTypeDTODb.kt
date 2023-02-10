package com.mianeko.fuzzyinferencesystemsbackend.DTODb.enums

import com.mianeko.fuzzyinferencesystemsbackend.database.entities.enums.DBMembershipFunctionType
import com.mianeko.fuzzyinferencesystemsbackend.exceptions.FunctionTypeException
import com.mianeko.fuzzyinferencesystemsbackend.services.models.enums.MembershipFunctionType

enum class MembershipFunctionTypeDTODb(private val text: String) {
    Shoulder("shoulder"),
    Gauss("gauss"),
    Triangle("triangle"),
    Trapezoidal("trapezoidal"),
    Linguistic("linguistic"),
    Linear("linear"),
    Crisp("crisp");

    override fun toString(): String {
        return text
    }

    fun toMembershipFunctionType() =
        when (this) {
            Shoulder -> MembershipFunctionType.Shoulder
            Gauss -> MembershipFunctionType.Gauss
            Triangle -> MembershipFunctionType.Triangle
            Trapezoidal -> MembershipFunctionType.Trapezoidal
            Linguistic -> MembershipFunctionType.Linguistic
            Linear -> MembershipFunctionType.Linear
            Crisp -> MembershipFunctionType.Crisp
        }

    fun toMembershipFunctionTypeDb() =
        when (this) {
            Shoulder -> DBMembershipFunctionType.Shoulder
            Gauss -> DBMembershipFunctionType.Gauss
            Triangle -> DBMembershipFunctionType.Triangle
            Trapezoidal -> DBMembershipFunctionType.Trapezoidal
            Linguistic -> DBMembershipFunctionType.Linguistic
            Linear -> DBMembershipFunctionType.Linear
            Crisp -> DBMembershipFunctionType.Crisp
        }

    companion object {
        fun fromString(s: String): MembershipFunctionTypeDTODb =
            when (s) {
                "gauss" -> Gauss
                "linear" -> Linear
                "linguistic" -> Linguistic
                "shoulder" -> Shoulder
                "trapezoidal" -> Trapezoidal
                "triangle" -> Triangle
                "crisp" -> Crisp
                else -> throw FunctionTypeException(s)
            }

        fun fromMembershipFunctionType(m: MembershipFunctionType) =
            when (m) {
                MembershipFunctionType.Shoulder -> Shoulder
                MembershipFunctionType.Gauss -> Gauss
                MembershipFunctionType.Triangle -> Triangle
                MembershipFunctionType.Trapezoidal -> Trapezoidal
                MembershipFunctionType.Linguistic -> Linguistic
                MembershipFunctionType.Linear -> Linear
                MembershipFunctionType.Crisp -> Crisp
            }

        fun fromMembershipFunctionTypeDb(m: DBMembershipFunctionType) =
            when (m) {
                DBMembershipFunctionType.Shoulder -> Shoulder
                DBMembershipFunctionType.Gauss -> Gauss
                DBMembershipFunctionType.Triangle -> Triangle
                DBMembershipFunctionType.Trapezoidal -> Trapezoidal
                DBMembershipFunctionType.Linguistic -> Linguistic
                DBMembershipFunctionType.Linear -> Linear
                DBMembershipFunctionType.Crisp -> Crisp
            }
    }
}