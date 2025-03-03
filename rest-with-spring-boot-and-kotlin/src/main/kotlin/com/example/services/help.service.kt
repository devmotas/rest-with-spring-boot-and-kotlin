package com.example.services

import org.springframework.stereotype.Service

@Service
class HelpService {
     fun isNumeric(number: String?): Boolean {
        if (number.isNullOrBlank()) return false
        val value = number.replace(",".toRegex(), ".")
        return value.matches("""[-+]?[0-9]*\.?[0-9]""".toRegex())

    }

     fun convertToDouble(number: String?): Double {
        if (number.isNullOrBlank()) return 0.0
        val value = number.replace(",".toRegex(), ".")
        return if (isNumeric(value)) value.toDouble() else 0.0
    }
}