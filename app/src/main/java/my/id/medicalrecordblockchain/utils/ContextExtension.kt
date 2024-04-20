package my.id.medicalrecordblockchain.utils

import android.app.Activity
import android.app.DownloadManager
import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.content.ContextWrapper
import android.graphics.drawable.Drawable
import android.net.Uri
import android.os.Environment
import android.util.Log
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.annotation.ColorRes
import androidx.annotation.DrawableRes
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.LifecycleOwner
import my.id.medicalrecordblockchain.BuildConfig
import my.id.medicalrecordblockchain.R
import java.io.File
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

fun String.formatDate(): String {
    return try {
        val parentSplitter = this.split('T')
        val splitter = parentSplitter[0].split('-')

        "${splitter[2]}-${splitter[1]}-${splitter[0]}"
    } catch (exception: Exception) {
        "-"
    }

}

fun Context.downloadMedicalRecord(filename: String, url: String?) {
    try {
        val dm = this.getSystemService(Context.DOWNLOAD_SERVICE) as DownloadManager?
        val downloadUri = Uri.parse(url)
        val request = DownloadManager.Request(downloadUri)
        request.setAllowedNetworkTypes(DownloadManager.Request.NETWORK_WIFI or DownloadManager.Request.NETWORK_MOBILE)
            .setAllowedOverRoaming(false)
            .setTitle(filename)
            .setMimeType("application/pdf")
            .setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED)
            .setDestinationInExternalPublicDir(
                Environment.DIRECTORY_DOWNLOADS,
                File.separator + filename + ".pdf"
            )
        dm!!.enqueue(request)
    } catch (e: Exception) {
        Toast.makeText(this, "Gagal ekspor rekam medis", Toast.LENGTH_SHORT).show()
    }
}

fun String.getGender(): String {
    return if (this == "MALE") {
        "Laki-laki"
    } else {
        "Perempuan"
    }
}