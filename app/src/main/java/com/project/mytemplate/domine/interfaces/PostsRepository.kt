package com.project.mytemplate.domine.interfaces

import com.project.mytemplate.domine.models.IAResponse
import com.project.mytemplate.utils.StatusResult

interface PostsRepository {
    suspend fun sendMessage(message: String): StatusResult<IAResponse>
}
