package com.mianeko.fuzzyinferencesystemsbackend.services.models.enums

import com.mianeko.fuzzyinferencesystemsbackend.exceptions.FunctionTypeException

enum class MembershipFunctionType(private val text: String) {
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


    companion object {
        fun fromString(s: String): MembershipFunctionType =
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