package com.project.mytemplate.presentation.screens.screen1

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.project.mytemplate.domine.usecase.SendMessageUseCase
import com.project.mytemplate.utils.StatusResult
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch


class ScreenOneViewModel(
    private val sendMessageUseCase: SendMessageUseCase
) : ViewModel() {

    private val _messages = MutableStateFlow<List<ChatMessage>>(emptyList())
    val messages: StateFlow<List<ChatMessage>> = _messages

    private val _inputText = MutableStateFlow("")
    val inputText: StateFlow<String> = _inputText

    private val _isTyping = MutableStateFlow(false)
    val isTyping: StateFlow<Boolean> = _isTyping

    init {
        sendWelcomeMessage()
    }

    private fun sendWelcomeMessage() {
        sendMessageToBot("Hola")
    }

    fun onInputChange(text: String) {
        _inputText.value = text
    }

    fun sendMessage() {
        val text = _inputText.value.trim()
        if (text.isNotEmpty()) {
            _messages.update { it + ChatMessage(text, isUser = true) }
            _inputText.value = ""
            sendMessageToBot(text)
        }
    }

    private fun sendMessageToBot(text: String) {
        viewModelScope.launch {
            _isTyping.value = true
            _messages.update { it + ChatMessage("...", isUser = false) }

            when (val result = sendMessageUseCase(text)) {
                is StatusResult.Success -> {
                    _messages.update { it.dropLast(1) + ChatMessage(result.value.respuesta, isUser = false) }
                }
                is StatusResult.Error -> {
                    _messages.update { it.dropLast(1) + ChatMessage("Error: ${result.message}", isUser = false) }
                }
            }
            _isTyping.value = false
        }
    }
}



data class ChatMessage(val text: String, val isUser: Boolean)
