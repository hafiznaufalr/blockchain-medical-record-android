package my.id.medicalrecordblockchain.utils

import android.app.Activity
import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.graphics.drawable.Drawable
import android.view.View
import android.view.inputmethod.InputMethodManager
import androidx.annotation.ColorRes
import androidx.annotation.DrawableRes
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import my.id.medicalrecordblockchain.BuildConfig
import my.id.medicalrecordblockchain.R
import kotlin.math.roundToInt

fun Context.getDrawableResources(
    @DrawableRes drawableRes: Int
): Drawable? {
    return ContextCompat.getDrawable(this, drawableRes)
}

fun Context.getColorResources(
    @ColorRes colorRes: Int
): Int {
    return ContextCompat.getColor(this, colorRes)
}

fun Context.hideKeyboard(view: View) {
    val inputMethodManager = getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
    inputMethodManager.hideSoftInputFromWindow(view.windowToken, 0)
}

fun Activity.hideKeyboard() {
    hideKeyboard(currentFocus ?: View(this))
}

fun Fragment.hideKeyboard() {
    this.view?.let { activity?.hideKeyboard(it) }
}

fun Context.copyText(text: String) {
    val clipboard: ClipboardManager =
        getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
    val clipData = ClipData.newPlainText("${R.string.app_name}", text)

    clipboard.setPrimaryClip(clipData)
}

fun Context.getDpFromInt(value: Int): Int {
    val scale: Float = this.resources.displayMetrics.density
    return (value * scale + 0.5f).roundToInt()
}

fun decideActionByFlavor(
    patientAction: () -> Unit,
    doctorAction: () -> Unit
) {
    if (BuildConfig.FLAVOR == "patient") {
        patientAction.invoke()
    } else {
        doctorAction.invoke()
    }

}