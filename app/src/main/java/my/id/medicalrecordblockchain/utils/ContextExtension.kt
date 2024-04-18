package my.id.medicalrecordblockchain.utils

import android.app.Activity
import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.content.ContextWrapper
import android.graphics.drawable.Drawable
import android.view.View
import android.view.inputmethod.InputMethodManager
import androidx.annotation.ColorRes
import androidx.annotation.DrawableRes
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import my.id.medicalrecordblockchain.BuildConfig
import my.id.medicalrecordblockchain.R
import java.time.LocalDate
import java.time.Period
import java.util.Calendar
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

fun decideFlavor(): String {
    return if (BuildConfig.FLAVOR.equals("patient")) {
        "PATIENT"
    } else {
        "DOCTOR"
    }
}


tailrec fun Context.activity(): Activity? = when (this) {
    is Activity -> this
    else -> (this as? ContextWrapper)?.baseContext?.activity()
}

fun Context.getLifecycleOwner(): LifecycleOwner {
    return try {
        this as LifecycleOwner
    } catch (exception: ClassCastException) {
        (this as ContextWrapper).baseContext as LifecycleOwner
    }
}

fun calculateAge(date: String): String {
    return try {
        val dob = Calendar.getInstance()
        val today = Calendar.getInstance()

        val parentSplitter = date.split('T')
        val splitter = parentSplitter[0].split('-')
        dob.set(splitter[0].toInt(), splitter[1].toInt(), splitter[2].toInt())

        var age = today.get(Calendar.YEAR) - dob.get(Calendar.YEAR)

        if (today.get(Calendar.DAY_OF_YEAR) < dob.get(Calendar.DAY_OF_YEAR)) {
            age--
        }

        val ageInt = age + 1

        "$ageInt Tahun"
    } catch (exception: Exception) {
        "-"
    }

}