package test.android.cardrec

import android.app.PendingIntent
import android.content.Context
import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.text.BasicText
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.google.android.gms.wallet.PaymentCardRecognitionIntentRequest
import com.google.android.gms.wallet.PaymentsClient
import com.google.android.gms.wallet.Wallet
import com.google.android.gms.wallet.WalletConstants
import test.android.cardrec.showToast

internal class GMSActivity : AppCompatActivity() {
    companion object {
        private fun Context.getPaymentsClient(): PaymentsClient {
            val options = Wallet.WalletOptions.Builder()
                .setEnvironment(WalletConstants.ENVIRONMENT_TEST)
//                .setEnvironment(WalletConstants.ENVIRONMENT_PRODUCTION)
                .build()
            return Wallet.getPaymentsClient(this, options)
        }
    }

    @Composable
    private fun OnIntent(intent: PendingIntent) {
        Box(modifier = Modifier.fillMaxSize()) {
            val requested = rememberSaveable { mutableStateOf(false) }
            BasicText(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(48.dp)
                    .align(Alignment.Center)
                    .clickable {
                        showToast("click")
                        requested.value = true
                    },
                style = TextStyle(
                    color = Color.Blue,
                    textAlign = TextAlign.Center
                ),
                text = "request"
            )
            if (requested.value) {
                StartIntentSenderForResult(
                    builder = { intent.intentSender },
                    onResult = {
                        requested.value = false
                        println("on result: ${it.data?.data}")
                        showToast("on result: ${it.data?.data}")
                    }
                )
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val client = getPaymentsClient()
        val request = PaymentCardRecognitionIntentRequest.getDefaultInstance()
        setContent {
            val intentState = remember { mutableStateOf<PendingIntent?>(null) }
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color.Black)
            ) {
                val intent = intentState.value
                if (intent == null) {
                    client
                        .getPaymentCardRecognitionIntent(request)
                        .addOnSuccessListener {
                            intentState.value = it.paymentCardRecognitionPendingIntent
                        }
                        .addOnFailureListener {
                            println("on failure: $it")
                            showToast(it.toString())
                        }
                } else {
                    OnIntent(intent)
                }
            }
        }
    }
}
