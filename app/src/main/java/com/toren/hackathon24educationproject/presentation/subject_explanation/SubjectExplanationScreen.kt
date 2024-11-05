package com.toren.hackathon24educationproject.presentation.subject_explanation

import android.widget.Toast
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Send
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.lifecycle.repeatOnLifecycle
import app.rive.runtime.kotlin.RiveAnimationView
import app.rive.runtime.kotlin.core.ExperimentalAssetLoader
import com.toren.hackathon24educationproject.R
import com.toren.hackathon24educationproject.common.Constants.STATE_MACHINE_NAME
import com.toren.hackathon24educationproject.presentation.components.LoadingAnimation
import com.toren.hackathon24educationproject.presentation.theme.Purple80
import kotlinx.coroutines.flow.Flow

@OptIn(ExperimentalAssetLoader::class)
@Composable
fun SubjectExplanationScreen(
    uiState: SubjectExplanationContract.UiState,
    uiEffect: Flow<SubjectExplanationContract.UiEffect>,
    uiEvent: (SubjectExplanationContract.UiEvent) -> Unit,
) {
    val focusManager = LocalFocusManager.current
    val keyboardController = LocalSoftwareKeyboardController.current
    var riveView: RiveAnimationView? by remember { mutableStateOf(null) }

    val context = LocalContext.current
    val lifecycleOwner = LocalLifecycleOwner.current

    LaunchedEffect(uiEffect, lifecycleOwner) {
        lifecycleOwner.lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED) {
            uiEffect.collect { effect ->
                when (effect) {
                    is SubjectExplanationContract.UiEffect.ShowToast -> {
                        Toast.makeText(context, effect.message, Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }
    }

    LaunchedEffect(uiState.isAnswerFocused) {
        riveView?.setBooleanState(STATE_MACHINE_NAME, "Check", uiState.isAnswerFocused.not())
    }

    LaunchedEffect(uiState.text) {
        riveView?.setNumberState(STATE_MACHINE_NAME, "Look", 2 * uiState.text.length.toFloat())
    }

    Column(
        modifier = Modifier.fillMaxWidth()
    ) {

        Box(
            modifier = Modifier
                .weight(0.4f)
                .fillMaxWidth(),
            contentAlignment = Alignment.BottomCenter
        ) {
            AndroidView(
                modifier = Modifier,
                factory = { context ->
                    RiveAnimationView(context).apply {
                        setRiveResource(
                            resId = R.raw.animated_character,
                            stateMachineName = STATE_MACHINE_NAME,
                            alignment = app.rive.runtime.kotlin.core.Alignment.BOTTOM_CENTER
                        )
                        riveView = this
                    }
                }
            )
        }


        Card(
            modifier = Modifier
                .fillMaxWidth()
                .weight(0.9f)
                .padding(4.dp),
            colors = CardDefaults.cardColors(
                containerColor = Purple80
            )
        ) {
            Box(
                modifier = Modifier
                    .fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                if (uiState.isLoading) {
                    LoadingAnimation()
                } else {
                    Text(
                        modifier = Modifier
                            .padding(8.dp)
                            .verticalScroll(rememberScrollState()),
                        text = uiState.explanation,
                        style = TextStyle(
                            color = Color.Black,
                            fontSize = 17.sp
                        ),
                        textAlign = TextAlign.Center
                    )
                }
            }
        }

        Row(
            modifier = Modifier
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            OutlinedTextField(
                value = uiState.text,
                onValueChange = { uiEvent(SubjectExplanationContract.UiEvent.OnTextChange(it)) },
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(0.2f)
                    .padding(4.dp)
                    .onFocusChanged { uiEvent(SubjectExplanationContract.UiEvent.OnAnswerFocused) },
            )
            IconButton(onClick = {
                focusManager.clearFocus()
                keyboardController?.hide()
                uiEvent(SubjectExplanationContract.UiEvent.OnReplyClick)
            }) {
                Icon(imageVector = Icons.Default.Send, contentDescription = "Send Button")
            }
        }

    }
}