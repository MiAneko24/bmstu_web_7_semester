package com.mianeko.fuzzyinferencesystemsbackend.api.models.enums

import com.fasterxml.jackson.annotation.JsonValue
import com.mianeko.fuzzyinferencesystemsbackend.exceptions.FunctionTypeException
import com.mianeko.fuzzyinferencesystemsbackend.services.models.enums.MembershipFunctionType

enum class MembershipFunctionTypeNet(@JsonValue val text: String) {
    shoulder("shoulder"),
    gauss("gauss"),
    triangle("triangle"),
    trapezoidal("trapezoidal"),
    linguistic("linguistic"),
    linear("linear"),
    crisp("crisp");

    fun toMembershipFunctionType() =
        when (this) {
            shoulder -> MembershipFunctionType.Shoulder
            gauss -> MembershipFunctionType.Gauss
            triangle -> MembershipFunctionType.Triangle
            trapezoidal -> MembershipFunctionType.Trapezoidal
            linguistic -> MembershipFunctionType.Linguistic
            linear -> MembershipFunctionType.Linear
            crisp -> MembershipFunctionType.Crisp
        }


    companion object {
        fun fromMembershipFunctionType(membershipFunctionType: MembershipFunctionType) =
            when(membershipFunctionType) {
                MembershipFunctionType.Shoulder -> shoulder
                MembershipFunctionType.Gauss -> gauss
                MembershipFunctionType.Triangle -> triangle
                MembershipFunctionType.Trapezoidal -> trapezoidal
                MembershipFunctionType.Linguistic -> linguistic
                MembershipFunctionType.Linear -> linear
                MembershipFunctionType.Crisp -> crisp
            }

        fun fromString(s: String) =
            when (s) {
                "gauss" -> gauss
                "linear" -> linear
                "linguistic" -> linguistic
                "shoulder" -> shoulder
                "trapezoidal" -> trapezoidal
                "triangle" -> triangle
                "crisp" -> crisp
                else -> throw FunctionTypeException(s)
            }
    }
}