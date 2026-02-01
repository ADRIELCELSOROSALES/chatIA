package com.project.mytemplate.data.mappers


import com.project.mytemplate.data.models.IAResponseDTO
import com.project.mytemplate.domine.models.IAResponse

fun IAResponseDTO.toPost(): IAResponse {
    return IAResponse(
        respuesta = respuesta
    )
}

