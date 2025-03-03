package com.example.controller

import com.example.exceptions.UnsupportedMathOperationException
import com.example.services.HelpService
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
class MathController(private val helpService: HelpService) {
    @RequestMapping(value = ["/sum/{number1}/{number2}"])
    fun sum(
        @PathVariable(value = "number1") number1: String?,
        @PathVariable(value = "number2") number2: String?
    ): Double {
        if (!helpService.isNumeric(number1) || !helpService.isNumeric(number2))
            throw UnsupportedMathOperationException("Por favor insira valores numericos")

        return helpService.convertToDouble(number1) + helpService.convertToDouble(number2)
    }

    @RequestMapping(value = ["/subtract/{number1}/{number2}"])
    fun subtract(
        @PathVariable(value = "number1") number1: String?,
        @PathVariable(value = "number2") number2: String?
    ): Double {
        if (!helpService.isNumeric(number1) || !helpService.isNumeric(number2))
            throw UnsupportedMathOperationException("Por favor insira valores numericos")

        return helpService.convertToDouble(number1) - helpService.convertToDouble(number2)
    }

    @RequestMapping(value = ["/multiply/{number1}/{number2}"])
    fun multiply(
        @PathVariable(value = "number1") number1: String?,
        @PathVariable(value = "number2") number2: String?
    ): Double {
        if (!helpService.isNumeric(number1) || !helpService.isNumeric(number2))
            throw UnsupportedMathOperationException("Por favor insira valores numericos")

        return helpService.convertToDouble(number1) * helpService.convertToDouble(number2)
    }

    @RequestMapping(value = ["/divide/{number1}/{number2}"])
    fun divide(
        @PathVariable(value = "number1") number1: String?,
        @PathVariable(value = "number2") number2: String?
    ): Double {
        if (!helpService.isNumeric(number1) || !helpService.isNumeric(number2))
            throw UnsupportedMathOperationException("Por favor insira valores numericos")

        return helpService.convertToDouble(number1) / helpService.convertToDouble(number2)
    }

    @RequestMapping(value = ["/average/{number1}/{number2}"])
    fun average(
        @PathVariable(value = "number1") number1: String?,
        @PathVariable(value = "number2") number2: String?
    ): Double {
        if (!helpService.isNumeric(number1) || !helpService.isNumeric(number2))
            throw UnsupportedMathOperationException("Por favor insira valores numericos")

        return (helpService.convertToDouble(number1) + helpService.convertToDouble(number2)) / 2
    }


    @RequestMapping(value = ["/sqrt/{number}"])
    fun sqrt(
        @PathVariable(value = "number") number1: String?,
    ): Double {
        if (!helpService.isNumeric(number1))
            throw UnsupportedMathOperationException("Por favor insira valores numericos")

        return kotlin.math.sqrt(helpService.convertToDouble(number1))
    }
}