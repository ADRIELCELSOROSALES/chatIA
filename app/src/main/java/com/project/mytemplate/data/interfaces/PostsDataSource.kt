package com.project.mytemplate.data.interfaces

import com.project.mytemplate.data.models.IAResponseDTO
import com.project.mytemplate.utils.StatusResult

interface PostsDataSource {
    suspend fun sendMessage(message: String): StatusResult<IAResponseDTO>
}
