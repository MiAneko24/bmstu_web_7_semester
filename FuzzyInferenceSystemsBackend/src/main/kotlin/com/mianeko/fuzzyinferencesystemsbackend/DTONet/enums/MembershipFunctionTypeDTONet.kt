package com.mianeko.fuzzyinferencesystemsbackend.DTODb.enums

import com.mianeko.fuzzyinferencesystemsbackend.api.models.enums.MembershipFunctionTypeNet
import com.mianeko.fuzzyinferencesystemsbackend.exceptions.FunctionTypeException
import com.mianeko.fuzzyinferencesystemsbackend.services.models.enums.MembershipFunctionType

enum class MembershipFunctionTypeDTONet(private val text: String) {
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

    fun toMembershipFunctionTypeNet() =
        when (this) {
            Shoulder -> MembershipFunctionTypeNet.shoulder
            Gauss -> MembershipFunctionTypeNet.gauss
            Triangle -> MembershipFunctionTypeNet.triangle
            Trapezoidal -> MembershipFunctionTypeNet.trapezoidal
            Linguistic -> MembershipFunctionTypeNet.linguistic
            Linear -> MembershipFunctionTypeNet.linear
            Crisp -> MembershipFunctionTypeNet.crisp
        }

    companion object {
        fun fromString(s: String): MembershipFunctionTypeDTONet =
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

        fun fromMembershipFunctionTypeNet(m: MembershipFunctionTypeNet) =
            when (m) {
                MembershipFunctionTypeNet.shoulder -> Shoulder
                MembershipFunctionTypeNet.gauss -> Gauss
                MembershipFunctionTypeNet.triangle -> Triangle
                MembershipFunctionTypeNet.trapezoidal -> Trapezoidal
                MembershipFunctionTypeNet.linguistic -> Linguistic
                MembershipFunctionTypeNet.linear -> Linear
                MembershipFunctionTypeNet.crisp -> Crisp
            }
    }
}