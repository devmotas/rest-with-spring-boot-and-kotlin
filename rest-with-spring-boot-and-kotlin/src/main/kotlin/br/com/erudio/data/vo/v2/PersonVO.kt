package br.com.erudio.data.vo.v2

import java.util.*


data class PersonVO(
    var id: Long = 0,
    var firstName: String = "",
    var lastName: String = "",
    var address: String = "",
    var gender: String = "",
    var birthDate: Date? = null,
)