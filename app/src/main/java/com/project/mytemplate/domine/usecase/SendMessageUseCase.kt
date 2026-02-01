package com.project.mytemplate.domine.usecase

import android.util.Log
import com.project.mytemplate.domine.interfaces.PostsRepository
import com.project.mytemplate.domine.models.IAResponse
import com.project.mytemplate.utils.StatusResult

class SendMessageUseCase(private val postsRepository: PostsRepository) {
    suspend operator fun invoke(message: String): StatusResult<IAResponse> {
        Log.d("SendMessageUseCase", "Invocado con mensaje: $message")
        return postsRepository.sendMessage(message)
    }
}
