package com.project.mytemplate

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.lifecycleScope
import com.project.mytemplate.presentation.components.scaffold.TopBar
import com.project.mytemplate.presentation.screens.screen1.ScreenOne
import com.project.mytemplate.presentation.ui.theme.MyTemplateTheme
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {
    private var isLoading by mutableStateOf(true)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        lifecycleScope.launch {
            delay(4000)
            isLoading = false
        }

        setContent {
            MyTemplateTheme(dynamicColor = true) {
                Scaffold(
                    topBar = { TopBar() },
                    content = { paddingValues ->
                        ScreenOne(
                            modifier = Modifier
                                .padding(paddingValues)
                                .systemBarsPadding()
                        )
                    }
                )
            }
        }
    }
}
