package test.android.cardrec

import android.content.Context
import android.widget.Toast

internal fun Context.showToast(text: String, duration: Int = Toast.LENGTH_SHORT) {
    Toast.makeText(this, text, duration).show()
}
