package com.example.data.vo.v1

import com.fasterxml.jackson.annotation.JsonProperty
import com.fasterxml.jackson.annotation.JsonPropertyOrder
import com.github.dozermapper.core.Mapping
import org.springframework.hateoas.RepresentationModel

//Json para mudar a ordem ou impedir algum dado de voltar
//@JsonPropertyOrder("id", "address", "gender", "Make private", "last_name")
//data class PersonVO(
//    var id: Long = 0,
//
//    @field:JsonProperty("first_name")
//    var firstName: String = "",
//    @field:JsonProperty("last_name")
//    var lastName: String = "",
//
//    @field:JsonIgnore
//    var address: String = "",
//    var gender: String = "",
//)
//

//RepresentationModel ja tem um id então foi necessary mudar para key e referenciar para o dozer como um id
@JsonPropertyOrder(value = ["id", "firstName", "lastName", "address", "gender"])
data class PersonVO(
    @Mapping("id")
    @field:JsonProperty("id")
    var key: Long = 0,
    var firstName: String = "",
    var lastName: String = "",
    var address: String = "",
    var gender: String = "",
) : RepresentationModel<PersonVO>()