package test.android.cardrec

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.BasicText
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp

internal class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val context: Context = this
        setContent {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color.Black)
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .align(Alignment.Center)
                ) {
                    BasicText(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(48.dp)
                            .clickable {
                                finish()
                                startActivity(Intent(context, GMSActivity::class.java))
                            },
                        style = TextStyle(
                            color = Color.Blue,
                            textAlign = TextAlign.Center
                        ),
                        text = "google"
                    )
                    BasicText(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(48.dp)
                            .clickable {
                                finish()
                                startActivity(Intent(context, PaycardsActivity::class.java))
                            },
                        style = TextStyle(
                            color = Color.Blue,
                            textAlign = TextAlign.Center
                        ),
                        text = "pay.cards"
                    )
                }
            }
        }
    }
}
