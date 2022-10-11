package test.android.cardrec

import android.app.Activity
import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.activity.result.ActivityResult
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.BasicText
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import cards.pay.paycardsrecognizer.sdk.Card
import cards.pay.paycardsrecognizer.sdk.ScanCardIntent

internal class PaycardsActivity : AppCompatActivity() {
    private fun onResult(result: ActivityResult) {
        println("on result: ${result.data?.data}")
        when (result.resultCode) {
            Activity.RESULT_OK -> {
                val intent = result.data ?: TODO()
                val card = intent.getParcelableExtra<Card>(ScanCardIntent.RESULT_PAYCARDS_CARD) ?: TODO()
                showToast("""
                    card
                     - ${card.cardNumber}
                     - ${card.cardNumberRedacted}
                     - ${card.cardHolderName}
                     - ${card.expirationDate}
                """.trimIndent())
            }
            else -> showToast("on result: ${result.data}")
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val intent = ScanCardIntent.Builder(this).build()
        setContent {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color.Black)
            ) {
                val requested = rememberSaveable { mutableStateOf(false) }
                val cardState = remember { mutableStateOf<Card?>(null) }
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
                    StartActivityForResult(
                        builder = { intent },
                        onResult = {
                            requested.value = false
                            when (it.resultCode) {
                                Activity.RESULT_OK -> {
                                    val data = it.data ?: TODO()
                                    cardState.value = data.getParcelableExtra(ScanCardIntent.RESULT_PAYCARDS_CARD) ?: TODO()
                                }
                                else -> showToast("on result: ${it.data}")
                            }
                        }
                    )
                }
                val card = cardState.value
                if (card != null) {
                    BasicText(
                        modifier = Modifier
                            .align(Alignment.BottomStart)
                            .padding(bottom = 48.dp),
                        style = TextStyle(color = Color.Green),
                        text = """
                            card
                             - ${card.cardNumber}
                             - ${card.cardNumberRedacted}
                             - ${card.cardHolderName}
                             - ${card.expirationDate}
                        """.trimIndent()
                    )
                }
            }
        }
    }
}
