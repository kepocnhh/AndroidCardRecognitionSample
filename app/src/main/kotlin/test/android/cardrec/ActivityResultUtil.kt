package test.android.cardrec

import android.content.Intent
import android.content.IntentSender
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.ActivityResult
import androidx.activity.result.IntentSenderRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable

@Composable
internal fun StartActivityForResult(
    builder: () -> Intent,
    onResult: (ActivityResult) -> Unit
) {
    val isLaunched = rememberSaveable { mutableStateOf(false) }
    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.StartActivityForResult(),
        onResult = onResult
    )
    LaunchedEffect(Unit) {
        if (!isLaunched.value) {
            isLaunched.value = true
            launcher.launch(builder())
        }
    }
}

@Composable
internal fun StartIntentSenderForResult(
    builder: () -> IntentSender,
    onResult: (ActivityResult) -> Unit
) {
    val isLaunched = rememberSaveable { mutableStateOf(false) }
    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.StartIntentSenderForResult(),
        onResult = onResult
    )
    LaunchedEffect(Unit) {
        if (!isLaunched.value) {
            isLaunched.value = true
            launcher.launch(IntentSenderRequest.Builder(builder()).build())
        }
    }
}
