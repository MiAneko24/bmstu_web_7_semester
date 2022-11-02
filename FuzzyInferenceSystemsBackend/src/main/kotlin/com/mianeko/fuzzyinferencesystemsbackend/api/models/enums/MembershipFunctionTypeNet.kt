package com.mianeko.fuzzyinferencesystemsbackend.api.models.enums

import com.fasterxml.jackson.annotation.JsonValue
import com.mianeko.fuzzyinferencesystemsbackend.exceptions.FunctionTypeException
import com.mianeko.fuzzyinferencesystemsbackend.services.models.enums.MembershipFunctionType

enum class MembershipFunctionTypeNet(@JsonValue val text: String) {
    Shoulder("shoulder"),
    Gauss("gauss"),
    Triangle("triangle"),
    Trapezoidal("trapezoidal"),
    Linguistic("linguistic"),
    Linear("linear"),
    Crisp("crisp");

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


    companion object {
        fun fromMembershipFunctionType(membershipFunctionType: MembershipFunctionType) =
            when(membershipFunctionType) {
                MembershipFunctionType.Shoulder -> Shoulder
                MembershipFunctionType.Gauss -> Gauss
                MembershipFunctionType.Triangle -> Triangle
                MembershipFunctionType.Trapezoidal -> Trapezoidal
                MembershipFunctionType.Linguistic -> Linguistic
                MembershipFunctionType.Linear -> Linear
                MembershipFunctionType.Crisp -> Crisp
            }

        fun fromString(s: String) =
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
    }
}