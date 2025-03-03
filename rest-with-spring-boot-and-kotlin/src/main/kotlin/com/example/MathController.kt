package com.example

import com.example.exceptions.UnsupportedMathOperationException
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
class MathController {

    @RequestMapping(value = ["/sum/{number1}/{number2}"])
    fun sum(
        @PathVariable(value = "number1") number1: String?,
        @PathVariable(value = "number2") number2: String?
    ): Double {
        if (!isNumeric(number1) || !isNumeric(number2))
            throw UnsupportedMathOperationException("Por favor insira valores numericos")

        return convertToDouble(number1) + convertToDouble(number2)
    }


    private fun isNumeric(number: String?): Boolean {
        if (number.isNullOrBlank()) return false
        val value = number.replace(",".toRegex(), ".")
        return value.matches("""[-+]?[0-9]*\.?[0-9]""".toRegex())

    }

    private fun convertToDouble(number: String?): Double {
        if (number.isNullOrBlank()) return 0.0;
        val value = number.replace(",".toRegex(), ".")
        return if (isNumeric(value)) value.toDouble() else 0.0
    }
}