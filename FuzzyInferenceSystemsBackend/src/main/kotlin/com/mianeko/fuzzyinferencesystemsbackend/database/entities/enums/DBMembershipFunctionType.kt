package com.mianeko.fuzzyinferencesystemsbackend.database.entities.enums

import com.fasterxml.jackson.annotation.JsonValue
import com.mianeko.fuzzyinferencesystemsbackend.exceptions.FunctionTypeException

enum class DBMembershipFunctionType(@JsonValue val text: String) {
    Shoulder("shoulder"),
    Gauss("gauss"),
    Triangle("triangle"),
    Trapezoidal("trapezoidal"),
    Linguistic("linguistic"),
    Linear("linear"),
    Crisp("crisp");

    companion object {
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