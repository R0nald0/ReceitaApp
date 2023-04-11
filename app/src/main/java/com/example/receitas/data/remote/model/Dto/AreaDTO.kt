package com.example.receitas.data.remote.model.Dto

import com.example.receitas.data.local.model.AreaDAO

data class AreaDTO(
    val strArea: String
)
fun AreaDTO.toAreaDAO():AreaDAO{
    return AreaDAO().apply { this.nameArea = strArea}
}